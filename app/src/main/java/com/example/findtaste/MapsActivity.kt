package com.example.findtaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findtaste.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var currentMarker: Marker
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var type = intent.getStringExtra("type")
        lat = intent.getDoubleExtra("lat", 0.0)
        lng = intent.getDoubleExtra("lng", 0.0)

        binding.mapsFoodType.setText("Categoría: ${type}")
        binding.noCommerces.setText("Lo sentimos! No se encuentra ningún restaurante de esta categoria")

        /*
        val commerces: List<Commerce> = //API RECOLECT ALL RESTAURANTS WITH THE SPECIFIC TYPE

        if (commerces.isEmpty()){
            binding.recyclerFavoriteCommerces.visibility = View.INVISIBLE
            binding.noFavoriteCommerces.visibility = View.VISIBLE
        }else{
            binding.recyclerFavoriteCommerces.visibility = View.VISIBLE
            binding.noFavoriteCommerces.visibility = View.GONE

            binding.recyclerFavoriteCommerces.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerFavoriteCommerces.adapter = RecyclerFavoriteCommerces(
                requireContext(),
                commerces
            )
        }
         */

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val zoomLevel = 11f

        val current = LatLng(lat, lng)
        currentMarker = mMap.addMarker(MarkerOptions()
            .position(current)
            .title("Tú")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoomLevel))
    }

}