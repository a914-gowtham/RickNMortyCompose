package com.gowtham.ricknmorty.compose.episodes

import GetEpisodesQuery
import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.gowtham.ricknmorty.utils.Utils
import fragment.EpisodeDetail

private const val START_OFFSET = 0

class EpisodesDataSource(
    private val context: Context,
    private val apollo: ApolloClient
) :
    PagingSource<Int, EpisodeDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDetail> {
        val pageNumber = params.key ?: START_OFFSET

        if (!Utils.isNetConnected(context)) {
            return LoadResult.Error(Throwable(message = "No internet connected"))
        }

        return try {
            val episodesResponse =
                apollo.query(GetEpisodesQuery(page = Input.optional(pageNumber))).await()
            val episodeData = episodesResponse.data?.episodes
            if (episodesResponse.errors != null) {
                LoadResult.Error(Throwable(message = "${episodesResponse.errors?.first()}"))
            } else {
                val episodes =
                    episodeData?.results?.filterNotNull()?.map { it.fragments.episodeDetail }
                //      val prevKey = if (pageNumber == START_OFFSET) null else pageNumber - 1
                val prevKey = if (pageNumber > START_OFFSET) pageNumber - 1 else null
                val nextKey = episodeData?.info?.next
                LoadResult.Page(
                    data = episodes ?: emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(Throwable(message = e.localizedMessage ?: "Unexpected error occurred"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeDetail>): Int? {
        return null
    }
}
