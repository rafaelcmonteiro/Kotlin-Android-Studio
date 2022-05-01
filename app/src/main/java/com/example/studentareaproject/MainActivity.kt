package com.example.studentareaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        var db = DataBaseHandler(context)
        signup.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            if (username.text.toString().isNotEmpty() &&
                password.text.toString().isNotEmpty() &&
                repassword.text.toString() == password.text.toString()
            ) {
                var user = User((username.text.toString()), password.text.toString())
                db.insertData(user)
                startActivity(intent)
            } else if (password.text.toString() != repassword.text.toString()) {
                Toast.makeText(context, "Passwords doesn't match", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }
        }

        signin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}