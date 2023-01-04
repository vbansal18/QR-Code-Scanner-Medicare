package com.example.anveshnaqrscanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anveshnaqrscanner.model.MedicineDetails
import com.example.anveshnaqrscanner.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun postMedDetails(medicineDetails: MedicineDetails): MutableLiveData<String> {
        val _liveData = MutableLiveData<String>()

        viewModelScope.launch {
            repository.postMedDetails(medicineDetails)
                .flowOn(Dispatchers.IO)
                .catch { _ ->
                    _liveData.postValue("Unstable Internet Connection")
                }
                .collect {
                    _liveData.postValue("Medicine added successfully")
                }
        }
        return _liveData
    }
}