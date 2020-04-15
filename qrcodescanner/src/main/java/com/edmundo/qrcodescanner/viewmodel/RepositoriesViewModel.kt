package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edmundo.domain.model.Repository
import com.edmundo.domain.repository.QrCodeScannerRepository
import com.edmundo.qrcodescanner.utils.State
import kotlinx.coroutines.launch

class RepositoriesViewModel(val repository: QrCodeScannerRepository) : BaseViewModel() {

    val reposList: MutableLiveData<List<Repository>> = MutableLiveData()
    val noReposFoundVisibility = MutableLiveData<Int>().apply {
        value = View.GONE
    }

    fun validateData() {
        reposList.value?.takeIf { it.isEmpty() }?.run {
            noReposFoundVisibility.value = getVisibility(true)
        }
    }

    private fun clearEmptyWarning() {
        noReposFoundVisibility.value = getVisibility(false)
    }

    fun getData(username: String) {
        clearEmptyWarning()

        setState(State.LOADING)

        viewModelScope.launch {
            try {
                reposList.value = repository.getRepositories(username)
                validateData()

                setState(State.SUCCESS)
            } catch (ex: Exception) {
                setState(State.ERROR)
            }
        }
    }
}