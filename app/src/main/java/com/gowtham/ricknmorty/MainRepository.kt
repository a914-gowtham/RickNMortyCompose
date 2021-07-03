package com.gowtham.ricknmorty

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.ui.characters.CharactersDataSource
import fragment.CharacterDetail
import kotlinx.coroutines.flow.Flow

class MainRepository(private val apolloClient: ApolloClient) {

   /* suspend fun getAllCharacters(page: Int): Response<GetCharactersQuery.Data> {
        return apolloClient.query(GetCharactersQuery(page = Input.optional(page))).await()
    }*/

    val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(apollo = apolloClient)
    }.flow

    /*val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(apollo = apolloClient)
    }.flow

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
