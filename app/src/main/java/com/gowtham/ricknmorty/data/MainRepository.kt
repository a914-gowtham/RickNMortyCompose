package com.gowtham.ricknmorty.data

import android.content.Context
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiDataSourceImpl: ApiDataSource // injects by hilt
) : ApiDataSource {

    override fun getCharacters(context: Context) = apiDataSourceImpl.getCharacters(context)

    override fun getEpisodes(context: Context) = apiDataSourceImpl.getEpisodes(context)

    override fun getLocations(context: Context) = apiDataSourceImpl.getLocations(context)

    override suspend fun getCharacter(
        context: Context,
        characterId: String
    ) = apiDataSourceImpl.getCharacter(context, characterId)

    override suspend fun getEpisode(
        context: Context,
        episodeId: String
    ) = apiDataSourceImpl.getEpisode(context, episodeId)

    override suspend fun getLocation(
        context: Context,
        locationId: String
    ) = apiDataSourceImpl.getLocation(context, locationId)
}
