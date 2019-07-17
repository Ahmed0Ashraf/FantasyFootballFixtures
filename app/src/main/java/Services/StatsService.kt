package Services

import Model.PlayerFixture
import Model.PreviousHistory
import Model.Team
import Utilities.URL_DATA
import Utilities.URL_STATS
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException

object StatsService {

    var fixtures = arrayListOf<PlayerFixture>()
    var previousSeason = arrayListOf<PreviousHistory>()

    fun findPlayerStats (playerId:Int, complete:(Boolean)-> Unit){

        val statsRequest = object: JsonObjectRequest(Method.GET, URL_STATS+playerId+"/",null, Response.Listener { response ->
            try{
                ///////////////////////////////////////fixtures
                var fixtureArray = response.getJSONArray("fixtures")
                fixtures.clear()
                for(x in 0 until fixtureArray.length()){
                    val fixture = fixtureArray.getJSONObject(x)
                    val date = fixture.getString("kickoff_time")
                    val round = fixture.getInt("event")
                    val difficulty = fixture.getInt("difficulty")


                    var opponentString = ""
                    if (fixture.getBoolean("is_home")){

                        var opponent = TeamsService.findTeamById(fixture.getInt("team_a"))
                        opponentString = opponent+" (H)"

                    }
                    else{
                        var opponent = TeamsService.findTeamById(fixture.getInt("team_h"))
                        opponentString = opponent+" (A)"

                    }


                    fixtures.add(PlayerFixture(date,round,opponentString,difficulty))

                }
                /////////////////////////////////////history

                var prevHistoryArray = response.getJSONArray("history_past")
                previousSeason.clear()
                for(x in 0 until prevHistoryArray.length()){
                    val prevData = prevHistoryArray.getJSONObject(x)
                    val seasonName = prevData.getString("season_name")
                    val totalPoints = prevData.getInt("total_points")
                    val minutes = prevData.getInt("minutes")
                    val goals = prevData.getInt("goals_scored")
                    val assists = prevData.getInt("assists")
                    val cleanSheets = prevData.getInt("clean_sheets")
                    val goalConceded = prevData.getInt("goals_conceded")
                    val penaltiesSaved = prevData.getInt("penalties_saved")
                    val yellowCards = prevData.getInt("yellow_cards")
                    val redCards = prevData.getInt("red_cards")
                    val saves = prevData.getInt("saves")
                    val bonus = prevData.getInt("bonus")
                    val startPrice = prevData.getInt("start_cost").toDouble()
                    val endPrice = prevData.getInt("end_cost").toDouble()

                    previousSeason.add(PreviousHistory(seasonName,totalPoints,minutes,goals,assists,cleanSheets,goalConceded,penaltiesSaved,yellowCards,redCards,saves,bonus,startPrice,endPrice))

                }
                previousSeason.reverse()


                complete(true)
            }catch (e: JSONException){
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("request error","couldn't get waziifa"+error)
            complete(false)
        })
        {
            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        App.prefs.requestQueue.add(statsRequest)

    }
}