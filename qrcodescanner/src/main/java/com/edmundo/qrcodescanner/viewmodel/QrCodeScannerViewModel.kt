package com.edmundo.qrcodescanner.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edmundo.domain.model.User
import com.edmundo.domain.repository.QrCodeScannerRepository
import com.edmundo.qrcodescanner.utils.State
import kotlinx.coroutines.launch

class QrCodeScannerViewModel(val repository: QrCodeScannerRepository) : BaseViewModel() {

    private val URL_BASE = "https://github.com/"
    val user: MutableLiveData<User> = MutableLiveData()
    val noUserFoundVisibility = MutableLiveData<Int>().apply {
        value = View.GONE
    }

    fun validateData() {
        user.value?.takeIf { it.id <= 0 }?.run {
            noUserFoundVisibility.value = getVisibility(true)
        }
    }

    private fun clearEmptyWarning() {
        noUserFoundVisibility.value = getVisibility(false)
    }

    fun splitUrl(url: String) {
        val username = url.split(URL_BASE)

        try {
            when {
                username.size > 1 && username[1].isNotEmpty() ->
                    getUser(username[1])
                else ->
                    noUserFoundVisibility.value = getVisibility(true)
            }
        } catch (e: Exception) {
            noUserFoundVisibility.value = getVisibility(true)
        }

    }

    fun getUser(username: String) {
        clearEmptyWarning()

        setState(State.LOADING)

        viewModelScope.launch {
            try {
                user.value = repository.getUser(username)

                validateData()

                setState(State.SUCCESS)
            } catch (ex: Exception) {
                setState(State.ERROR)
            }
        }
    }
}