package com.probation.myapplication.ui.earthquakes

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.database.EarthquakeDatabaseDao
import com.probation.myapplication.repository.EarthquakesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.lang.Exception

 enum class EarthquakeType(val mmi:Int)
{
    All(-1),Weak(3),Light(4),Moderate(5),
    Strong(6),Severe(7),Extreme(8)
}

class EarthquakeViewModel
@ViewModelInject constructor (
    private val repository:EarthquakesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
)
    : ViewModel() {

//    private val repository=EarthquakesRepository(EarthquakeDatabase.getInstance(application))
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
            repository.refreshEarthquakes()
        }
        catch (e:Exception)
        {
            Toast.makeText(context,"${e.message}",Toast.LENGTH_LONG).show()
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