package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import com.google.android.gms.maps.model.Marker
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    val repository = MarkerRepository()
    init {
        repository.add(MarkerEntity(LatLng(59.941688, 30.338012),"Чижик-пыжик" ))
        repository.add(MarkerEntity(LatLng(59.941698, 30.338012),"Чижик-пыжик2" ))
        repository.add(MarkerEntity(LatLng(59.941678, 30.338012),"Чижик-пыжик3" ))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val Piter = LatLng(59.941688, 30.338012)
//        val a = mMap.addMarker(MarkerOptions().position(Piter).title("Чижик-пыжик"))
        mMap.addAllMarkersEntites(repository.getAll())

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Piter, 10.0f))

        mMap.setOnMarkerClickListener(this)
    }


    override fun onMarkerClick(currentMarker: Marker?): Boolean {
        val results = repository.searchByTitle(currentMarker!!.title)
        results.forEach {
            val intent = Intent(this, MarkerActivity::class.java)
            intent.putExtra("position", it.position.toString())
            intent.putExtra("title", it.title)

            startActivity(intent)
        }

        return true
    }
}

fun GoogleMap.addAllMarkersEntites(list : ArrayList<MarkerEntity>){
    list.forEach {
        this.addMarker(MarkerOptions().position(it.position).title(it.title))
    }
}
