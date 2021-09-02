package com.gowtham.ricknmorty.domain

import android.content.Context
import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class LocationUseCase @Inject constructor(val repository: MainRepository) {
    fun getLocations() = repository.getLocations()
    suspend fun getLocation(
        context: Context,
        locationId: String
    ) = repository.getLocation( locationId)
}