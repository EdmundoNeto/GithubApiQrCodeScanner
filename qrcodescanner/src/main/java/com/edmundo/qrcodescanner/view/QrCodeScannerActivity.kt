package com.edmundo.qrcodescanner.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.extensions.*
import com.edmundo.qrcodescanner.viewmodel.QrCodeScannerViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_qrcode_scanneer.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.android.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class QrCodeScannerActivity: AppCompatActivity(),
    ZXingScannerView.ResultHandler,
    EasyPermissions.PermissionCallbacks {

    val REQUEST_CODE_CAMERA = 1

    private val viewModel: QrCodeScannerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_qrcode_scanneer)
        setupObservable()
        askCameraPermission()
    }

    private fun setupObservable() {

        viewModel.run {
            observe(user) {
                it?.run {
                    startActivity(
                        Intent(this@QrCodeScannerActivity, RepositoriesActivity::class.java)
                            .putExtra("username", this.login)
                    )
                }
            }
            observe(mainLoaderVisibility) {
                it?.run {
                    loadingLayout.visibility = this
                }
            }

            observe(noUserFoundVisibility) {
                it?.run {
                    tvUserNotFound.visibility = this
                }
            }

            observe(errorVisibility) {
                it?.runCatching {
                    tvUserNotFound.visibility = this
                }
            }

        }
    }
    override fun onResume() {
        super.onResume()
        z_xing_scanner.setResultHandler(this)
        restartCameraIfInactive()
    }

    private fun restartCameraIfInactive(){
        if( !z_xing_scanner.isCameraStarted()
            && EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA) ){
            startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        z_xing_scanner.stopCameraForAllDevices()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this )
    }

    override fun onPermissionsDenied(
        requestCode: Int,
        perms: MutableList<String>) {

        askCameraPermission()
    }

    private fun askCameraPermission(){
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(this, REQUEST_CODE_CAMERA, Manifest.permission.CAMERA)
                .setRationale( getString(R.string.request_permission_description) )
                .setPositiveButtonText( getString(R.string.request_permission_button_ok) )
                .setNegativeButtonText( getString(R.string.request_permission_button_cancel) )
                .build() )
    }

    override fun onPermissionsGranted(
        requestCode: Int,
        perms: MutableList<String>) {

        startCamera()
    }

    private fun startCamera(){
        z_xing_scanner.startCameraForAllDevices(this)
    }

    override fun handleResult(result: Result?) {

        if( result == null ){
            unrecognizedCode(this, {  })
            return
        }

        processBarcodeResult(
            result.text,
            result.barcodeFormat.name)
    }

    private fun processBarcodeResult(
        text: String,
        barcodeFormatName: String ){

        val result = Result(
            text,
            text.toByteArray(),
            arrayOf(),
            BarcodeFormat.valueOf(barcodeFormatName))

        z_xing_scanner.resumeCameraPreview(this)

        viewModel.splitUrl(result.text)

    }
}