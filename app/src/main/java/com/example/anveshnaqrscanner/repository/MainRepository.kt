package com.example.anveshnaqrscanner.repository

import com.example.anveshnaqrscanner.model.MedicineDetails
import com.example.anveshnaqrscanner.network.APIService
import com.example.anveshnaqrscanner.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepository(private val apiService: APIService = RetrofitBuilder.providesAPIService()) {

    fun postMedDetails(medicineDetails: MedicineDetails): Flow<String> = flow {
        emit(
            apiService.postMedDetails(
                medicineDetails.Name,
                medicineDetails.Description,
                medicineDetails.ExpiryDate,
                medicineDetails.Manufacturer,
                medicineDetails.MFGDate,
                medicineDetails.Price,
                medicineDetails.Quantity
            )
        )
    }
}