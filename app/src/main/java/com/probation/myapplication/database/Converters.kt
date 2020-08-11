package com.probation.myapplication.database

import androidx.room.TypeConverter
import java.util.*

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