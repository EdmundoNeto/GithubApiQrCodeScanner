package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import com.edmundo.domain.repository.QrCodeScannerRepository
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class QrCodeScannerViewModelTest {

    private val repository: QrCodeScannerRepository = mockk(relaxed = true)
    private lateinit var viewModel: QrCodeScannerViewModel

    @Before
    fun setup() {
        viewModel = QrCodeScannerViewModel(repository)
    }

    @Test
    fun `when QrCode url is not valid then I must show a message`() {

        val qrCodeUrl = "https://globo.com"

        viewModel.run {
            splitUrl(qrCodeUrl)

            Assert.assertEquals(View.VISIBLE, noUserFoundVisibility.value)
        }
    }

    @Test
    fun `when QrCode url is valid but user is not found then I must show a message`() {

        val qrCodeUrl = "https://github.com/EdmundoNetoqweweterty"

        viewModel.run {
            splitUrl(qrCodeUrl)

            Assert.assertEquals(View.VISIBLE, errorVisibility.value)
        }
    }

    @Test
    fun `when QrCode url is valid and user was found then I must not show a message`() {

        val qrCodeUrl = "https://github.com/EdmundoNeto"

        viewModel.run {
            splitUrl(qrCodeUrl)

            Assert.assertEquals(View.GONE, noUserFoundVisibility.value)
        }
    }
}