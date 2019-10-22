package com.example.travellist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_travel_edit.*

class TravelEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_edit)
    }

    fun onClick(view: View?) {

        if(view == btnSave) {
            // Mostramos el toast con los datos introducidos
            val info = "${txtCity.text} (${txtCountry.text}), año: ${txtYear.text}"
            Toast.makeText(this,info,Toast.LENGTH_LONG).show()
        }
    }
}
