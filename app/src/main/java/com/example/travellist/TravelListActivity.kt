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

    /** Variables */
    private var adapter: TravelAdapter? = null

    /** Datos */
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

    /** Clase adaptador para la lista */
    private inner class TravelAdapter constructor(context: Context, var travels: ArrayList<TravelInfo>) : ArrayAdapter<TravelInfo>(context, LIST_ITEM_RESOURCE, travels) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view: LinearLayout
            val holder: ViewHolder

            if (convertView == null) {
                view = LinearLayout(context)

                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(LIST_ITEM_RESOURCE, view, true)

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

    /** Ciclo de vida */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_list)

        val list = findViewById<ListView>(R.id.lstInfo)
        list.onItemClickListener = this
        registerForContextMenu(list)


        adapter = if(savedInstanceState == null) {
            // Creamos el adapter y lo asociamos a la lista de la actividad
            TravelAdapter(this, data)
        }
        else {
            // O bien lo reiniciamos con el estado guardado
            TravelAdapter(this, savedInstanceState.getParcelableArrayList<TravelInfo>(KEY_DATA) as ArrayList<TravelInfo>)
        }

        list.adapter = adapter
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // Guardamos el estado de la lista ("travels")
        outState?.putParcelableArrayList(KEY_DATA,adapter?.travels)
    }

    /** Manjador de eventos de interfaz */
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
            R.id.btnNew -> newItem()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        // Obtenemos la posicion en la lista del elemento que vamos a editar o eliminar
        val position = (item?.menuInfo as AdapterView.AdapterContextMenuInfo).position

        when(item.itemId) {
            R.id.btnEdit -> editItem(position)
            R.id.btnDelete -> deleteItem(position)
        }

        return super.onContextItemSelected(item)
    }

    /** Lógica de negocio */
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

        // Añadimos al intent la posición del viaje en la lista...
        intent.putExtra(KEY_POSITION, position)
        // ...y los datos del viaje
        intent.putExtra(KEY_INFO, info?.toBundle())

        startActivityForResult(intent, RC_EDIT)
    }

    private fun deleteItem(position: Int) {

        // Definimos un diálogo para obtener la confirmación del usuario (antes de eliminar)
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

            // No eliminamos el elemento
            Toast.makeText(this,getString(R.string.delete_item_cancelled),Toast.LENGTH_SHORT).show()
        }

        // Creamos y mostramos el diálogo de confirmación
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /** Manejador de resultados de actividades */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            // Si venimos de crear un nuevo elemento...
            RC_NEW -> if (resultCode == Activity.RESULT_OK) {
                // ...lo añadimos a la lista
                adapter?.travels?.add(data!!.getBundleExtra(KEY_INFO).toTravelInfo())
                adapter?.notifyDataSetChanged()
            }
            // ...si no, si venimos de editar un elemento...
            RC_EDIT -> if (resultCode == Activity.RESULT_OK) {
                // ...recuperamos su posicion en la lista...
                val position = data!!.getIntExtra(KEY_POSITION, -1)
                if (position >= 0) {
                    // ...y lo modificamos directamente
                    adapter?.travels?.set(position, data.getBundleExtra(KEY_INFO).toTravelInfo())
                    adapter?.notifyDataSetChanged()
                }
            }
        }

    }

    /** Constantes estáticas */
    companion object {

        private const val KEY_DATA = "Data"
        private const val RC_NEW = 1
        private const val RC_EDIT = 2


        private const val LIST_ITEM_RESOURCE = android.R.layout.simple_list_item_2
    }
}
