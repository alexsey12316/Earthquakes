package com.probation.myapplication.database

import android.content.Context
import androidx.room.*
import com.probation.myapplication.network.statistics.Statistics
import java.util.*

@Database (entities = [Earthquake::class,PerDate::class,PerRange::class],version = 1,exportSchema = false)
@TypeConverters(TimeConverters::class,TypeToIntConverter::class)
abstract class EarthquakeDatabase:RoomDatabase() {
    abstract val earthquakeDatabaseDao:EarthquakeDatabaseDao

    companion object{
        private var INSTANCE:EarthquakeDatabase?=null

        fun getInstance(context:Context):EarthquakeDatabase
        {
            synchronized(this)
            {
                var instance= INSTANCE

                if(instance==null)
                    instance= Room.databaseBuilder(context,EarthquakeDatabase::class.java,"earthquake_database")
                        .fallbackToDestructiveMigration().build()
                INSTANCE=instance
                return instance
            }

        }
    }
}

class TimeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

class TypeToIntConverter{
    @TypeConverter
    fun fromTypeToInt(type:statisticsType?):Long?=type.let { when(type){
        statisticsType.WEEK->0
        statisticsType.MONTH->1
        statisticsType.YEAR->2
        else -> throw IllegalArgumentException("unknown type")
    }}

    @TypeConverter
    fun fromIntToType(value: Long?):statisticsType?=value.let {
        when(value)
        {
            0L->statisticsType.WEEK
            1L->statisticsType.MONTH
            2L->statisticsType.YEAR
            else -> throw IllegalArgumentException("unknown type")
        }
    }

}