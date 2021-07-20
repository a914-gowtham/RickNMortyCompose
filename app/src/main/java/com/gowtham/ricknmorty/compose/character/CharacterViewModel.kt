package com.gowtham.ricknmorty.compose.character

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import fragment.CharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    apolloClient: ApolloClient,
    @ApplicationContext appContext: Context,
) : ViewModel() {

    private val _characterDetail = MutableStateFlow<Resource<CharacterDetail>>(Resource.Loading())
    val state: StateFlow<Resource<CharacterDetail>>
        get() = _characterDetail

    private var mainRepository: MainRepository = MainRepository(
        appContext = appContext,
        apolloClient = apolloClient
    )

    fun setCharacter(characterId: String) {
        _characterDetail.value = Resource.Loading()
        viewModelScope.launch {
            _characterDetail.value = mainRepository.getCharacter(characterId)
        }
    }
}
