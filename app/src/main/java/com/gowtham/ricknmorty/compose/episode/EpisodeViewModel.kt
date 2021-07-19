package com.gowtham.ricknmorty.compose.episode

import android.content.Context
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import fragment.CharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
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

    suspend fun setCharacter(characterId: String) {
        _characterDetail.value=Resource.Loading()
        _characterDetail.value = mainRepository.getCharacter(characterId)
    }
}
