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
    }

    fun onClick(view: View?) {

        if(view == btnSave) {

            val intent = Intent()
            intent.putExtra("City", txtCity.text.toString())
            intent.putExtra("Country", txtCountry.text.toString())
            intent.putExtra("Year", txtYear.text.toString())
            intent.putExtra("Note", txtNote.text.toString())

            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
