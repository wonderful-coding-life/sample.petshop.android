package com.example.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val jsonString = this@MainActivity.assets.open("pharmacy.json").bufferedReader().readText()
            val jsonType = object : TypeToken<PharmacyInfo>() {}.type
            val pharmacyInfo = Gson().fromJson(jsonString, jsonType) as PharmacyInfo
            pharmacyInfo.pharmacy.forEach {
                googleMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.name))
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(pharmacyInfo.pharmacy[0].latitude, pharmacyInfo.pharmacy[0].longitude), 12F))
        }
    }
}