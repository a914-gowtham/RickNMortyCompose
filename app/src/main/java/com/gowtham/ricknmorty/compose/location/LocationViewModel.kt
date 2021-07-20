package com.gowtham.ricknmorty.compose.location

import android.content.Context
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.gowtham.ricknmorty.MainRepository
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import fragment.LocationDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    apolloClient: ApolloClient,
    @ApplicationContext appContext: Context,
) : ViewModel() {

    private val _locationDetail = MutableStateFlow<Resource<LocationDetail>>(Resource.Loading())
    val state: StateFlow<Resource<LocationDetail>>
        get() = _locationDetail

    private var mainRepository: MainRepository = MainRepository(
        appContext = appContext,
        apolloClient = apolloClient
    )

    suspend fun setLocation(locationId: String) {
        _locationDetail.value = Resource.Loading()
        _locationDetail.value = mainRepository.getLocation(locationId)
    }
}
