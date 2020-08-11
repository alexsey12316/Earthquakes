package com.probation.myapplication.repository

import android.util.Log
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.database.EarthquakeDatabaseDao
import com.probation.myapplication.database.statisticsType
import com.probation.myapplication.network.EarthquakesApiService
import com.probation.myapplication.ui.earthquakes.EarthquakeType
import com.probation.myapplication.utils.toEarthquakeList
import com.probation.myapplication.utils.toListOfDatePairs
import com.probation.myapplication.utils.toListOfMMIPairs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EarthquakesRepository @Inject constructor(private val ApiService:EarthquakesApiService, private  val databaseDao:EarthquakeDatabaseDao)
{
    fun getEarthquakeList()=  databaseDao.getAllEarthquakes()
    fun getEarthquakeListByMMI(type:EarthquakeType)=  databaseDao.getByMMI(type.mmi)

    suspend fun quickRefreshEarthquakes()
    {
        withContext(Dispatchers.IO)
        {
            var list= ApiService.getEarthquakesAsync().await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
        }
    }

    suspend fun refreshEarthquakes()
    {
        withContext(Dispatchers.IO)
        {

            var list= ApiService.getEarthquakesAsync("8").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
            list= ApiService.getEarthquakesAsync("7").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
            list= ApiService.getEarthquakesAsync("6").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
            list= ApiService.getEarthquakesAsync("5").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
            list= ApiService.getEarthquakesAsync("4").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
            list= ApiService.getEarthquakesAsync("3").await().toEarthquakeList()
            databaseDao.insertAllEarthquakes(list)
        }
    }

    fun getRangeStatistics(type: statisticsType)=databaseDao.getRangeStatistics(type)
    fun getYearDateStatistics()=databaseDao.getYearDateStatistics()

    suspend fun refreshStatistics()
    {
        withContext(Dispatchers.IO)
        {
            databaseDao.deleteRangeStatistics()
            databaseDao.deleteYearDateStatistics()
            val statistic=ApiService.getStatisticsAsync().await()
            databaseDao.InsertPerRange(statistic.magnitudeCount.days7.toListOfMMIPairs(statisticsType.WEEK))
            databaseDao.InsertPerRange(statistic.magnitudeCount.days28.toListOfMMIPairs(statisticsType.MONTH))
            databaseDao.InsertPerRange(statistic.magnitudeCount.days365.toListOfMMIPairs(statisticsType.YEAR))
            databaseDao.InsertYearDate(statistic.rate.perDay.toListOfDatePairs())
        }

    }
}