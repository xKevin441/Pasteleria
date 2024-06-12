package com.edu.pasteleria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class contactosActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    //private lateinit var binding: ActivityMapsBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contactos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mapView = findViewById(R.id.mapView1)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val Principal = LatLng(-19.049209, -65.259841)
        mMap.addMarker(MarkerOptions().position(Principal).title("Pasteleria principal"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Principal, 14f))

        val Sucursal1 = LatLng(-19.046419, -65.252256)
        mMap.addMarker(MarkerOptions().position(Sucursal1).title("CupCakes Sucursal"))

        val Sucursal2 = LatLng(-19.043374, -65.263479)
        mMap.addMarker(MarkerOptions().position(Sucursal2).title("CupCakes sucursal"))

        val Sucursal3 = LatLng(-19.045108, -65.257826)
        mMap.addMarker(MarkerOptions().position(Sucursal3).title("CupCakes Sucursal"))

        val Sucursal4 = LatLng(-19.047087, -65.263557)
        mMap.addMarker(MarkerOptions().position(Sucursal4).title("CupCakes Sucursal"))
    }
}