package com.gowtham.ricknmorty

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.apollographql.apollo.ApolloClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    apolloClient: ApolloClient,
    @ApplicationContext context: Context
) :
    ViewModel() {

    private var mainRepository: MainRepository = MainRepository(
        appContext = context,
        apolloClient = apolloClient
    )

    val characters = mainRepository.characters.cachedIn(viewModelScope)

    val episodes = mainRepository.episodes.cachedIn(viewModelScope)

    val locations = mainRepository.locations.cachedIn(viewModelScope)

}
