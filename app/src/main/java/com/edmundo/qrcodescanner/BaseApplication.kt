package com.edmundo.qrcodescanner

import android.app.Application
import com.edmundo.domain.di.repositoryModule
import com.edmundo.domain.di.serviceModule
import com.edmundo.qrcodescanner.di.adapterModule
import com.edmundo.qrcodescanner.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@BaseApplication)

            modules(
                listOf(
                    serviceModule,
                    repositoryModule,
                    viewModelModule,
                    adapterModule
                )
            )
        }

    }
}