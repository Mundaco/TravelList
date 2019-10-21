package com.example.travellist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_travel.*

class TravelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        // Mostramos los datos pasados en el intent en cada control
        txtLocation.text  = "${intent.extras?.getString("City")} (${intent.extras.getString("Country")})"
        txtYear.text  = """Year: ${intent.extras?.getString("Year")}"""
        txtNote.text  = intent.extras?.getString("Note")
    }


}
