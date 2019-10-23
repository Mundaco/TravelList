package com.example.travellist

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Esta clase contiene la informacion de un viaje. Los datos que almacena son la ciudad, el pais,
 * el anyo del viaje y una nota opcional.
 *
 *
 */

// TravelInfo es "parcelable" de manera que se puede añadir a un Bundle
@Parcelize
data class TravelInfo(var city: String?, var country: String?, var year: Int, var note: String? = null) : Parcelable {

    // Funcion para devolver los datos en forma de Bundle
    fun toBundle(): Bundle {
        val info = Bundle()
        info.putString(KEY_CITY, this.city)
        info.putString(KEY_COUNTRY, this.country)
        info.putInt(KEY_YEAR, this.year)
        info.putString(KEY_NOTE, this.note)
        return info
    }

    companion object {

        const val KEY_INFO = "Info"
        const val KEY_POSITION = "Position"
        const val KEY_CITY = "City"
        const val KEY_COUNTRY = "Country"
        const val KEY_YEAR = "Year"
        const val KEY_NOTE = "Note"
    }
}

// Extensión del objeto Bundle para convertirlo a TravelInfo
fun Bundle.toTravelInfo(): TravelInfo {
    return TravelInfo(
            this.getString(TravelInfo.KEY_CITY),
            this.getString(TravelInfo.KEY_COUNTRY),
            this.getInt(TravelInfo.KEY_YEAR),
            this.getString(TravelInfo.KEY_NOTE)
    )
}
