package com.askjeffreyliu.mapgradientpolyline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.askjeffreyliu.gradientpolyline.GradientPolyline

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val taipei = LatLng(25.030076, 121.536324)

        val latLngList = mutableListOf<LatLng>()
        latLngList.add(taipei)
        latLngList.add(sydney)

        val colorStart = ContextCompat.getColor(this, R.color.colorAccent)
        val colorEnd = ContextCompat.getColor(this, R.color.colorPrimary)

        val gradientPolyline = GradientPolyline.Builder()
            .map(mMap)
            .add(latLngList)
            .width(10f)
            .geodesic(false)
            .colors(colorStart, colorEnd)
            .build()


        val la = LatLng(34.048850, -118.403659)
        val newZealand = LatLng(-41.901445, 172.854370)

        val latLngList2 = mutableListOf<LatLng>()
        latLngList2.add(newZealand)
        latLngList2.add(la)

        val gradientPolyline2 = GradientPolyline.Builder()
            .map(mMap)
            .add(latLngList2)
            .width(15f)
            .geodesic(true)
            .colors(colorStart, colorEnd)
            .build()
    }
}
