package com.example.travellist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_travel_edit.*



class TravelEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_edit)

        if(intent.hasExtra("Position")) {
            val info= TravelInfo.fromBundle(intent.getBundleExtra("Info"))
            txtCity.setText(info.city)
            txtCountry.setText(info.country)
            txtYear.setText("${info.year}")
            txtNote.setText(info.note)
        }
    }

    fun onClick(view: View?) {

        if(view == btnSave) {

            val data = Intent()
            data.putExtra("Position", intent.getIntExtra("Position", -1))
            val info = TravelInfo(
                    "${txtCity.text}",
                    "${txtCountry.text}",
                     try { "${txtYear.text}".toInt() } catch (e: Exception) { 1900 },
                    "${txtNote.text}"
            )
            data.putExtra("Info", info.toBundle())

            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }
}
