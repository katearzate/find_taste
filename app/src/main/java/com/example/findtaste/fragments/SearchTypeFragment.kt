package com.example.findtaste.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findtaste.R
import com.example.findtaste.databinding.FragmentSearchTypeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SearchTypeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSearchTypeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTypeBinding.inflate(layoutInflater)

        /*
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.searchTypeMap)
        mapFragment.getMapAsync(this)
        */
        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val morelia = LatLng(19.7, -101.2)
        mMap.addMarker(MarkerOptions().position(morelia).title("Marca en Morelia!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(morelia))
    }

}