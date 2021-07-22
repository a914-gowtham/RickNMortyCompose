package com.gowtham.ricknmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class MainViewModel(
    mainRepository: MainRepository
) :
    ViewModel() {

    val characters = mainRepository.getCharacterFlow().cachedIn(viewModelScope)

    val episodes = mainRepository.getEpisodeFlow().cachedIn(viewModelScope)

    val locations = mainRepository.getLocationFlow().cachedIn(viewModelScope)
}
