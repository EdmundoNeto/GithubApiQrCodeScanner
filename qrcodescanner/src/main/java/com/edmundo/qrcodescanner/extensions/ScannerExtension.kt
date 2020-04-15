package com.edmundo.qrcodescanner.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Camera
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.widget.Toast
import com.edmundo.qrcodescanner.R
import me.dm7.barcodescanner.core.CameraUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import kotlin.concurrent.thread


fun ZXingScannerView.startCameraForAllDevices(context: Context){
    this.configCameraForAllDevices(context)
    this.startCamera()
    this.setTag(this.id, true)
}

private fun ZXingScannerView.configCameraForAllDevices(context: Context){
    this.setBorderColor(Color.RED) /* Cor das extremidades */
    this.setLaserColor(Color.YELLOW) /* Cor da linha central de alinhamento com código de barra */
    this.setAutoFocus(true)

    this.rotation = 0.0F
    val brand = Build.MANUFACTURER
    if( brand.equals("HUAWEI", true) ){
        this.setAspectTolerance(0.5F)
    }
}

fun ZXingScannerView.stopCameraForAllDevices(){
    this.stopCamera()
    this.releaseForAllDevices()
    this.setTag(this.id, false)
}

private fun ZXingScannerView.releaseForAllDevices(){
    val camera = CameraUtils.getCameraInstance()
    if( camera != null ){
        (camera as Camera).release()
    }
}

fun ZXingScannerView.isCameraStarted(): Boolean{
    val startData = this.getTag(this.id)
    val startStatus = (startData ?: false) as Boolean
    return startStatus
}

fun ZXingScannerView.isFlashSupported(context: Context) =
    context
        .packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

fun ZXingScannerView.enableFlash(
    context: Context,
    status: Boolean) {

    if( this.isFlashSupported(context) ){
        this.flash = status
    }
}

fun ZXingScannerView.threadCallWhenCameraIsWorking(callback: ()->Unit){
    thread {
        while( !this.isShown() ){
            SystemClock.sleep(1000) /* 1 segundo foi o tempo ideal para não parar com o funcionamento da câmera. */
        }

        callback()
    }
}

fun unrecognizedCode( context: Context, callbackClear: ()->Unit = {} ){
    Toast
        .makeText(
            context,
            context.getString(R.string.unrecognized_code),
            Toast.LENGTH_SHORT )
        .show()

    callbackClear()
}

fun notification(context: Context){
    try {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context.getApplicationContext(), notification)
        ringtone.play()
    }
    catch(e: Exception) { }
}