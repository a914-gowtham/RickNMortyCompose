package com.gowtham.ricknmorty.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.BuildConfig
import com.gowtham.ricknmorty.data.ApiDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://rickandmortyapi.com/graphql/"
    private const val TIMEOUT = 60L

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttp = OkHttpClient().newBuilder()
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            okHttp.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        return okHttp.build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiHelper(
        @ApplicationContext
        context: Context,
        apollo: ApolloClient,
    ): ApiDataSourceImpl {
        return ApiDataSourceImpl(
            context,
            apolloClient = apollo
        )
    }
}
