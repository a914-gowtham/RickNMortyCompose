package com.gowtham.ricknmorty.presentation.compose.location

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.ricknmorty.domain.LocationUseCase
import com.gowtham.ricknmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import fragment.LocationDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _locationDetail = MutableStateFlow<Resource<LocationDetail>>(Resource.Loading())
    val state: StateFlow<Resource<LocationDetail>>
        get() = _locationDetail

    fun setLocation(context: Context, locationId: String) {
        if (_locationDetail.value is Resource.Success)
            return
        _locationDetail.value = Resource.Loading()
        viewModelScope.launch {
            _locationDetail.value = locationUseCase.getLocation(context,locationId)
        }
    }
}
