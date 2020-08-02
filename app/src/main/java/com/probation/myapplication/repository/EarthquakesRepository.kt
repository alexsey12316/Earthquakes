package com.probation.myapplication.repository

import android.util.Log
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.database.statisticsType
import com.probation.myapplication.network.EarthquakesApi
import com.probation.myapplication.ui.earthquakes.EarthquakeType
import com.probation.myapplication.utils.toEarthquakeList
import com.probation.myapplication.utils.toListOfDatePairs
import com.probation.myapplication.utils.toListOfMMIPairs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarthquakesRepository(private  val database:EarthquakeDatabase)
{
    fun getEarthquakeList()=  database.earthquakeDatabaseDao.getAllEarthquakes()
    fun getEarthquakeListByMMI(type:EarthquakeType)=  database.earthquakeDatabaseDao.getByMMI(type.mmi)

    suspend fun quickRefreshEarthquakes()
    {
        withContext(Dispatchers.IO)
        {
            var list= EarthquakesApi.retrofitServise.getEarthquakesAsync().await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
        }
    }

    suspend fun refreshEarthquakes()
    {
        withContext(Dispatchers.IO)
        {

            var list= EarthquakesApi.retrofitServise.getEarthquakesAsync("8").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
            list= EarthquakesApi.retrofitServise.getEarthquakesAsync("7").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
            list= EarthquakesApi.retrofitServise.getEarthquakesAsync("6").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
            list= EarthquakesApi.retrofitServise.getEarthquakesAsync("5").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
            list= EarthquakesApi.retrofitServise.getEarthquakesAsync("4").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
            list= EarthquakesApi.retrofitServise.getEarthquakesAsync("3").await().toEarthquakeList()
            database.earthquakeDatabaseDao.insertAllEarthquakes(list)
        }
    }

    fun getRangeStatistics(type: statisticsType)=database.earthquakeDatabaseDao.getRangeStatistics(type)
    fun getYearDateStatistics()=database.earthquakeDatabaseDao.getYearDateStatistics()

    suspend fun refreshStatistics()
    {
        withContext(Dispatchers.IO)
        {
            database.earthquakeDatabaseDao.deleteRangeStatistics()
            database.earthquakeDatabaseDao.deleteYearDateStatistics()
            val statistic=EarthquakesApi.retrofitServise.getStatisticsAsync().await()
            database.earthquakeDatabaseDao.InsertPerRange(statistic.magnitudeCount.days7.toListOfMMIPairs(statisticsType.WEEK))
            database.earthquakeDatabaseDao.InsertPerRange(statistic.magnitudeCount.days28.toListOfMMIPairs(statisticsType.MONTH))
            database.earthquakeDatabaseDao.InsertPerRange(statistic.magnitudeCount.days365.toListOfMMIPairs(statisticsType.YEAR))
            database.earthquakeDatabaseDao.InsertYearDate(statistic.rate.perDay.toListOfDatePairs())
        }

    }
}