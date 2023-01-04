package com.example.anveshnaqrscanner.network

import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @POST("/APImedicine")
    suspend fun postMedDetails(
        @Query("Name") Name: String,
        @Query("Description") Description: String,
        @Query("ExpiryDate") ExpiryDate: String,
        @Query("Manufacturer") Manufacturer: String,
        @Query("MFGDate") MFGDate: String,
        @Query("Price") Price: String,
        @Query("Quantity") Quantity: String,
    ): String

}