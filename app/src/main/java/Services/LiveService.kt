package Services

import Model.Fixture
import Model.LiveFixture
import Model.RoundBonus
import Utilities.URL_FIXTURES
import Utilities.URL_STATS
import Utilities.URL_STATUS
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException

object LiveService {
    var fixtures = arrayListOf<LiveFixture>()
    var weekFinished = true
    var weekBonus = arrayListOf<RoundBonus>()
    fun findGWFixtures(complete:(Boolean)-> Unit){

        val fixtureRequest = object: JsonArrayRequest(Method.GET, URL_FIXTURES + UserService.currentGW ,null,
            Response.Listener { response ->
                try {
                    fixtures.clear()

                    for (x in 0 until response.length()) {

                        var homeTeam = response.getJSONObject(x).getInt("team_h")
                        var awayTeam = response.getJSONObject(x).getInt("team_a")
                        var id = response.getJSONObject(x).getInt("id")
                        var started = response.getJSONObject(x).getBoolean("started")
                        var finished = response.getJSONObject(x).getBoolean("finished")
                        var finishedProvisional = response.getJSONObject(x).getBoolean("finished_provisional")
                        fixtures.add(LiveFixture(id,homeTeam, awayTeam, started, finished, finishedProvisional))
                        if (finishedProvisional){
                            var stats = response.getJSONObject(x).getJSONArray("stats")
                            var bonusObject = stats.getJSONObject(8)
                            var homeBonus = bonusObject.getJSONArray("h")
                            if (homeBonus.length() != 0){
                                for (x in 0 until homeBonus.length()){
                                    var bonusId = homeBonus.getJSONObject(x).getInt("element")
                                    var bonusValue = homeBonus.getJSONObject(x).getInt("value")
                                    weekBonus.add(RoundBonus(bonusId,bonusValue))
                                }
                            }
                            var awayBonus = bonusObject.getJSONArray("a")
                            if (awayBonus.length() != 0){
                                for (x in 0 until awayBonus.length()){
                                    var bonusId = awayBonus.getJSONObject(x).getInt("element")
                                    var bonusValue = awayBonus.getJSONObject(x).getInt("value")
                                    weekBonus.add(RoundBonus(bonusId,bonusValue))
                                }
                            }

                        }


                    }
                    complete(true)
                } catch (e: JSONException) {
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

        App.prefs.requestQueue.add(fixtureRequest)

    }
    fun findEventStatus(complete:(Boolean)-> Unit){

        val fixtureRequest = object: JsonObjectRequest(Method.GET, URL_STATUS,null,
            Response.Listener { response ->
                try {
                    var statusArray = response.getJSONArray("status")
                    for (x in 0 until statusArray.length()) {
                        if (!statusArray.getJSONObject(x).getBoolean("bonus_added")){
                            weekFinished = false
                        }

                    }
                    complete(true)
                } catch (e: JSONException) {
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

        App.prefs.requestQueue.add(fixtureRequest)

    }

}