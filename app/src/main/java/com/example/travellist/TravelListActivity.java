package com.example.travellist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Este ejemplo muestra el uso de una clase ListActivity que muestra una lista de paises visitados.
 * 
 * Para ello hacemos uso de una extension del ArrayAdapter que contiene una lista de objetos TravelInfo.
 * El metodo getView del adapter se encarga de mostrar la informacion de cada entrada TravelInfo de la
 * forma correcta en la vista.
 * 
 */
public class TravelListActivity extends AppCompatActivity implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {

	private static final int RC_NEW = 1;
	private static final int RC_EDIT = 2;

	private TravelAdapter adapter;


	private class TravelAdapter extends ArrayAdapter<TravelInfo>{
		
		private Context context;
		private ArrayList<TravelInfo> travels;
		private static final int RESOURCE = android.R.layout.simple_list_item_2;

		TravelAdapter(Context context, ArrayList<TravelInfo> travels) {
			super(context, RESOURCE, travels);
			
			this.context = context;
			this.travels = travels;
		}

		@NotNull
		@Override
		public View getView(int position, View convertView, @NotNull ViewGroup parent) {
			
			LinearLayout view;
			ViewHolder holder;
			
			if (convertView == null){
				view = new LinearLayout(context);
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				inflater.inflate(RESOURCE, view, true);
				
				holder = new ViewHolder();
				holder.text1 = view.findViewById(android.R.id.text1);
				holder.text2 = view.findViewById(android.R.id.text2);
				view.setTag(holder);
				
			} else {
				view = (LinearLayout) convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			//Rellenamos la vista con los datos
			TravelInfo info = travels.get(position);
			// TODO: String templates for string literals
			holder.text1.setText(info.getCity() + " (" + info.getCountry() + ")");
			holder.text2.setText(getResources().getString(R.string.year) + " " + (info.getYear() > 0 ? info.getYear() : "n/a"));

			return view;
		}
		
	}
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_list);

		ListView list = findViewById(R.id.lstInfo);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);

		//Generamos los datos
		ArrayList<TravelInfo> values = getData();

        //Creamos el adapter y lo asociamos a la lista de la actividad
        adapter = new TravelAdapter(this, values);
        list.setAdapter(adapter);
    }

	//Generamos datos a mostrar
    //En una aplicacion funcional se tomarian de base de datos o algun otro medio
    private ArrayList<TravelInfo> getData(){
    	ArrayList<TravelInfo> travels = new ArrayList<TravelInfo>();

        TravelInfo info = new TravelInfo("Londres", "UK", 2012, "�Juegos Olimpicos!");
        TravelInfo info2 = new TravelInfo("Paris", "Francia", 2007);
        TravelInfo info3 = new TravelInfo("Gotham City", "EEUU", 2011, "��Batman!!");
        TravelInfo info4 = new TravelInfo("Hamburgo", "Alemania", 2009);
        TravelInfo info5 = new TravelInfo("Pekin", "China", 2011);

        travels.add(info);
        travels.add(info2);
        travels.add(info3);
        travels.add(info4);
        travels.add(info5);
        
        return travels;
    }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		// Obtenemos los datos del viaje seleccionado
		TravelInfo info = adapter.getItem(position);

		// Creamos el intent para mostrar la nueva actividad
		Intent intent = new Intent(this, TravelActivity.class);

		// Añadimos al intent los datos del viaje
		assert info != null;
		intent.putExtra("Info", info.toBundle());

		// Lanzamos la nueva actividad
		startActivity(intent);

	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		// Obtenemos los datos del viaje seleccionado
		TravelInfo info = adapter.getItem(position);

		// Creamos el intent para mostrar la nueva actividad
		Intent intent = new Intent(this, TravelEditActivity.class);

		// Añadimos al intent los datos del viaje
		assert info != null;
		intent.putExtra("Position",position);
		intent.putExtra("Info",info.toBundle());
		startActivityForResult(intent,RC_EDIT);

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_travel_list,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getTitle().equals("Nuevo")) {
			// Lanzamos la actividad para añadir un nuevo viaje
			Intent intent = new Intent(this, TravelEditActivity.class);
			startActivityForResult(intent,RC_NEW);

		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case (RC_NEW):
				if (resultCode == Activity.RESULT_OK) {
					adapter.travels.add(TravelInfo.Companion.fromBundle(data.getBundleExtra("Info")));
					adapter.notifyDataSetChanged();
				}
				break;
			case (RC_EDIT):
				if (resultCode == Activity.RESULT_OK) {

					int position = data.getIntExtra("Position",-1);
					if(position >= 0) {
						adapter.travels.set(position, TravelInfo.Companion.fromBundle(data.getBundleExtra("Info")));
						adapter.notifyDataSetChanged();
					}
				}
				break;
			default:
				break;
		}

	}
}
