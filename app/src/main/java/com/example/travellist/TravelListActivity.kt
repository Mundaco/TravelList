package com.example.travellist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import java.util.*

/**
 * Este ejemplo muestra el uso de una clase ListActivity que muestra una lista de paises visitados.
 *
 * Para ello hacemos uso de una extension del ArrayAdapter que contiene una lista de objetos TravelInfo.
 * El metodo getView del adapter se encarga de mostrar la informacion de cada entrada TravelInfo de la
 * forma correcta en la vista.
 *
 */
class TravelListActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private var adapter: TravelAdapter? = null

    //Generamos datos a mostrar
    //En una aplicacion funcional se tomarian de base de datos o algun otro medio
    private val data: ArrayList<TravelInfo>
        get() {
            val travels = ArrayList<TravelInfo>()

            val info = TravelInfo("Londres", "UK", 2012, "�Juegos Olimpicos!")
            val info2 = TravelInfo("Paris", "Francia", 2007)
            val info3 = TravelInfo("Gotham City", "EEUU", 2011, "��Batman!!")
            val info4 = TravelInfo("Hamburgo", "Alemania", 2009)
            val info5 = TravelInfo("Pekin", "China", 2011)

            travels.add(info)
            travels.add(info2)
            travels.add(info3)
            travels.add(info4)
            travels.add(info5)

            return travels
        }


    private inner class TravelAdapter constructor(context: Context, val travels: ArrayList<TravelInfo>) : ArrayAdapter<TravelInfo>(context, RESOURCE, travels) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view: LinearLayout
            val holder: ViewHolder

            if (convertView == null) {
                view = LinearLayout(context)

                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(RESOURCE, view, true)

                holder = ViewHolder()
                holder.text1 = view.findViewById(android.R.id.text1)
                holder.text2 = view.findViewById(android.R.id.text2)
                view.tag = holder

            } else {
                view = convertView as LinearLayout
                holder = view.tag as ViewHolder
            }

            //Rellenamos la vista con los datos
            val info = travels[position]
            holder.text1!!.text = getString(R.string.location_string, info.city, info.country)
            holder.text2!!.text = if (info.year > 0)
                getString(R.string.year_string, info.year.toString())
            else
                getString(R.string.year_string_na)

            return view
        }
    }

    internal class ViewHolder {
        var text1: TextView? = null
        var text2: TextView? = null
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_list)

        val list = findViewById<ListView>(R.id.lstInfo)
        list.setOnItemClickListener(this)
        list.setOnItemLongClickListener(this)

        //Generamos los datos
        val values = data

        //Creamos el adapter y lo asociamos a la lista de la actividad
        adapter = TravelAdapter(this, values)
        list.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        // Obtenemos los datos del viaje seleccionado
        val info = adapter!!.getItem(position)

        // Creamos el intent para mostrar la nueva actividad
        val intent = Intent(this, TravelActivity::class.java)

        // Añadimos al intent los datos del viaje
        assert(info != null)
        intent.putExtra("Info", info!!.toBundle())

        // Lanzamos la nueva actividad
        startActivity(intent)

    }

    override fun onItemLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long): Boolean {

        // Obtenemos los datos del viaje seleccionado
        val info = adapter!!.getItem(position)

        // Creamos el intent para mostrar la nueva actividad
        val intent = Intent(this, TravelEditActivity::class.java)

        // Añadimos al intent los datos del viaje
        assert(info != null)
        intent.putExtra("Position", position)
        intent.putExtra("Info", info!!.toBundle())
        startActivityForResult(intent, RC_EDIT)

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_travel_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_new_travel) {
            // Lanzamos la actividad para añadir un nuevo viaje
            val intent = Intent(this, TravelEditActivity::class.java)
            startActivityForResult(intent, RC_NEW)

        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_NEW -> if (resultCode == Activity.RESULT_OK) {
                adapter!!.travels.add(TravelInfo.fromBundle(data!!.getBundleExtra("Info")))
                adapter!!.notifyDataSetChanged()
            }
            RC_EDIT -> if (resultCode == Activity.RESULT_OK) {

                val position = data!!.getIntExtra("Position", -1)
                if (position >= 0) {
                    adapter!!.travels[position] = TravelInfo.fromBundle(data.getBundleExtra("Info"))
                    adapter!!.notifyDataSetChanged()
                }
            }
            else -> {
            }
        }

    }

    companion object {

        private val RC_NEW = 1
        private val RC_EDIT = 2
        private val RESOURCE = android.R.layout.simple_list_item_2
    }
}
