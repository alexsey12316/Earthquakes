package com.probation.myapplication.ui.details

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.network.EarthquakesApiService
//import com.probation.myapplication.network.EarthquakesApi
import com.probation.myapplication.utils.toEarthquakeList
import kotlinx.coroutines.*
import java.lang.Exception


class DetailViewModel @ViewModelInject constructor (
    @Assisted private val savedStateHandle: SavedStateHandle,
    private  val apiService: EarthquakesApiService
):ViewModel() {

//    private val api=
//        EarthquakesApi.retrofitServise
    private lateinit var googleMap: GoogleMap

    private lateinit var publicID:String


    private val _earthquake=MutableLiveData<Earthquake>()
    val earthquake:LiveData<Earthquake> get() = _earthquake

    private val job= Job()

    private val uiScope= CoroutineScope(Dispatchers.Main+job)

    private val _isNetErrorShow=MutableLiveData<Boolean>(false)
    val isNetErrorShow:LiveData<Boolean> get() = _isNetErrorShow


    private fun getNetworkData() {
        uiScope.launch {
            try {
                val list = apiService.getByPublicIDAsync(publicID).await().toEarthquakeList()
                _earthquake.value = if (list.isNullOrEmpty())
                    null
                else
                    list[0]

                _earthquake.value?.let {
                    val homeLatLng = LatLng(it.latitude, it.longitude)
                    googleMap.addMarker(MarkerOptions().position(homeLatLng))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))
                }

            } catch (e: Exception) {
                Log.d("GOOGLE_MAP", "", e)
                _isNetErrorShow.value = true
            }
        }
    }

    fun setGoogleMap(map: GoogleMap)
    {
        googleMap=map
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds( LatLng(-48.0, 165.0), LatLng(-33.0, 180.0)),0))
    }

    fun setPublicID(publicID:String)
    {
        this.publicID=publicID
        getNetworkData()
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}