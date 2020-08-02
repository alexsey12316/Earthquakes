package com.probation.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EarthquakeDatabaseDao {

    @Query("select * from earthquake_table order by time desc")
    fun getAllEarthquakes():List<Earthquake>

    @Query("select * from earthquake_table where :mmi<=mmi order by time desc")
    fun getByMMI(mmi:Int):List<Earthquake>

    @Query("select * from earthquake_table where publicID = :key")
    fun get(key: String):Earthquake?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEarthquakes(list:List<Earthquake>)

    @Query("select * from per_range_table where type=:type")
    fun getRangeStatistics(type: statisticsType):List<PerRange>

    @Query("delete from per_range_table")
    fun deleteRangeStatistics()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertPerRange(list: List<PerRange>)

    @Query("select * from per_date_table")
    fun getYearDateStatistics():List<PerDate>

    @Query("delete from per_date_table")
    fun deleteYearDateStatistics()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertYearDate(list: List<PerDate>)


}