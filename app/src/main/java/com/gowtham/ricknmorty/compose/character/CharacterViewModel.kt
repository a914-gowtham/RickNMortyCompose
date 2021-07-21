package com.gowtham.ricknmorty.compose.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import fragment.CharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _characterDetail = MutableStateFlow<Resource<CharacterDetail>>(Resource.Loading())
    val state: StateFlow<Resource<CharacterDetail>>
        get() = _characterDetail

    fun setCharacter(characterId: String) {
        if (_characterDetail.value is Resource.Success)
            return
        _characterDetail.value = Resource.Loading()
        viewModelScope.launch {
            _characterDetail.value = mainRepository.getCharacter(characterId)
        }
    }
}
