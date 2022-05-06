package com.example.studentareaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.password
import kotlinx.android.synthetic.main.activity_main.signin
import kotlinx.android.synthetic.main.activity_main.username

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        var db = DataBaseHandler(context)

        signin.setOnClickListener {
            var data = db.readData()
            for (i in 0 until data.size) {
                if (username.text.toString() == data[i].username.toString()) {
                    val returnedValues = data[i].username.toString()
                    if (username.text.toString() == data[i].username.toString() &&
                        password.text.toString() == data[i].password.toString()
                    ) {
                        Toast.makeText(
                            this@MainActivity,
                            "Bem Vindo $returnedValues",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, ScheduleActivity::class.java)
                        startActivity(intent)
                        break
                    } else {
                        Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}