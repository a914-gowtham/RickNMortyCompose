package com.gowtham.ricknmorty

import GetCharacterQuery
import GetEpisodeQuery
import GetLocationQuery
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val appContext: Context,
    private val apolloClient: ApolloClient
) {

    /* suspend fun getAllCharacters(page: Int): Response<GetCharactersQuery.Data> {
         return apolloClient.query(GetCharactersQuery(page = Input.optional(page))).await()
     }*/

    val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(context = appContext, apollo = apolloClient)
    }.flow

    val episodes: Flow<PagingData<EpisodeDetail>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(context = appContext, apollo = apolloClient)
    }.flow

    val locations: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(context = appContext, apollo = apolloClient)
    }.flow

    suspend fun getCharacter(characterId: String): Resource<CharacterDetail> {
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

    suspend fun getEpisode(episodeId: String): Resource<EpisodeDetail> {
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

    suspend fun getLocation(locationId: String): Resource<LocationDetail> {
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

    /*
    suspend fun getCharacter(characterId: String): CharacterDetail? {
        val response = apolloClient.query(GetCharacterQuery(characterId)).await()
        return response.data?.character?.fragments?.characterDetail
    }

    suspend fun getEpisodes(page: Int): GetEpisodesQuery.Episodes? {
        val response =
            apolloClient.query(GetEpisodesQuery(Input.optional(page))).await()
        return response.data?.episodes
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail? {
        val response = apolloClient.query(GetEpisodeQuery(episodeId)).await()
        return response.data?.episode?.fragments?.episodeDetail
    }

    suspend fun getLocations(page: Int): GetLocationsQuery.Locations? {
        val response =
            apolloClient.query(GetLocationsQuery(Input.optional(page))).await()
        return response.data?.locations
    }

    suspend fun getLocation(locationId: String): LocationDetail? {
        val response = apolloClient.query(GetLocationQuery(locationId)).await()
        return response.data?.location?.fragments?.locationDetail
    }

    fun getApolloClient() = apolloClient;*/
}
