package com.example.studentareaproject

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.icu.util.*
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.studentareaproject.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_student_schedule.*
import java.time.LocalTime

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ifPresent.append(studentPresentorNot())
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val unicid = LatLng(-23.536622, -46.561975)
        mMap.addMarker(MarkerOptions().position(unicid).title("Here is Unicid"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unicid))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null){
                lastLocation = location
                var currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,12f))

            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    private fun studentPresentorNot() : String{
        val week = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.DAY_OF_WEEK)

        val context = this
        var isPresent = false
        var db = DataBaseHandler(context)
        var data = db.getSchedule(week)
        tvResult.text =""

        for (i in 0 until data.size){
            val initial_hour = data[i].start_at
            val end_hour = data[i].end_at
            isPresent = returningDateTime(initial_hour, end_hour)
        }

        return if (isPresent) {
            "Você esta presente!!!"
        }else{
            "Você não esta presente"
        }
    }

    private fun returningDateTime(initial_hour : String, end_hour : String) : Boolean {
        // Getting minute and hour
        val hour = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.HOUR)
        val min = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.MINUTE)
        // Transforming to an actual hour
        val current_hour = LocalTime.of(hour,min)
        val initial = getNewHour(initial_hour)
        val end = getNewHour(end_hour)

        return current_hour >= initial && current_hour <= end
    }

    private fun getNewHour(hour : String) : LocalTime{
        val time_string = hour
        val time_string_sec = (time_string.subSequence(6,8))
        val time_string_min = (time_string.subSequence(3,5))
        val time_string_hour = (time_string.subSequence(0,2))

        val time_int_sec = Integer.parseInt(time_string_sec.toString())
        val time_int_min = Integer.parseInt(time_string_min.toString())
        val time_int_hour = Integer.parseInt(time_string_hour.toString())
        val time_again = LocalTime.of(time_int_hour, time_int_min, time_int_sec)
        return time_again
    }

    override fun onMarkerClick(p0: Marker): Boolean = false
}