package com.example.studentareaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        signup.setOnClickListener({
            if(username.text.toString().length > 0 &&
                password.text.toString().length > 0){
                var user = User(username.text.toString(),password.text.toString())
                var db = DataBaseHandler(context)
                db.insertData(user)
            }else{
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }
        })
    }
}