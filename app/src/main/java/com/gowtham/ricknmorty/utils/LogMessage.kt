package com.gowtham.ricknmorty.utils

import android.util.Log
import com.gowtham.ricknmorty.BuildConfig.DEBUG

object LogMessage {

    private val logVisible = DEBUG

    internal fun v(msg: String) {
        if (logVisible) Log.v("RickNMortyApp:: ", msg)
    }

    internal fun e(msg: String) {
        if (logVisible) Log.e("RickNMortyApp:: ", msg)
    }
}
