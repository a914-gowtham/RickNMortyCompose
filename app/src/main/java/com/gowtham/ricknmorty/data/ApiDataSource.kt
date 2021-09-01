package com.gowtham.ricknmorty.data

import android.content.Context
import androidx.paging.PagingData
import com.gowtham.ricknmorty.utils.Resource
import fragment.CharacterDetail
import fragment.EpisodeDetail
import fragment.LocationDetail
import kotlinx.coroutines.flow.Flow

interface ApiDataSource {

    fun getCharacters(context: Context): Flow<PagingData<CharacterDetail>>

    fun getEpisodes(context: Context): Flow<PagingData<EpisodeDetail>>

    fun getLocations(context: Context): Flow<PagingData<LocationDetail>>

    suspend fun getCharacter(context: Context,characterId: String): Resource<CharacterDetail>

    suspend fun getEpisode(context: Context,episodeId: String): Resource<EpisodeDetail>

    suspend fun getLocation(context: Context,locationId: String): Resource<LocationDetail>
}
