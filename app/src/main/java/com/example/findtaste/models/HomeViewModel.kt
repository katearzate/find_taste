package com.example.findtaste.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(){
    private val lat = MutableLiveData<Double>()
    private var lng = MutableLiveData<Double>()

    fun getLat(): LiveData<Double?>? {
        return lat
    }

    fun setLat(latitude: Double?) {
        latitude?.let {
            lat.value = it
        }
    }

    fun getLng(): LiveData<Double?>? {
        return lng
    }

    fun setLng(longitude: Double?) {
        longitude?.let {
            lng.value = it
        }
    }
}