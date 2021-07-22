package com.gowtham.ricknmorty.di

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://rickandmortyapi.com/graphql/"
private const val TIMEOUT = 60L

val apiModule = module {
    single {
        OkHttpClient().newBuilder()
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
    }

    single {
        ApolloClient.builder()
            .okHttpClient(get())
            .serverUrl(BASE_URL)
            .build()
    }
}
