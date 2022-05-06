package com.example.studentareaproject

import android.Manifest
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_present.*
import kotlinx.android.synthetic.main.activity_student_schedule.*
import java.time.LocalTime
import kotlin.system.exitProcess

class PresentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_present)
        val fullLocation = getLastLocation()

        val week = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.DAY_OF_WEEK)

        val context = this
        val db = DataBaseHandler(context)
        val data = db.getSchedule(week)
        tvText.text =""
        val isPresent = studentPresentOrNot()

        for (i in 0 until data.size){
            // o primeiro if é se a localização estiver correta.
            if (isPresent) {
                tvText.append(
                            "Descrição:" + " Você esta em horário de aula" + "\n" +
                            "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at + "\n" +
                            "Status: " + "Presente"
                )
                break
                // o segundo para caso a aula esteja correndo porém a localização não bete.
            }else if(isPresent){
                tvText.append(
                    "Descrição:" + " Você esta em horário de aula" + "\n" +
                            "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at + "\n" +
                            "Status: " + "Ausente"
                )
                break
                // para caso o aluno não esteja em horário de aula.
            }else if(!isPresent){
                tvText.append(
                    "Descrição:" + " Você não esta em horário de aula" + "\n" +
                            "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at + "\n" +
                            "Status: " + "Sem Status"
                )
                break
            }
        }
    }

    fun logOut(view: View){
        finishAffinity()
    }

    private fun getLastLocation(): Boolean {
        var locationLatitude : Double = 0.0
        var locationLongitude : Double = 0.0
        var booleanLatitudeLongitude : Boolean = true

        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PermissionChecker.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PermissionChecker.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener(
                    this
                ) { location ->
                    locationLatitude = (location.latitude)
                    locationLongitude = (location.longitude)
                    val latitudeLongitude = LatLng(locationLatitude, locationLongitude)
                    val locationTrue =
                        LatLng(-23.536286105990403, -46.560337171952156) == latitudeLongitude
                    booleanLatitudeLongitude = locationTrue
                    Toast.makeText(
                        this@PresentActivity,
                        "Esta presente?: $booleanLatitudeLongitude",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        return booleanLatitudeLongitude
    }

    private fun studentPresentOrNot() : Boolean{
        val week = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.DAY_OF_WEEK)

        val context = this
        var isPresent = false
        val db = DataBaseHandler(context)
        val data = db.getSchedule(week)

        for (i in 0 until data.size){
            val initialHour = data[i].start_at
            val endHour = data[i].end_at
            isPresent = returningDateTime(initialHour, endHour)
        }

        return isPresent
    }

    private fun returningDateTime(initialHour : String, endHour : String) : Boolean {
        // Getting minute and hour
        val hour = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.HOUR)
        val min = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.MINUTE)
        // Transforming to an actual hour
        val currentHour = LocalTime.of(hour,min)
        val initial = getNewHour(initialHour)
        val end = getNewHour(endHour)

        return currentHour >= initial && currentHour <= end
    }

    private fun getNewHour(hour: String): LocalTime {
        val timeIntSec = Integer.parseInt((hour.subSequence(6, 8)).toString())
        val timeIntMin = Integer.parseInt((hour.subSequence(3, 5)).toString())
        val timeIntHour = Integer.parseInt((hour.subSequence(0, 2)).toString())
        return LocalTime.of(timeIntHour, timeIntMin, timeIntSec)
    }
}