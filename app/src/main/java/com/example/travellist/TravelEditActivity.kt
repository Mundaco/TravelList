package com.example.travellist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.travellist.TravelInfo.Companion.KEY_INFO
import com.example.travellist.TravelInfo.Companion.KEY_POSITION
import kotlinx.android.synthetic.main.activity_travel_edit.*



class TravelEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_edit)

        // Si el intent contiene datos de un viaje, estamos editándolo
        if(intent.hasExtra(KEY_POSITION)) {

            // Obtenemos los datos del intent
            val info= intent.getBundleExtra(KEY_INFO).toTravelInfo()

            // Mostramos los datos en los cuandros de texto
            txtCity.setText(info.city)
            txtCountry.setText(info.country)
            txtYear.setText("${info.year}")
            txtNote.setText(info.note)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_travel_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Si la opcion elejida es "Guardar"
        if(item.itemId == R.id.btnSave) {

            // Creamos un nuevo intent ("data") para devolver los datos
            val data = Intent()

            // Añadimos la posición del viaje en la lista obtenida del intent llamador
            // (-1 en caso de ser un uevo viaje)
            data.putExtra(KEY_POSITION, intent.getIntExtra(KEY_POSITION, -1))

            // Creamos un TravelInfo con la informacion del usuario para este viaje y lo añadimos a "data"
            val info = TravelInfo(
                    "${txtCity.text}",
                    "${txtCountry.text}",
                    try { "${txtYear.text}".toInt() } catch (e: Exception) { 0 },
                    "${txtNote.text}"
            )
            data.putExtra(KEY_INFO, info.toBundle())

            // Devolvemos el foco a la Activity llamadora
            setResult(Activity.RESULT_OK,data)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
