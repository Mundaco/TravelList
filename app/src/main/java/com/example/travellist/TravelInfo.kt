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
            info.getString("City"),
            info.getString("Country"),
            info.getInt("Year"),
            info.getString("Note"))

    fun toBundle(): Bundle {
        val info = Bundle()
        info.putString("City", this.city)
        info.putString("Country", this.country)
        info.putInt("Year", this.year)
        info.putString("Note", this.note)
        return info
    }

    companion object {

        fun fromBundle(bundle: Bundle): TravelInfo {
            return TravelInfo(bundle)
        }
    }


}
