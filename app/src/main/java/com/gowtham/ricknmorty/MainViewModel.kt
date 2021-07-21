package com.gowtham.ricknmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) :
    ViewModel() {

    val characters = mainRepository.getCharacterFlow().cachedIn(viewModelScope)

    val episodes = mainRepository.getEpisodeFlow().cachedIn(viewModelScope)

    val locations = mainRepository.getLocationFlow().cachedIn(viewModelScope)
}
