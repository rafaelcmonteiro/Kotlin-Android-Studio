package com.example.studentareaproject

import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student_schedule.*

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_schedule)

        val week = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.DAY_OF_WEEK)

        val context = this
        var db = DataBaseHandler(context)
        var data = db.getSchedule(week)
        tvResult.text =""
        for (i in 0 until data.size){
            if (data[i].class_day.toInt() == week) {
                tvResult.append(
                    "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at
                )
                break
            }else {
                maps.isEnabled = false
                tvResult.append("Hoje você não possui aula!!!")
                break
            }
        }

        maps.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }


    }
}