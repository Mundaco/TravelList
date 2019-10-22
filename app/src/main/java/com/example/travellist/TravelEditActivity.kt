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
            intent.putExtra("City", "${txtCity.text}")
            intent.putExtra("Country", "${txtCountry.text}")
            intent.putExtra("Year", try {"${txtYear.text}".toInt()} catch (e: Exception) {0} )
            intent.putExtra("Note", "${txtNote.text}")

            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
