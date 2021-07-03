package com.gowtham.ricknmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.apollographql.apollo.ApolloClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(apolloClient: ApolloClient) :
    ViewModel() {

    private var mainRepository: MainRepository = MainRepository(apolloClient = apolloClient)

    val characters = mainRepository.characters.cachedIn(viewModelScope)
}
