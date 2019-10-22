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
            txtCity.setText(intent.getStringExtra("City"))
            txtCountry.setText(intent.getStringExtra("Country"))
            txtYear.setText("${intent.getIntExtra("Year",1900)}")
            txtNote.setText(intent.getStringExtra("Note"))
        }
    }

    fun onClick(view: View?) {

        if(view == btnSave) {

            val data = Intent()
            data.putExtra("Position",intent.getIntExtra("Position",-1))
            data.putExtra("City", "${txtCity.text}")
            data.putExtra("Country", "${txtCountry.text}")
            data.putExtra("Year", try {"${txtYear.text}".toInt()} catch (e: Exception) {1900} )
            data.putExtra("Note", "${txtNote.text}")

            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }
}
