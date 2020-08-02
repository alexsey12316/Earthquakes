package com.probation.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

enum class statisticsType
{
    WEEK,MONTH,YEAR
}


@Entity(tableName = "earthquake_table")
data class Earthquake(
                      @PrimaryKey
                      val publicID: String,
                      val longitude:Double, //долгота
                      val latitude:Double, //широта
                      val depth: Double,
                      val locality: String,
                      val magnitude: Double,
                      val mmi: Int,
                      val quality: String,
                      val time: Date)
{
    private companion object{
        val format= SimpleDateFormat("hh:mm dd/MM/yyyy")
    }
    val strTime:String get() = format.format(time)
}



@Entity(tableName = "per_date_table")
data class PerDate(
    @PrimaryKey
    val date:Date,
    val count:Int
)

@Entity(tableName = "per_range_table",primaryKeys = ["type","mmi"] )
data class PerRange(
    val type:statisticsType,
    val mmi:Int,
    val count:Int
)
