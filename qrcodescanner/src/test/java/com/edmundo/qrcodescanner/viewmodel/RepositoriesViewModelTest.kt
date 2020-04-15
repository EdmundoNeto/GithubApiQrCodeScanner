package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import com.edmundo.domain.model.Repository
import com.edmundo.domain.repository.QrCodeScannerRepository
import com.edmundo.qrcodescanner.utils.State
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RepositoriesViewModelTest {

    private val repository: QrCodeScannerRepository = mockk(relaxed = true)
    private lateinit var viewModel: RepositoriesViewModel

    @Before
    fun setup() {
        viewModel = RepositoriesViewModel(repository)
    }

    @Test
    fun `when there is no repositories then I must show a message`() {
        viewModel.run {
            reposList.value = emptyList()
            validateData()

            Assert.assertEquals(View.VISIBLE, noReposFoundVisibility.value)
        }
    }

    @Test
    fun `when eventsList is not empty then I must not show a message`() {
        val repo: Repository = mockk(relaxed = true)
        viewModel.run {
            reposList.value = listOf(repo)
            validateData()

            Assert.assertEquals(View.GONE, noReposFoundVisibility.value)
        }
    }

    @Test
    fun `when an error occoured trying to get repositories list then I must not show a screen error`() {
        viewModel.run {
            setState(State.ERROR)

            Assert.assertEquals(View.VISIBLE, errorVisibility.value)
        }
    }

}