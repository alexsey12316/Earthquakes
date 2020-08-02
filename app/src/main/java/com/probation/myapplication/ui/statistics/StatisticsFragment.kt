package com.probation.myapplication.ui.statistics

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.probation.myapplication.R
import com.probation.myapplication.databinding.FragmentStatisticsBinding
import com.probation.myapplication.utils.setData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class StatisticsFragment : Fragment() {

    private lateinit var sViewModel:StatisticsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding =FragmentStatisticsBinding.inflate(inflater)

        val application= requireNotNull(activity).application
        val factory=StatisticsViewModelFactory(application)
        sViewModel=ViewModelProvider(this,factory).get(StatisticsViewModel::class.java)

        binding.apply {
            chart.description.text=""
            chart.isHighlightPerTapEnabled=false
            chart.isHighlightPerDragEnabled=false
            chart.xAxis.valueFormatter=object : ValueFormatter()
            {
                val sdf=SimpleDateFormat("dd.MM.yyyy")

                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return sdf.format(value.toLong())
                }
            }

            pieChart.description.text=""
            pieChart.setDrawEntryLabels(false)
            pieChart.legend.isWordWrapEnabled=true
            pieChart.minAngleForSlices = 12f
            viewModel=sViewModel
            lifecycleOwner=viewLifecycleOwner
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.statistics_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menu_detail_year->sViewModel.changeChart(ShowChart.YEAR_DETAILS)
            R.id.menu_year->sViewModel.changeChart(ShowChart.YEAR)
            R.id.menu_month->sViewModel.changeChart(ShowChart.MONTH)
            R.id.menu_week->sViewModel.changeChart(ShowChart.WEEK)
            else->throw IllegalArgumentException("Unknown menu item")
        }
        return super.onOptionsItemSelected(item)
    }

}
