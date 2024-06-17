package com.badal.shoppinglist

data class LocationData(val latitude: Double, val longitude: Double)

data class GeocodingResponse(val results: List<GeocodingResult>, val status: String)

data class GeocodingResult(val formated_address: String)
