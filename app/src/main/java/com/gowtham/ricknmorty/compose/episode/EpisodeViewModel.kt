package com.gowtham.ricknmorty.compose.episode

import android.content.Context
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import fragment.EpisodeDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    apolloClient: ApolloClient,
    @ApplicationContext appContext: Context,
) : ViewModel() {

    private val _episodeDetail = MutableStateFlow<Resource<EpisodeDetail>>(Resource.Loading())
    val state: StateFlow<Resource<EpisodeDetail>>
        get() = _episodeDetail

    private var mainRepository: MainRepository = MainRepository(
        appContext = appContext,
        apolloClient = apolloClient
    )

    suspend fun setEpisodeId(episodeId: String) {
        _episodeDetail.value = Resource.Loading()
        _episodeDetail.value = mainRepository.getEpisode(episodeId)
    }
}
