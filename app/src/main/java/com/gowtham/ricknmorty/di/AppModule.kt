package com.gowtham.ricknmorty.di

import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.MainViewModel
import com.gowtham.ricknmorty.remote.ApiHelper
import com.gowtham.ricknmorty.remote.ApiHelperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiHelper> { ApiHelperImpl(get(), get()) }
    single { MainRepository(get()) }
    viewModel { MainViewModel(get()) }
}
