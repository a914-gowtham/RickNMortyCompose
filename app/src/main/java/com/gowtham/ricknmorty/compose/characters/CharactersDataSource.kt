package com.gowtham.ricknmorty.compose.characters

import GetCharactersQuery
import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.gowtham.ricknmorty.utils.Utils
import fragment.CharacterDetail
import kotlinx.coroutines.delay

private const val START_OFFSET = 0

class CharactersDataSource(
    private val context: Context,
    private val apollo: ApolloClient
) :
    PagingSource<Int, CharacterDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetail> {
        val pageNumber = params.key ?: START_OFFSET

        if (!Utils.isNetConnected(context))
            return LoadResult.Error(Throwable(message = "No internet connected"))
        return try {
            val charactersResponse =
                apollo.query(GetCharactersQuery(page = Input.optional(pageNumber))).await()
            val characterData = charactersResponse.data?.characters
            if (charactersResponse.errors != null)
                LoadResult.Error(Throwable(message = "${charactersResponse.errors?.first()}"))
            else {
                val characters =
                    characterData?.results?.filterNotNull()?.map { it.fragments.characterDetail }
                //      val prevKey = if (pageNumber == START_OFFSET) null else pageNumber - 1
                val prevKey = if (pageNumber > START_OFFSET) pageNumber - 1 else null
                val nextKey = characterData?.info?.next
                LoadResult.Page(
                    data = characters ?: emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(Throwable(message = "Excepted error occured"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDetail>): Int? {
        return null
    }
}
