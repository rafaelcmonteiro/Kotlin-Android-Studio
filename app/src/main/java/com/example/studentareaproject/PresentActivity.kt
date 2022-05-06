package com.example.studentareaproject

import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_present.*
import java.time.LocalTime

class PresentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_present)

        val locationComparison = loadData("sameLocation")
        val localization = loadData("localization")

        Toast.makeText(this, "$localization", Toast.LENGTH_SHORT).show()

        val week = Calendar.getInstance(TimeZone.getTimeZone("GMT-3")).get(Calendar.DAY_OF_WEEK)

        val context = this
        val db = DataBaseHandler(context)
        val data = db.getSchedule(week)
        val isPresent = studentPresentOrNot()

        for (i in 0 until data.size){
            // o primeiro if é se a localização estiver correta.
            if (isPresent && (locationComparison).toBoolean()) {
                tvText.append(
                            "Descrição:" + " Você esta em horário de aula" + "\n" +
                            "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at + "\n" +
                            "Localização: " + localization + "\n" +
                            "Status: " + "Presente"
                )
                break
                // o segundo para caso a aula esteja correndo porém a localização não bete.
            }else if(isPresent && !(locationComparison).toBoolean()){
                tvText.append(
                    "Descrição:" + " Você esta em horário de aula" + "\n" +
                            "Aula: " + data[i].discipline + "\n" +
                            "Horário de início: " + data[i].start_at + "\n" +
                            "Horário de Término: " + data[i].end_at + "\n" +
                            "Localização: " + localization + "\n" +
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
                            "Localização: " + localization + "\n" +
                            "Status: " + "Sem Status"
                )
                break
            }
        }
    }

    fun logOut(view: View){
        finishAffinity()
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

    private fun loadData(whichData : String): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return (sharedPreferences.getBoolean(whichData, true)).toString()
    }
}