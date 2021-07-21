package com.gowtham.ricknmorty.di

import com.gowtham.ricknmorty.remote.ApiHelper
import com.gowtham.ricknmorty.remote.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: ApiHelperImpl
    ): ApiHelper
}
