package com.edmundo.qrcodescanner.extensions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.bindingContentView(layout: Int): ViewDataBinding =
    DataBindingUtil.setContentView(this, layout)

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, expression: (T?) -> Unit) {
    liveData.observe(this, Observer(expression))
}


fun String.toDate(): Date? =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(this)

@SuppressLint("DefaultLocale")
fun Date.longDateTimeString(): String =
    SimpleDateFormat(
        "EEEE, dd/MM 'às' HH:mm",
        Locale.forLanguageTag("pt-BR")
    ).format(this)