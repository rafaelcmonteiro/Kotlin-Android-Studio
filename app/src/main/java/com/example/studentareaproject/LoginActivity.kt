package com.example.studentareaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    val context = this
    var db = DataBaseHandler(context)

    signin.setOnClickListener({
        var data = db.readData()
        for (i in 0..(data.size-1)){
            val returnedValues = data.get(i).id.toString() + " " + data.get(i).username.toString() + " " +  data.get(i).password.toString()
            Toast.makeText(this@LoginActivity, returnedValues, Toast.LENGTH_SHORT).show()
        }

    })
}
}