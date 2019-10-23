package com.example.travellist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_travel.*

class TravelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        // Mostramos los datos pasados en el intent en cada control
        val info = TravelInfo.fromBundle(intent.getBundleExtra("Info"))

        txtLocation.text  = getString(R.string.location_string, info.city, info.country)
        txtYear.text  = if (info.year > 0)
            "${getString(R.string.Year)}: ${info.year}"
        else
            "${getString(R.string.Year)}: ${getString(R.string.NA)}"
        txtNote.text  = info.note
    }



}
