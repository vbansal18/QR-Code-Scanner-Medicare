package com.example.anveshnaqrscanner.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {
    fun providesAPIService(): APIService {
        val BASE_URL = "https://cqpxn4hibj.execute-api.ap-south-1.amazonaws.com"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}