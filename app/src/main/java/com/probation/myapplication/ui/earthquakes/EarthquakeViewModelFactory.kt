package com.probation.myapplication.ui.earthquakes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

//class EarthquakeViewModelFactory(val application: Application):ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(EarthquakeViewModel::class.java))
//            return EarthquakeViewModel(application) as T
//        throw IllegalArgumentException("Unknown view model")
//    }
//
//}