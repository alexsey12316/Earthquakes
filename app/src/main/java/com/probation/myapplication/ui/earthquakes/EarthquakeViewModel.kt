package com.probation.myapplication.ui.earthquakes

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.database.EarthquakeDatabaseDao
import com.probation.myapplication.repository.EarthquakesRepository
import kotlinx.coroutines.*
import java.lang.Exception

 enum class EarthquakeType(val mmi:Int)
{
    All(-1),Weak(3),Light(4),Moderate(5),
    Strong(6),Severe(7),Extreme(8)
}

class EarthquakeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository=EarthquakesRepository(EarthquakeDatabase.getInstance(application))
    private val _earthquakeList=MutableLiveData<List<Earthquake>>()
    val earthquakeList:LiveData<List<Earthquake>> get()=_earthquakeList

    private val job= Job()
    private val refreshScope= CoroutineScope(Dispatchers.Main+job)

    private var type=EarthquakeType.All


    init {
        refreshScope.launch {
            _earthquakeList.value= withContext(Dispatchers.IO){ repository.getEarthquakeList()}
            if(_earthquakeList.value.isNullOrEmpty())
                refresh()
        }
    }

    private fun refresh()=refreshScope.launch {
        _isRefreshing.value=true
        try
        {
            repository.quickRefreshEarthquakes()
            _earthquakeList.value= withContext(Dispatchers.IO){ repository.getEarthquakeListByMMI(type)}
            repository.refreshEarthquakes()
        }
        catch (e:Exception)
        {
            Toast.makeText(getApplication(),"${e.message}",Toast.LENGTH_LONG).show()
        }
        _earthquakeList.value= withContext(Dispatchers.IO){ repository.getEarthquakeListByMMI(type)}
        _isRefreshing.value=false
    }

    private val _isRefreshing=MutableLiveData<Boolean>()
    val isRefreshing:LiveData<Boolean> get()=_isRefreshing

    fun onSwipe()
    {
        refresh()
    }

    fun changeType(type: EarthquakeType)
    {
        if(this.type!=type)
        {
            this.type=type
            reload()
        }

    }

    private fun reload()
    {
        refreshScope.launch {
            _earthquakeList.value= withContext(Dispatchers.IO){
                repository.getEarthquakeListByMMI(type)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}