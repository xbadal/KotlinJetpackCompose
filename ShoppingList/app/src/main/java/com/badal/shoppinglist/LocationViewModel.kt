package com.badal.shoppinglist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(locationData: LocationData) {
        _location.value = locationData
    }

    fun fetchAddress(latlng: String) {

        try {

            viewModelScope.launch {

                val result = RetrofitClient.create()
                    .getAddressFromCoordinate(latlng, "Google API KEY")

                _address.value = result.results
            }
        } catch (e: Exception) {
            Log.d("res1", e.message.toString())
        }
    }
}