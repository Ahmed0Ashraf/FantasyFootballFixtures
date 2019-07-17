package Services

import Utilities.URL_DATA
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONArray
import org.json.JSONException
import java.net.URL
import kotlin.math.log

object UserService {
    var userId : Int = 0
    var playersId = arrayListOf<Int>()
    var captianIndex = -1
    var viceCaptianIndex = -1
    var multiplierIndex = -1


    var currentGW : Int = 0

    fun findCurrentGameWeek(complete:(Boolean)-> Unit){

        val gameWeekRequest = object: JsonArrayRequest(Method.GET, URL_DATA,null,Response.Listener {response ->
            try{
                for(x in 0 until response.length()){
                    val gameWeek = response.getJSONObject(x)
                    val isCurrent = gameWeek.getBoolean("is_current")
                    if (isCurrent){
                        currentGW = gameWeek.getInt("id")
                    }
                }
                println("current game week"+currentGW)
                complete(true)
            }catch (e:JSONException){
                complete(false)
            }

        },Response.ErrorListener {error ->
            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(gameWeekRequest)

    }
    fun findTeam(complete:(Boolean)-> Unit){
            val url = "https://fantasy.premierleague.com/drf/entry/"+ userId+"/event/"+ currentGW+"/picks"
        val teamRequest = object: JsonObjectRequest(Method.GET, url,null,Response.Listener {response ->
            try{
                val picks = response.getJSONArray("picks")

                for(x in 0 until picks.length()){
                    val playerId = picks.getJSONObject(x).getInt("element")
                    playersId.add(playerId)
                    if(picks.getJSONObject(x).getBoolean("is_captain")){
                        captianIndex = x
                    }
                    if(picks.getJSONObject(x).getBoolean("is_vice_captain")){
                        viceCaptianIndex = x

                    }
                    if(picks.getJSONObject(x).getInt("multiplier").equals(2) && multiplierIndex.equals(-1) ){
                        multiplierIndex = x

                    }
                }
                println("player id"+multiplierIndex)
                complete(true)
            }catch (e:JSONException){
                complete(false)
            }

        },Response.ErrorListener {error ->
            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(teamRequest)

    }
}