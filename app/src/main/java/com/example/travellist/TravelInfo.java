package com.example.travellist;

import android.os.Bundle;

/**
 * Esta clase contiene la informacion de un viaje. Los datos que almacena son la ciudad, el pais,
 * el anyo del viaje y una nota opcional.
 *
 *
 */
public class TravelInfo {

	private String city;
	private String country;
	private int year;
	private String note;

	public static TravelInfo fromBundle(Bundle bundle) {
		return new TravelInfo(bundle);
	}
	
	public TravelInfo(String city, String country, int year, String note){
		this.city = city;
		this.country = country;
		this.year = year;
		this.note = note;
	}
	
	public TravelInfo(String city, String country, int year){
		this(city, country, year, null);
	}

	public TravelInfo(Bundle info) {
		this(
				info.getString("City"),
				info.getString("Country"),
				info.getInt("Year"),
				info.getString("Note"));
	}

	public Bundle toBundle() {
		Bundle info = new Bundle();
		info.putString("City" , this.city);
		info.putString("Country" , this.country);
		info.putInt("Year" , this.year);
		info.putString("Note" , this.note);
		return info;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setNote(String note) {
		this.city = note;
	}
	
	public String getNote() {
		return note;
	}
	
	
	
}
