package com.gowtham.ricknmorty.compose.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import fragment.EpisodeDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.dsl.module

val episodeViewModel = module {
    factory { EpisodeViewModel(get()) }
}

class EpisodeViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _episodeDetail = MutableStateFlow<Resource<EpisodeDetail>>(Resource.Loading())
    val state: StateFlow<Resource<EpisodeDetail>>
        get() = _episodeDetail

    fun setEpisodeId(episodeId: String) {
        if (_episodeDetail.value is Resource.Success)
            return
        _episodeDetail.value = Resource.Loading()
        viewModelScope.launch {
            _episodeDetail.value = mainRepository.getEpisode(episodeId)
        }
    }
}
