package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.repository.QrCodeScannerRepository
import com.edmundo.qrcodescanner.utils.State
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class CommitsViewModelTest {

    private val repository: QrCodeScannerRepository = mockk(relaxed = true)
    private lateinit var viewModel: CommitsViewModel

    @Before
    fun setup() {
        viewModel = CommitsViewModel(repository)
    }

    @Test
    fun `when there is no commit then I must show a message`() {
        viewModel.run {
            commitsList.value = emptyList()
            validateData()

            Assert.assertEquals(View.VISIBLE, noComitsFoundVisibility.value)
        }
    }

    @Test
    fun `when commitsList is not empty then I must not show a message`() {
        val repo: CommitResponse = mockk(relaxed = true)
        viewModel.run {
            commitsList.value = listOf(repo)
            validateData()

            Assert.assertEquals(View.GONE, noComitsFoundVisibility.value)
        }
    }

    @Test
    fun `when an error occoured trying to get commit list then I must not show a screen error`() {
        viewModel.run {
            setState(State.ERROR)

            Assert.assertEquals(View.VISIBLE, errorVisibility.value)
        }
    }

}