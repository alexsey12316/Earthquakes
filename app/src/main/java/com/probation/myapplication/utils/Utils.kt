package com.probation.myapplication.utils

import android.content.res.Resources
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.probation.myapplication.App
import com.probation.myapplication.R
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.database.PerDate
import com.probation.myapplication.database.PerRange
import com.probation.myapplication.database.statisticsType
import com.probation.myapplication.network.earthquake.FeatureCollection
import java.text.SimpleDateFormat
import java.util.*

fun FeatureCollection.toEarthquakeList():List<Earthquake>
{
    val inFormatter=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    inFormatter.timeZone= TimeZone.getTimeZone("UTC")
    return features.map {
    Earthquake(it.properties.publicID,
    it.geometry.coordinates[0],
    it.geometry.coordinates[1],
    it.properties.depth,
    it.properties.locality,
    it.properties.magnitude,
    it.properties.mmi,
    it.properties.quality,
    inFormatter.parse(it.properties.time))
}
}

fun mmiToString(mmi:Int)=when(mmi)
{
    8->App.context.getString(R.string.extreme)
    7->App.context.getString(R.string.severe)
    6->App.context.getString(R.string.strong)
    5->App.context.getString(R.string.moderate)
    4->App.context.getString(R.string.light)
    3->App.context.getString(R.string.weak)
    else -> App.context.getString(R.string.unnoticeable)
}


fun Map<String,Int>.toListOfMMIPairs(type: statisticsType):List<PerRange>
{
      return this.map { PerRange(type, it.key.toInt(),it.value) }
}

fun Map<String,Int>.toListOfDatePairs():List<PerDate>
{
    val sdf=SimpleDateFormat("yyyy-MM-dd")
    return this.map { PerDate(sdf.parse(it.key),it.value) }
}

fun List<PerDate>.toListOfBarEntry():List<BarEntry>{
    return this.map { BarEntry(it.date.time.toFloat(),it.count.toFloat()) }
}

fun List<PerRange>.toListOfPieEntry():List<PieEntry>{

    val resList=ArrayList<PieEntry>(7)
    var firstCount=0f
    for (i in this)
        if(i.mmi<3)
            firstCount+=i.count
        else
            break
    resList.add(PieEntry(firstCount, mmiToString(0)))
    for (i in this)
        if(i.mmi<3)
            continue
        else
            resList.add(PieEntry(i.count.toFloat(),mmiToString(i.mmi)))
    return resList
}



