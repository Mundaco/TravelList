package com.example.travellist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Este ejemplo muestra el uso de una clase ListActivity que muestra una lista de paises visitados.
 * 
 * Para ello hacemos uso de una extension del ArrayAdapter que contiene una lista de objetos TravelInfo.
 * El metodo getView del adapter se encarga de mostrar la informacion de cada entrada TravelInfo de la
 * forma correcta en la vista.
 * 
 */
public class TravelListActivity extends ListActivity {
	
	private TravelAdapter adapter;

	private class TravelAdapter extends ArrayAdapter<TravelInfo>{
		
		private Context context;
		private ArrayList<TravelInfo> travels;
		private static final int RESOURCE = android.R.layout.simple_list_item_2;

		public TravelAdapter(Context context, ArrayList<TravelInfo> travels) {
			super(context, RESOURCE, travels);
			
			this.context = context;
			this.travels = travels;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout view;
			ViewHolder holder;
			
			if (convertView == null){
				view = new LinearLayout(context);
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				inflater.inflate(RESOURCE, view, true);
				
				holder = new ViewHolder();
				holder.text1 = (TextView) view.findViewById(android.R.id.text1);
				holder.text2 = (TextView) view.findViewById(android.R.id.text2);
				view.setTag(holder);
				
			} else {
				view = (LinearLayout) convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			//Rellenamos la vista con los datos
			TravelInfo info = travels.get(position);
			holder.text1.setText(info.getCity() + " (" + info.getCountry() + ")");
			holder.text2.setText(getResources().getString(R.string.year) + " " + info.getYear());
			
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

        //Generamos los datos
        ArrayList<TravelInfo> values = getData();
        
        //Creamos el adapter y lo asociamos a la activity
        adapter = new TravelAdapter(this, values);
        
        setListAdapter(adapter);

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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// Obtenemos los datos del viaje seleccionado
		TravelInfo info = adapter.getItem(position);

		// Creamos el intent para mostrar la nueva actividad
		Intent intent = new Intent(this, TravelActivity.class);

		// Añadimos al intent los datos del viaje
		assert info != null;
		intent.putExtra("City", info.getCity());
		intent.putExtra("Country", info.getCountry());
		intent.putExtra("Year", String.valueOf(info.getYear()));
		intent.putExtra("Note", info.getNote());

		// Lanzamos la nueva actividad
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}

	public void onClick(View view) {

		if(view == findViewById(R.id.btnAdd)) {
			// Lanzamos la actividad para añadir un nuevo viaje
			Intent intent = new Intent(this, TravelEditActivity.class);
			startActivity(intent);
		}
	}
}
