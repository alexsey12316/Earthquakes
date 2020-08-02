package com.probation.myapplication

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.probation.myapplication.database.EarthquakeDatabase
import com.probation.myapplication.network.EarthquakesApi
import com.probation.myapplication.network.earthquake.FeatureCollection
import com.probation.myapplication.network.statistics.Statistics
import com.probation.myapplication.repository.EarthquakesRepository
import kotlinx.coroutines.*
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
private const val TAG="DEBUG_TEST"


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var job:Job
    private lateinit var networkScope:CoroutineScope

    @Before
    fun loadSetup()
    {
        job= Job()
        networkScope= CoroutineScope(Dispatchers.Main+job)
    }

    @Test
     fun loadData()
    {
        Log.d(TAG,"Start")

        lateinit var list: FeatureCollection
        networkScope.launch {
            val getEarthquake=
                EarthquakesApi.retrofitServise.getByPublicIDAsync("2020p566075")
            try {
                Log.d(TAG,"Start loading")
                list=getEarthquake.await()
                Log.d(TAG,"Done loading")
                for (i in list.features)
                    Log.d(TAG,i.toString())
            }
            catch (e:Exception)
            {
                Log.d(TAG,"Error: ${e.message}")
            }

        }
        var a=0
        for(i in Long.MIN_VALUE..Long.MAX_VALUE)
            a=0

        Log.d(TAG,"End")
    }

    @Test
    fun loadStatistics()
    {
        Log.d(TAG,"Start")

        lateinit var stats: Statistics
        networkScope.launch {
            val getStatistics=EarthquakesApi.retrofitServise.getStatisticsAsync()
            try {
                Log.d(TAG,"Start loading")
                stats=getStatistics.await()
                Log.d(TAG,"Done loading")
                Log.d(TAG,"7 days")
                for (i in stats.magnitudeCount.days7)
                    Log.d(TAG,i.toString())
                Log.d(TAG,"28 days")
                for (i in stats.magnitudeCount.days28)
                    Log.d(TAG,i.toString())
                Log.d(TAG,"365 days")
                for (i in stats.magnitudeCount.days365)
                    Log.d(TAG,i.toString())
                Log.d(TAG,"YEAR")
                for (i in stats.rate.perDay)
                    Log.d(TAG,i.toString())

            }
            catch (e:Exception)
            {
                Log.d(TAG,"Error: ${e.message}")
            }

        }
        var a=0
        for(i in Long.MIN_VALUE..Long.MAX_VALUE)
            a=0

        Log.d(TAG,"End")
    }

    @Test
    fun CheckRepository()
    {
        Log.d(TAG,"Start")

         var repository=EarthquakesRepository(EarthquakeDatabase.getInstance(
            InstrumentationRegistry.getInstrumentation().targetContext))
        networkScope.launch {
            val getStatistics=EarthquakesApi.retrofitServise.getStatisticsAsync()
            try {

                repository.refreshStatistics()
                Log.d(TAG,"Done")

            }
            catch (e:Exception)
            {
                Log.d(TAG,"Error: ${e.message}")
            }

        }
        var a=0
        for(i in Long.MIN_VALUE..Long.MAX_VALUE)
            a=0

        Log.d(TAG,"End")
    }

    @After
    fun clearConnect()
    {
//        job.join()
//        job.cancel()
    }


}