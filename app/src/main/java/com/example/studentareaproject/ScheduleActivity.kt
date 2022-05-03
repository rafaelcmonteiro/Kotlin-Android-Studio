package com.example.studentareaproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_student_schedule.*

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_schedule)
        val context = this
        var db = DataBaseHandler(context)
        Toast.makeText(
                this@ScheduleActivity,
                "Olá!!!",
                Toast.LENGTH_SHORT
            ).show()
        var data = db.readData()
        tvResult.text =""
        for (i in 0..data.size-1){
            tvResult.append(data[i].id.toString() + " " + data[i].username)
        }
    }
}