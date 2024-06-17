package com.badal.shoppinglist

import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApiService {

    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinate(
        @Query("latlng") latLng: String,
        @Query("key") key: String
    ): GeocodingResponse

}