package com.gowtham.ricknmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
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

    val characters = mainRepository.getCharacterFlow().cachedIn(viewModelScope)

    val episodes = mainRepository.getEpisodeFlow().cachedIn(viewModelScope)

    val locations = mainRepository.getLocationFlow().cachedIn(viewModelScope)
}
