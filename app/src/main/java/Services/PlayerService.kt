package Services

import Model.Player
import Utilities.MySingleton
import Utilities.URL_DATA
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException
import java.lang.annotation.ElementType
import java.text.FieldPosition

object PlayerService {
   // var players = arrayListOf<Player>()
    var searchedPlayers= arrayListOf<Player>()
    var myPlayersPoints= arrayListOf<Player>()

    var selectedColumn = "Selected By"



    fun getAllPlayers(complete:(Boolean)-> Unit){

        val playersRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                var playersArray = response.getJSONArray("elements")
                for(x in 0 until UserService.playersId.size){
                    val id = UserService.playersId[x]
                    for(y in 0 until playersArray.length()){
                        val player = playersArray.getJSONObject(y)
                        val searchedId = player.getInt("id")
                        if(id == searchedId){
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
                            val minutesPlayed = player.getInt("minutes")

                            val news = player.getString("news")
                            var chance = 0
                            if (news.equals("")){
                                chance = 100
                            }else{
                                chance = player.getInt("chance_of_playing_next_round")

                            }
                            val cost = player.getInt("now_cost").toDouble()
                            val percent = player.getDouble("selected_by_percent")

                            UserService.myPlayers.add(Player(id, webName, status, teamCode, elementType, lastPoints,false,false,cost,minutesPlayed,percent,totalPoints,goalsScored, assists, cleanSheets, goalsConceded, penaltiesSaved, yellowCards, redCards, saves, bonus,news,chance))

                            break
                        }
                    }

                }

                UserService.myPlayers[UserService.captianIndex].isCaptian = true
                UserService.myPlayers[UserService.viceCaptianIndex].isViceCaptian = true

                ///////////points array
                myPlayersPoints.addAll( UserService.myPlayers)
                UserService.myPlayers.sortBy { selectorPosition(it) }

                 complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        MySingleton.getInstance(App.appContext).addToRequestQueue(playersRequest)


        //App.prefs.requestQueue.add(playersRequest)

    }
    fun searchPlayersByPosition(position: Int,complete:(Boolean)-> Unit){

        val searchRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                searchedPlayers.clear()
                var allPlayers = response.getJSONArray("elements")
                for(x in 0 until allPlayers.length()){
                    val player = allPlayers.getJSONObject(x)

                    if(player.getInt("element_type") == position) {
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
                        val minutesPlayed = player.getInt("minutes")

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
                        searchedPlayers.add(
                            Player(id, webName, status, teamCode, elementType, lastPoints,false,false,cost,minutesPlayed,percent,totalPoints,goalsScored, assists, cleanSheets, goalsConceded, penaltiesSaved, yellowCards, redCards, saves, bonus,news,chance)

                        )
                    }
                }

                complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(searchRequest)

    }
    fun searchPlayersByTeam(code: Int,complete:(Boolean)-> Unit){

        val searchRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                searchedPlayers.clear()
                var allPlayers = response.getJSONArray("elements")
                for(x in 0 until allPlayers.length()){
                    val player = allPlayers.getJSONObject(x)

                    if(player.getInt("team_code") == code) {
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
                        val minutesPlayed = player.getInt("minutes")

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
                        searchedPlayers.add(
                            Player(id, webName, status, teamCode, elementType, lastPoints,false,false,cost,minutesPlayed,percent,totalPoints,goalsScored, assists, cleanSheets, goalsConceded, penaltiesSaved, yellowCards, redCards, saves, bonus,news,chance)

                        )
                    }
                }

                complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(searchRequest)

    }
    fun searchAllPlayers(complete:(Boolean)-> Unit){

        val searchRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Listener { response ->
            try{
                searchedPlayers.clear()
                var allPlayers = response.getJSONArray("elements")
                for(x in 0 until allPlayers.length()){
                    val player = allPlayers.getJSONObject(x)

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
                    val minutesPlayed = player.getInt("minutes")

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
                        searchedPlayers.add(
                            Player(id, webName, status, teamCode, elementType, lastPoints,false,false,cost,minutesPlayed,percent,totalPoints,goalsScored, assists, cleanSheets, goalsConceded, penaltiesSaved, yellowCards, redCards, saves, bonus,news,chance)

                        )

                }

                complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            DataService.networkErrorListener(error)

            Log.d("request error","couldn't get gameWeek"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(searchRequest)

    }

    fun selectorPosition(p: Player): Int = p.elementType

    fun positionToString(elementType: Int):String{

        if (elementType.equals(1)){

            return "Goalkeeper"
        }

        if (elementType.equals(2)){

            return "Defender"
        }
        if (elementType.equals(3)){

            return "Midfielder"
        }
        if (elementType.equals(4)){

            return "Forward"
        }
        return ""
    }
}