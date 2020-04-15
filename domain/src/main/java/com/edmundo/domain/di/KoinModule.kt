package com.edmundo.domain.di

import com.edmundo.domain.QrCodeScannerApi
import com.edmundo.domain.repository.QrCodeScannerRepository
import org.koin.dsl.module

val serviceModule = module {
    single {
        QrCodeScannerApi.getApiService()
    }
}

val repositoryModule = module {
    single {
        QrCodeScannerRepository(service = get())
    }
}