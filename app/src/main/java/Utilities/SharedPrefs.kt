package Utilities

import android.content.Context
import com.android.volley.toolbox.Volley

class SharedPrefs(context: Context) {


    val PREFS_FILENAME = "prefs"
    val prefs = context.getSharedPreferences(PREFS_FILENAME,0)

    val RIVALS_IDS = "rivalsIds"

    var rivalsIds : String
    get() = prefs.getString(RIVALS_IDS,"")
    set(value) = prefs.edit().putString(RIVALS_IDS,value).apply()

    val requestQueue = Volley.newRequestQueue(context)
}