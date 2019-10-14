package Services

import Model.Player
import Model.Team
import Utilities.URL_DATA
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.Response.Listener
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
    var myPlayers = arrayListOf<Player>()

    var captianIndex = -1
    var viceCaptianIndex = -1
    var multiplierIndex = -1

    var points = 0
    var totalPoints = 0

    var currentGW : Int = 0
    var isFinished = false
    var requestedGW : Int = 0
    var activeChip: String? = ""


    fun findCurrentGameWeek(complete:(Boolean)-> Unit){

        val gameWeekRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                var events = response.getJSONArray("events")
                for(x in 0 until events.length()){
                    val gameWeek = events.getJSONObject(x)
                    val isCurrent = gameWeek.getBoolean("is_current")
                    if (isCurrent){
                        currentGW = gameWeek.getInt("id")
                        isFinished = gameWeek.getBoolean("finished")
                        requestedGW = currentGW +1
                    }
                }
                println("current game week"+currentGW)
                complete(true)
            }catch (e:JSONException){
                complete(false)
            }

        },Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

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
            val url = "https://fantasy.premierleague.com/api/entry/"+ userId+"/event/"+ currentGW+"/picks/"
        val teamRequest = object: JsonObjectRequest(Method.GET, url,null, Listener { response ->
            try{
                activeChip = response.getString("active_chip")


                val entryHistory = response.getJSONObject("entry_history")
                points = entryHistory.getInt("points")
                totalPoints = entryHistory.getInt("total_points")

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

        },Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

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

    /*fun findTeamPreSeason(complete:(Boolean)-> Unit){

        val playersRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                var eventsObject = response.getJSONArray("events")
                for (x in 0 until eventsObject.length()){
                    if (eventsObject.getJSONObject(x).getBoolean("is_next")){
                        currentGW = eventsObject.getJSONObject(x).getInt("id")
                        requestedGW = currentGW

                        break
                    }
                }
                println("current game week"+ currentGW)
                ///////////////////////////////////////////////////////////players
                var playersObject = response.getJSONArray("elements")
                for(x in 0 until playersId.size){

                    if (playersId[x] == 0){
                        myPlayers.add(Player(0,"","",0,0,0,false,false,0.0,0.0,0,0,0,0,0,0,0,0,0,0,"",0))
                    }else{
                        for(y in 0 until playersObject.length()){
                            val player = playersObject.getJSONObject(y)

                            if (playersId [x] == player.getInt("id")){
                                val webName = player.getString("web_name")
                                val status = player.getString("status")
                                val teamCode = player.getInt("team_code")
                                val elementType = player.getInt("element_type")
                                val lastPoints = player.getInt("event_points")
                                val totalPoints = player.getInt("total_points")
                                val goalsScored = player.getInt("goals_scored")
                                val assists = player.getInt("assists")
                                val cleanSheets = player.getInt("clean_sheets")
                                val goalsConceded = player.getInt("goals_conceded")
                                val penaltiesSaved = player.getInt("penalties_saved")
                                val yellowCards = player.getInt("yellow_cards")
                                val redCards = player.getInt("red_cards")
                                val saves = player.getInt("saves")
                                val bonus = player.getInt("bonus")
                                val news = player.getString("news")
                                var chance = 0
                                if (news.equals("")){
                                    chance = 100
                                }else{
                                    chance = player.getInt("chance_of_playing_next_round")

                                }




                                val cost = player.getInt("now_cost").toDouble()
                                val percent = player.getDouble("selected_by_percent")
                                val id = player.getInt("id")
                                myPlayers.add(
                                    Player(id, webName, status, teamCode, elementType, lastPoints,false,false,cost,percent,totalPoints,goalsScored, assists, cleanSheets, goalsConceded, penaltiesSaved, yellowCards, redCards, saves, bonus,news,chance)

                                )


                            }
                        }
                    }

                }

                complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

            Log.d("request error","couldn't get waziifa"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(playersRequest)

    }
    */
}