package com.gowtham.ricknmorty

import com.gowtham.ricknmorty.remote.ApiHelper

class MainRepository(
    private val apiHelper: ApiHelper // injected in appModule
) {

    fun getCharacterFlow() = apiHelper.getCharacters()

    fun getEpisodeFlow() = apiHelper.getEpisodes()

    fun getLocationFlow() = apiHelper.getLocations()

    suspend fun getCharacter(characterId: String) = apiHelper.getCharacter(characterId)

    suspend fun getEpisode(episodeId: String) = apiHelper.getEpisode(episodeId)

    suspend fun getLocation(locationId: String) = apiHelper.getLocation(locationId)
}
