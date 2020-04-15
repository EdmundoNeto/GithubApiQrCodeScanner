package com.edmundo.qrcodescanner.di

import com.edmundo.domain.model.Repository
import com.edmundo.qrcodescanner.adapter.CommitsAdapter
import com.edmundo.qrcodescanner.adapter.RepositoriesAdapter
import com.edmundo.qrcodescanner.viewmodel.CommitsViewModel
import com.edmundo.qrcodescanner.viewmodel.QrCodeScannerViewModel
import com.edmundo.qrcodescanner.viewmodel.RepositoriesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { QrCodeScannerViewModel(repository = get()) }
    viewModel { RepositoriesViewModel(repository = get()) }
    viewModel { CommitsViewModel(repository = get()) }
}

val adapterModule = module {
    factory { (action: (Repository) -> Unit) ->
        RepositoriesAdapter(
            itemClickAction = action
        )
    }
    factory {
        CommitsAdapter()
    }
}