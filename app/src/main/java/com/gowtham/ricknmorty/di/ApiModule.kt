package com.gowtham.ricknmorty.di

import com.gowtham.ricknmorty.data.ApiDataSource
import com.gowtham.ricknmorty.data.ApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: ApiDataSourceImpl
    ): ApiDataSource
}
