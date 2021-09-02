package com.gowtham.ricknmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gowtham.ricknmorty.domain.CharacterUseCase
import com.gowtham.ricknmorty.domain.EpisodeUseCase
import com.gowtham.ricknmorty.domain.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    characterUserCase: CharacterUseCase,
    episodeUseCase: EpisodeUseCase,
    locationUseCase: LocationUseCase
) :
    ViewModel() {

    companion object {
        const val DURATION = 2000L
    }

    private val _splashState = MutableStateFlow(true)
    val splash: StateFlow<Boolean>
        get() = _splashState

    init {
        viewModelScope.launch {
            delay(DURATION)
            _splashState.value = false
        }
    }

    val characters = characterUserCase.getCharacters().cachedIn(viewModelScope)

    val episodes = episodeUseCase.getEpisodes().cachedIn(viewModelScope)

    val locations = locationUseCase.getLocations().cachedIn(viewModelScope)
}
