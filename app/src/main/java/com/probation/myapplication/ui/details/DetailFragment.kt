package com.probation.myapplication.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.probation.myapplication.R
import com.probation.myapplication.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment:Fragment()
{
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding=DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,R.layout.fragment_detail,container,false)

        val publicID=DetailFragmentArgs.fromBundle(requireArguments()).publicID
        val factory=DetailViewModelFactory(publicID)
        detailViewModel=ViewModelProvider(this,factory).get(DetailViewModel::class.java)

        binding.apply {
            this.viewModel=detailViewModel
            lifecycleOwner=viewLifecycleOwner

        }
//        Toast.makeText(context,publicID,Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync{
            it?.let {
                detailViewModel.setGoogleMap(it)
            }
        }
        super.onActivityCreated(savedInstanceState)

    }



    override fun onStart() {
        mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }




}