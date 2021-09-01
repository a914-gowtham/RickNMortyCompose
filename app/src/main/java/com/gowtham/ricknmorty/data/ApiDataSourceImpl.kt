package com.gowtham.ricknmorty.data

import GetCharacterQuery
import GetEpisodeQuery
import GetLocationQuery
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.gowtham.ricknmorty.data.paging.CharactersDataSource
import com.gowtham.ricknmorty.data.paging.EpisodesDataSource
import com.gowtham.ricknmorty.data.paging.LocationsDataSource
import com.gowtham.ricknmorty.utils.Resource
import com.gowtham.ricknmorty.utils.Utils
import fragment.CharacterDetail
import fragment.EpisodeDetail
import fragment.LocationDetail

class ApiDataSourceImpl(
    private val apolloClient: ApolloClient
) : ApiDataSource {

    override fun getCharacters(context: Context) = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(context = context, apollo = apolloClient)
    }.flow

    override fun getEpisodes(context: Context) = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(context = context, apollo = apolloClient)
    }.flow

    override fun getLocations(context: Context) = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(context = context, apollo = apolloClient)
    }.flow

    override suspend fun getCharacter(context: Context,characterId: String): Resource<CharacterDetail> {
        if (!Utils.isNetConnected(context))
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

    override suspend fun getEpisode(context: Context,episodeId: String): Resource<EpisodeDetail> {
        if (!Utils.isNetConnected(context))
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

    override suspend fun getLocation(context: Context,locationId: String): Resource<LocationDetail> {
        if (!Utils.isNetConnected(context))
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
