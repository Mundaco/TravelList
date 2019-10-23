package com.example.travellist

import android.os.Bundle

/**
 * Esta clase contiene la informacion de un viaje. Los datos que almacena son la ciudad, el pais,
 * el anyo del viaje y una nota opcional.
 *
 *
 */
class TravelInfo @JvmOverloads constructor(var city: String?, var country: String?, var year: Int, var note: String? = null) {

    constructor(info: Bundle) : this(
            info.getString(KEY_CITY),
            info.getString(KEY_COUNTRY),
            info.getInt(KEY_YEAR),
            info.getString(KEY_NOTE))

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

        fun fromBundle(bundle: Bundle): TravelInfo {
            return TravelInfo(bundle)
        }
    }


}
