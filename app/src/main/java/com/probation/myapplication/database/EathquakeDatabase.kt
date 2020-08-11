package com.probation.myapplication.database

import android.content.Context
import androidx.room.*
import com.probation.myapplication.network.statistics.Statistics
import java.util.*

@Database (entities = [Earthquake::class,PerDate::class,PerRange::class],version = 1,exportSchema = false)
@TypeConverters(TimeConverters::class,TypeToIntConverter::class)
abstract class EarthquakeDatabase:RoomDatabase() {
//    abstract val earthquakeDatabaseDao:EarthquakeDatabaseDao
    abstract fun earthquakeDao():EarthquakeDatabaseDao

    companion object{
//        private var INSTANCE:EarthquakeDatabase?=null
        const val NAME="earthquake_database"

//        fun getInstance(context:Context):EarthquakeDatabase
//        {
//            synchronized(this)
//            {
//                var instance= INSTANCE
//
//                if(instance==null)
//                    instance= Room.databaseBuilder(context,EarthquakeDatabase::class.java,NAME)
//                        .fallbackToDestructiveMigration().build()
//                INSTANCE=instance
//                return instance
//            }
//
//        }
    }
}

