package com.gowtham.ricknmorty.domain

import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class LocationUseCase @Inject constructor(private val repository: MainRepository) {
    fun getLocations() = repository.getLocations()
    suspend fun getLocation(
        locationId: String
    ) = repository.getLocation(locationId)
}
