package com.example.travellist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.example.travellist.TravelInfo.Companion.KEY_INFO
import com.example.travellist.TravelInfo.Companion.KEY_POSITION

/**
 * Este ejemplo muestra el uso de una clase ListActivity que muestra una lista de paises visitados.
 *
 * Para ello hacemos uso de una extension del ArrayAdapter que contiene una lista de objetos TravelInfo.
 * El metodo getView del adapter se encarga de mostrar la informacion de cada entrada TravelInfo de la
 * forma correcta en la vista.
 *
 */
class TravelListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var adapter: TravelAdapter? = null

    companion object {

        private const val RC_NEW = 1
        private const val RC_EDIT = 2
        private const val ADAPTER_RESOURCE = android.R.layout.simple_list_item_2
    }

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


    private inner class TravelAdapter constructor(context: Context, val travels: ArrayList<TravelInfo>) : ArrayAdapter<TravelInfo>(context, ADAPTER_RESOURCE, travels) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view: LinearLayout
            val holder: ViewHolder

            if (convertView == null) {
                view = LinearLayout(context)

                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(ADAPTER_RESOURCE, view, true)

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
            holder.text1?.text = getString(R.string.location_string, info.city, info.country)
            holder.text2?.text = if (info.year > 0)
                "${getString(R.string.Year)}: ${info.year}"
            else
                "${getString(R.string.Year)}: ${getString(R.string.NA)}"

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
        list.onItemClickListener = this
        registerForContextMenu(list)

        //Creamos el adapter y lo asociamos a la lista de la actividad
        adapter = TravelAdapter(this, data)
        list.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        viewItem(position)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_travel_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_travel_list, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_new_travel -> newItem()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        val position = (item?.menuInfo as AdapterView.AdapterContextMenuInfo).position

        when(item.itemId) {
            R.id.btnEdit -> editItem(position)
            R.id.btnDelete -> deleteItem(position)
        }

        return super.onContextItemSelected(item)
    }

    private fun viewItem(position: Int) {

        // Creamos el intent para mostrar la nueva actividad
        val intent = Intent(this, TravelActivity::class.java)

        // Añadimos al intent los datos del viaje
        intent.putExtra(KEY_INFO, adapter?.getItem(position)?.toBundle())

        // Lanzamos la nueva actividad
        startActivity(intent)
    }

    private fun newItem() {

        // Lanzamos la actividad para añadir un nuevo viaje
        val intent = Intent(this, TravelEditActivity::class.java)
        startActivityForResult(intent, RC_NEW)
    }

    private fun editItem(position: Int) {

        // Obtenemos los datos del viaje seleccionado
        val info = adapter?.getItem(position)

        // Creamos el intent para mostrar la nueva actividad
        val intent = Intent(this, TravelEditActivity::class.java)

        // Añadimos al intent los datos del viaje
        intent.putExtra(KEY_POSITION, position)
        intent.putExtra(KEY_INFO, info?.toBundle())
        startActivityForResult(intent, RC_EDIT)
    }

    private fun deleteItem(position: Int) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.Delete))
        builder.setMessage(getString(R.string.delete_item_question))
        builder.setPositiveButton(getString(R.string.Yes)){ _, _ ->

            // Eliminamos el elemento
            adapter?.travels?.removeAt(position)
            adapter?.notifyDataSetChanged()

            Toast.makeText(this,getString(R.string.delete_item_confirmation),Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(getString(R.string.No)){ _, _ ->
            Toast.makeText(this,getString(R.string.delete_item_cancelled),Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_NEW -> if (resultCode == Activity.RESULT_OK) {
                adapter?.travels?.add(TravelInfo.fromBundle(data!!.getBundleExtra(KEY_INFO)))
                adapter?.notifyDataSetChanged()
            }
            RC_EDIT -> if (resultCode == Activity.RESULT_OK) {
                val position = data!!.getIntExtra(KEY_POSITION, -1)
                if (position >= 0) {
                    adapter?.travels?.set(position, TravelInfo.fromBundle(data.getBundleExtra(KEY_INFO)))
                    adapter?.notifyDataSetChanged()
                }
            }
        }

    }
}
