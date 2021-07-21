package com.gowtham.ricknmorty.remote

import GetCharacterQuery
import GetEpisodeQuery
import GetLocationQuery
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.gowtham.ricknmorty.compose.characters.CharactersDataSource
import com.gowtham.ricknmorty.compose.episodes.EpisodesDataSource
import com.gowtham.ricknmorty.compose.locations.LocationsDataSource
import com.gowtham.ricknmorty.utils.Resource
import com.gowtham.ricknmorty.utils.Utils
import fragment.CharacterDetail
import fragment.EpisodeDetail
import fragment.LocationDetail

class ApiHelperImpl(
    private val appContext: Context,
    private val apolloClient: ApolloClient
) : ApiHelper {

    override fun getCharacters() = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(context = appContext, apollo = apolloClient)
    }.flow

    override fun getEpisodes() = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(context = appContext, apollo = apolloClient)
    }.flow

    override fun getLocations() = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(context = appContext, apollo = apolloClient)
    }.flow

    override suspend fun getCharacter(characterId: String): Resource<CharacterDetail> {
        if (!Utils.isNetConnected(appContext))
            return Resource.Error("Internet is not connected")
        return try {
            val response = apolloClient.query(GetCharacterQuery(characterId)).await()
            response.data?.character?.fragments?.characterDetail?.let {
                Resource.Success(it)
            } ?: Resource.Error("Character detail is unavailable")
        } catch (ex: Exception) {
            print(ex)
            Resource.Error(ex.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getEpisode(episodeId: String): Resource<EpisodeDetail> {
        if (!Utils.isNetConnected(appContext))
            return Resource.Error("Internet is not connected")
        return try {
            val response = apolloClient.query(GetEpisodeQuery(episodeId)).await()
            response.data?.episode?.fragments?.episodeDetail?.let {
                Resource.Success(it)
            } ?: Resource.Error("Episode detail is unavailable")
        } catch (ex: Exception) {
            print(ex)
            Resource.Error(ex.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getLocation(locationId: String): Resource<LocationDetail> {
        if (!Utils.isNetConnected(appContext))
            return Resource.Error("Internet is not connected")
        return try {
            val response = apolloClient.query(GetLocationQuery(locationId)).await()
            response.data?.location?.fragments?.locationDetail?.let {
                Resource.Success(it)
            } ?: Resource.Error("Location detail is unavailable")
        } catch (ex: Exception) {
            print(ex)
            Resource.Error(ex.localizedMessage ?: "Unknown error occurred")
        }
    }
}
