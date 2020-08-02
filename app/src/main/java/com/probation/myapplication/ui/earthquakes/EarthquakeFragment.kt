package com.probation.myapplication.ui.earthquakes

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.probation.myapplication.R
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.databinding.FragmentEarthquakesBinding
import kotlinx.android.synthetic.main.fragment_earthquakes.*

class EarthquakeFragment : Fragment() {

    private lateinit var earthquakeViewModel:EarthquakeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding:FragmentEarthquakesBinding=DataBindingUtil.inflate(
            inflater,R.layout.fragment_earthquakes,container,false)

        val application= requireNotNull(activity).application
        val viewModelFactory=EarthquakeViewModelFactory(application)
        earthquakeViewModel=ViewModelProvider(this,viewModelFactory).get(EarthquakeViewModel::class.java)


//        earthquakeViewModel.earthquakeList.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(context,"${earthquakeViewModel.earthquakeList.value?.size}",Toast.LENGTH_SHORT).show() //debug

//        })

        binding.apply {
            viewModel=earthquakeViewModel
            lifecycleOwner=viewLifecycleOwner
            earthquakeList.adapter=EarthquakeAdapter(EarthquakeClickListener {

//                Toast.makeText(context,"on item clicked",Toast.LENGTH_SHORT).show()
                findNavController().navigate(EarthquakeFragmentDirections.actionNavigationEarthquakesToDetailFragment(it))
            })
//            (earthquakeList.adapter as EarthquakeAdapter).submitList(viewModel.earthquakeList)

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.sort_item_all->earthquakeViewModel.changeType(EarthquakeType.All)
            R.id.sort_item_weak->earthquakeViewModel.changeType(EarthquakeType.Weak)
            R.id.sort_item_light->earthquakeViewModel.changeType(EarthquakeType.Light)
            R.id.sort_item_moderate->earthquakeViewModel.changeType(EarthquakeType.Moderate)
            R.id.sort_item_strong->earthquakeViewModel.changeType(EarthquakeType.Strong)
            R.id.sort_item_severe->earthquakeViewModel.changeType(EarthquakeType.Severe)
            R.id.sort_item_extreme->earthquakeViewModel.changeType(EarthquakeType.Extreme)
            else ->{}
        }
        return super.onOptionsItemSelected(item)
    }
}