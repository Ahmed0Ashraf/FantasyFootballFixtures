package Utilities

import android.content.Context
import com.android.volley.toolbox.Volley

class SharedPrefs(context: Context) {


    val PREFS_FILENAME = "prefs"
    val prefs = context.getSharedPreferences(PREFS_FILENAME,0)

    val PLAYER_IDS = "playerIds"

    var playerIds : String
    get() = prefs.getString(PLAYER_IDS,"0-0-0-0-0-0-0-0-0-0-0-0-0-0-0")
    set(value) = prefs.edit().putString(PLAYER_IDS,value).apply()

    val requestQueue = Volley.newRequestQueue(context)
}