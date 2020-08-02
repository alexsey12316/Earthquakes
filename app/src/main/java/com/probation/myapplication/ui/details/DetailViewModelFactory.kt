package com.probation.myapplication.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewModelFactory(val publicID :String ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(publicID)as T
        else
            throw IllegalArgumentException("unknown view model")
    }
}