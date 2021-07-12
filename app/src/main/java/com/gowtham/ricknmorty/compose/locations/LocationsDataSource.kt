package com.gowtham.ricknmorty.compose.locations

import GetLocationsQuery
import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.gowtham.ricknmorty.utils.Utils
import fragment.LocationDetail

private const val START_OFFSET = 0

class LocationsDataSource(
    private val context: Context,
    private val apollo: ApolloClient
) :
    PagingSource<Int, LocationDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDetail> {
        val pageNumber = params.key ?: START_OFFSET

        if (!Utils.isNetConnected(context)) {
            return LoadResult.Error(Throwable(message = "No internet connected"))
        }

        return try {
            val locationsResponse =
                apollo.query(GetLocationsQuery(page = Input.optional(pageNumber))).await()
            val locationData = locationsResponse.data?.locations
            if (locationsResponse.errors != null) {
                LoadResult.Error(Throwable(message = "${locationsResponse.errors?.first()}"))
            } else {
                val locations =
                    locationData?.results?.filterNotNull()?.map { it.fragments.locationDetail }
                val prevKey = if (pageNumber > START_OFFSET) pageNumber - 1 else null
                val nextKey = locationData?.info?.next
                LoadResult.Page(
                    data = locations ?: emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(Throwable(message = "UnExcepted error occurred"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationDetail>): Int? {
        return null
    }
}
