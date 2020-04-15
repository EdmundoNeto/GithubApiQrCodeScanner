package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.repository.QrCodeScannerRepository
import com.edmundo.qrcodescanner.utils.State
import kotlinx.coroutines.launch

class CommitsViewModel(val repository: QrCodeScannerRepository) : BaseViewModel() {

    val commitsList: MutableLiveData<List<CommitResponse>> = MutableLiveData()
    val noComitsFoundVisibility = MutableLiveData<Int>().apply {
        value = View.GONE
    }

    fun validateData() {
        commitsList.value?.takeIf { it.isEmpty() }?.run {
            noComitsFoundVisibility.value = getVisibility(true)
        }
    }

    private fun clearEmptyWarning() {
        noComitsFoundVisibility.value = getVisibility(false)
    }

    fun getReposCommits(owner: String, repo: String) {
        clearEmptyWarning()

        setState(State.LOADING)

        viewModelScope.launch {
            try {
                commitsList.value = repository.getCommits(owner, repo)
                validateData()

                setState(State.SUCCESS)
            } catch (ex: Exception) {
                setState(State.ERROR)
            }
        }
    }
}