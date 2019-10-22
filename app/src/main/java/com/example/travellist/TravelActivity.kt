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
        // TODO: String templates for string literals
        txtLocation.text  = "${info.city} (${info.country})"
        txtYear.text  = "Year: " + if(info.year > 0) info.year else "n/a"
        txtNote.text  = info.note
    }



}
