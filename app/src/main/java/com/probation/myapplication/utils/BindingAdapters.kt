package com.probation.myapplication.utils

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.probation.myapplication.App
import com.probation.myapplication.R
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.ui.earthquakes.EarthquakeAdapter
import com.probation.myapplication.ui.statistics.ShowChart
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("ListData")
fun RecyclerView.ListData(list:List<Earthquake>?)
{
    val adapter=this.adapter as EarthquakeAdapter
    adapter.submitList(list)
}

@BindingAdapter ("earthquakeColor")
fun ImageView.earthquakeColor(mmi:Int)
{
    this.background=when(mmi)
    {
        3->resources.getDrawable(R.color.weakEarthquake)
        4->resources.getDrawable(R.color.lightEarthquake)
        5->resources.getDrawable(R.color.moderateEarthquake)
        6->resources.getDrawable(R.color.strongEarthquake)
        7->resources.getDrawable(R.color.severeEarthquake)
        8->resources.getDrawable(R.color.extremeEarthquake)
        else->resources.getDrawable(R.color.unnoticeableEarthquake)

    }
}

@BindingAdapter ("earthquakeTypeText")
fun TextView.earthquakeTypeText(type:Int)
{
    text=when(type)
    {
        3->resources.getString(R.string.weakEarthquake)
        4->resources.getString(R.string.lightEarthquake)
        5->resources.getString(R.string.moderateEarthquake)
        6->resources.getString(R.string.strongEarthquake)
        7->resources.getString(R.string.severeEarthquake)
        8->resources.getString(R.string.extremeEarthquake)
        else->resources.getString(R.string.unnoticeableEarthquake)
    }
}

@BindingAdapter("barData")
fun HorizontalBarChart.setData(list : List<BarEntry>?)
{
    list?.let {
        val dataSet=BarDataSet(list,App.context.getString(R.string.bar_chart_description))
        val data=BarData(dataSet)
        data.barWidth=100000000f //Костыль
        this.data=data
        this.notifyDataSetChanged()
        this.invalidate()
    }
}

@BindingAdapter("pieData")
fun PieChart.setData(list : List<PieEntry>?)
{
    list?.let {
    val dataSet= PieDataSet(list,"")
    dataSet.colors= listOf(
        ContextCompat.getColor(App.context,R.color.unnoticeableEarthquake),
        ContextCompat.getColor(App.context,R.color.weakEarthquake),
        ContextCompat.getColor(App.context,R.color.lightEarthquake),
        ContextCompat.getColor(App.context,R.color.moderateEarthquake),
        ContextCompat.getColor(App.context,R.color.strongEarthquake),
        ContextCompat.getColor(App.context,R.color.severeEarthquake),
        ContextCompat.getColor(App.context,R.color.extremeEarthquake))

        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.valueLinePart1OffsetPercentage = 100f /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
        dataSet.valueLinePart1Length = 0.5f /** When valuePosition is OutsideSlice, indicates length of first half of the line */
        dataSet.valueLinePart2Length = 0.1f

        dataSet.sliceSpace=3f
        dataSet.selectionShift=40f

        val data=PieData(dataSet)
        this.data=data
        this.invalidate()
    }
}

@BindingAdapter("centerText")
fun PieChart.circleCenterText(type:ShowChart?)
{
    type?.let {
        when(type)
        {
            ShowChart.WEEK->this.centerText=resources.getString(R.string.for_week)
            ShowChart.MONTH->this.centerText=resources.getString(R.string.for_month)
            ShowChart.YEAR->this.centerText=resources.getString(R.string.for_year)
            else->{}
        }

    }
}

