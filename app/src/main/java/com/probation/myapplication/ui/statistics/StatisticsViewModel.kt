package com.probation.myapplication.ui.statistics

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.snackbar.Snackbar
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.database.statisticsType
import com.probation.myapplication.repository.EarthquakesRepository
import com.probation.myapplication.utils.toListOfBarEntry
import com.probation.myapplication.utils.toListOfPieEntry
import kotlinx.coroutines.*
import java.lang.Exception

enum class ShowChart
{
    WEEK,MONTH,YEAR,YEAR_DETAILS
}


class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository=EarthquakesRepository(EarthquakeDatabase.getInstance(application))

    private val job= Job()

    private val uiScope= CoroutineScope(Dispatchers.Main+job)

    private val _isBarChart=MutableLiveData<Boolean>(true)
    val isBarChart:LiveData<Boolean> get()=_isBarChart

    private val _type=MutableLiveData<ShowChart>(ShowChart.YEAR_DETAILS)
    val type:LiveData<ShowChart> get() = _type

    private val _barListEntry=MutableLiveData<List<BarEntry>>()
    val barListEntry:LiveData<List<BarEntry>> get() = _barListEntry

    private val _pieListEntry=MutableLiveData<List<PieEntry>>()
    val pieListEntry:LiveData<List<PieEntry>> get() = _pieListEntry

    private val _isRefreshing=MutableLiveData<Boolean>()
    val isRefreshing:LiveData<Boolean> get()=_isRefreshing

    init {
        loadDataAndCheck()
    }

    private fun refresh()
    {
        uiScope.launch {
            _isRefreshing.value=true
            try {
                repository.refreshStatistics()
            }
            catch (e:Exception)
            {
                Toast.makeText(getApplication(),e.message,Toast.LENGTH_SHORT).show()
            }
            loadData()
            _isRefreshing.value=false
        }
    }

    private fun loadData() {

        uiScope.launch {

            if(_type.value==ShowChart.YEAR_DETAILS)
            {
                _barListEntry.value= withContext(Dispatchers.IO)
                {
                    repository.getYearDateStatistics().toListOfBarEntry()
                }
            }
            else
            {
                _pieListEntry.value= withContext(Dispatchers.IO)
                {
                    repository.getRangeStatistics(when(_type.value){
                        ShowChart.YEAR->statisticsType.YEAR
                        ShowChart.MONTH->statisticsType.MONTH
                        ShowChart.WEEK->statisticsType.WEEK
                        else ->throw IllegalArgumentException("im pretty sure this is impossible")
                    }).toListOfPieEntry()

                }
        }

        }
    }

    private fun loadDataAndCheck()
    {
        uiScope.launch {

            if(_type.value==ShowChart.YEAR_DETAILS)
            {
                _barListEntry.value= withContext(Dispatchers.IO)
                {
                    repository.getYearDateStatistics().toListOfBarEntry()
                }
                if(_barListEntry.value.isNullOrEmpty())
                    refresh()
            }
            else
            {
                _pieListEntry.value= withContext(Dispatchers.IO)
                {
                    repository.getRangeStatistics(when(_type.value){
                        ShowChart.YEAR->statisticsType.YEAR
                        ShowChart.MONTH->statisticsType.MONTH
                        ShowChart.WEEK->statisticsType.WEEK
                        else ->throw IllegalArgumentException("im pretty sure this is impossible")
                    }).toListOfPieEntry()

                }
                if(_pieListEntry.value.isNullOrEmpty())
                    refresh()
            }

        }
    }


    fun onSwipe()
    {
        refresh()
    }

    fun changeChart(type: ShowChart){
        if (this._type.value==type)
            return
        this._type.value=type
        _isBarChart.value=type==ShowChart.YEAR_DETAILS
        loadData()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}