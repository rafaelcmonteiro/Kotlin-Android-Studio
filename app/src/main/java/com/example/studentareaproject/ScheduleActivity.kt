package com.example.studentareaproject

import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_student_schedule.*
import java.time.DayOfWeek

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_schedule)
        val week = Calendar.getInstance(TimeZone.getTimeZone("UTC")).get(Calendar.DAY_OF_WEEK)
        println(week == (Calendar.THURSDAY))
        val context = this
        var db = DataBaseHandler(context)
        Toast.makeText(
                this@ScheduleActivity,
            week.toString(),
                Toast.LENGTH_SHORT
            ).show()
        var data = db.getSchedule()
        tvResult.text =""
        for (i in 0 until data.size){
//            val time_string = "19:10:00"
//            val time_string_hour = (time_string.subSequence(0,2))
//            val time_string_min = (time_string.subSequence(3,5))
//            val time_string_sec = (time_string.subSequence(6,8))
//
//            val time_int_hour = Integer.parseInt(time_string_hour.toString())
//            val time_int_min = Integer.parseInt(time_string_min.toString())
//            val time_int_sec = Integer.parseInt(time_string_sec.toString())
//
//            val time_again = LocalTime.of(time_int_hour, time_int_min, time_int_sec)
//            val time = LocalTime.of(3, 15, 10)
//            println(time_again)
//            println(time == time_again)
            tvResult.append("Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + "19:00:00" + "\n" +
                            "Horário de Término: " + "22:00:00")
        }

        maps.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }




    }
}