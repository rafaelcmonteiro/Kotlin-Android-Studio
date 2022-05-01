package com.example.studentareaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.signin
import kotlinx.android.synthetic.main.activity_login.username
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    val context = this
    var db = DataBaseHandler(context)

    signin.setOnClickListener {
        var data = db.readData()
        for (i in 0 until data.size) {
            val returnedValues = data[i].username.toString()
            if (username.text.toString() == data[i].username.toString() &&
                password.text.toString() == data[i].password.toString()
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "You are login!!! $returnedValues",
                    Toast.LENGTH_SHORT
                ).show()
                // Toast.makeText(this@LoginActivity, "You are login!!!", Toast.LENGTH_SHORT).show()
                break
            } else {
                Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
                break
            }
            //Toast.makeText(this@LoginActivity, returnedValues, Toast.LENGTH_SHORT).show()
        }
    }
    create_acc.setOnClickListener{
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        }
    }
}