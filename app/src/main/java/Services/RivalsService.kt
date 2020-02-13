package Services

import Model.Player
import Model.Rival
import Utilities.MySingleton
import Utilities.URL_DATA
import Utilities.URL_ENTRY
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException

object RivalsService {

    var rivalPoints = 0
    var rivalTotalPoints = 0
    var rivalsInfo = arrayListOf<Rival>()
    var rivalCaptain = -1
    var rivalViceCaptain = -1
    var rivalMultiplierIndex = -1
    var rivalPlayersId = arrayListOf<Int>()
    var rivalPlayers = arrayListOf<Player>()
var requestedRivalGameWeek = 0

    fun findRivalTeam( rivalId:String,complete:(Boolean)-> Unit){
        val url = "https://fantasy.premierleague.com/api/entry/"+ rivalId+"/event/"+ requestedRivalGameWeek+"/picks/"
        val teamRequest = object: JsonObjectRequest(Method.GET, url,null, Response.Listener { response ->
            try {
                rivalPlayersId.clear()
                rivalPlayers.clear()
                val entryHistory = response.getJSONObject("entry_history")
                rivalPoints = entryHistory.getInt("points")
                rivalTotalPoints = entryHistory.getInt("total_points")

                val picks = response.getJSONArray("picks")

                for (x in 0 until picks.length()) {
                    val playerId = picks.getJSONObject(x).getInt("element")
                    rivalPlayersId.add(playerId)
                    if (picks.getJSONObject(x).getBoolean("is_captain")) {
                        rivalCaptain = x
                    }
                    if (picks.getJSONObject(x).getBoolean("is_vice_captain")) {
                        rivalViceCaptain = x

                    }
                    if (picks.getJSONObject(x).getInt("multiplier").equals(2) && rivalMultiplierIndex.equals(-1)) {
                        rivalMultiplierIndex = x

                    }
                }
                println("player id" + rivalMultiplierIndex)
                complete(true)
            } catch (e: JSONException) {
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

        App.prefs.requestQueue.add(teamRequest)

    }
    fun findRivalInfo( rivalId:String,complete:(Boolean)-> Unit){
        val url = URL_ENTRY + rivalId + "/"
        val teamRequest = object: JsonObjectRequest(Method.GET, url,null, Response.Listener { response ->
            try {
                val firstName = response.getString("player_first_name")
                val lastName = response.getString("player_last_name")

                val teamName = response.getString("name")
                var fullName = firstName + " " + lastName

                rivalsInfo.add(Rival(rivalId,fullName,teamName))


                complete(true)
            } catch (e: JSONException) {
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
        MySingleton.getInstance(App.appContext).addToRequestQueue(teamRequest)

            //App.prefs.requestQueue.add(teamRequest)

    }
    fun findRivalsPlayerInfo(complete:(Boolean)-> Unit){

        val playersRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Response.Listener { response ->
            try {
                var playersArray = response.getJSONArray("elements")
                for (x in 0 until rivalPlayersId.size) {
                    val id = rivalPlayersId[x]
                    for (y in 0 until playersArray.length()) {
                        val player = playersArray.getJSONObject(y)
                        val searchedId = player.getInt("id")
                        if (id == searchedId) {
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
                            if (news.equals("")) {
                                chance = 100
                            } else {
                                chance = player.getInt("chance_of_playing_next_round")

                            }
                            val cost = player.getInt("now_cost").toDouble()
                            val percent = player.getDouble("selected_by_percent")

                            rivalPlayers.add(
                                Player(
                                    id,
                                    webName,
                                    status,
                                    teamCode,
                                    elementType,
                                    lastPoints,
                                    false,
                                    false,
                                    cost,
                                    minutesPlayed,
                                    percent,
                                    totalPoints,
                                    goalsScored,
                                    assists,
                                    cleanSheets,
                                    goalsConceded,
                                    penaltiesSaved,
                                    yellowCards,
                                    redCards,
                                    saves,
                                    bonus,
                                    news,
                                    chance
                                )
                            )

                            break
                        }
                    }

                }

                rivalPlayers[rivalCaptain].isCaptian = true
                rivalPlayers[rivalViceCaptain].isViceCaptian = true
                println("tiiz ma3'raby 7amraaaaaaaaaaaaaaaaaaaaaaa")
                for (x in 0 until rivalPlayers.size){
                    println(rivalPlayers[x].name)
                }

                complete(true)
            } catch (e: JSONException) {
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

        App.prefs.requestQueue.add(playersRequest)

    }
}