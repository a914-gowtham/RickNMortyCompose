package com.gowtham.ricknmorty.domain

import android.content.Context
import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class LocationUseCase @Inject constructor(val repository: MainRepository) {
    fun getLocations(context: Context) = repository.getLocations(context)
    suspend fun getLocation(
        context: Context,
        locationId: String
    ) = repository.getLocation(context, locationId)
}