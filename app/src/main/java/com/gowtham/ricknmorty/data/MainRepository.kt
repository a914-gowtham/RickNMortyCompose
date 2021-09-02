package com.gowtham.ricknmorty.data

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiDataSourceImpl: ApiDataSource // injects by hilt
) : ApiDataSource {

    override fun getCharacters() = apiDataSourceImpl.getCharacters()

    override fun getEpisodes() = apiDataSourceImpl.getEpisodes()

    override fun getLocations() = apiDataSourceImpl.getLocations()

    override suspend fun getCharacter(
        characterId: String
    ) = apiDataSourceImpl.getCharacter(characterId)

    override suspend fun getEpisode(
        episodeId: String
    ) = apiDataSourceImpl.getEpisode(episodeId)

    override suspend fun getLocation(
        locationId: String
    ) = apiDataSourceImpl.getLocation(locationId)
}
