package com.gowtham.ricknmorty

import android.app.Application
import com.gowtham.ricknmorty.compose.character.characterViewModel
import com.gowtham.ricknmorty.compose.episode.episodeViewModel
import com.gowtham.ricknmorty.compose.location.locationViewModel
import com.gowtham.ricknmorty.di.apiModule
import com.gowtham.ricknmorty.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MApplication)
            modules(
                listOf(
                    apiModule, appModule,
                    characterViewModel, episodeViewModel, locationViewModel
                )
            )
        }
    }
}
