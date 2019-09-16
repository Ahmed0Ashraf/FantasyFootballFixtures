package Services

import Model.Fixture
import Model.LiveFixture
import Model.Player
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

object LiveService {
    var fixtures = arrayListOf<LiveFixture>()
    var weekFinished = true
    var weekBonus = arrayListOf<RoundBonus>()
    var bonusSystemArray = arrayListOf<RoundBonus>()
    var dayFinished = true
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
                        if (finished){
                            var stats = response.getJSONObject(x).getJSONArray("stats")
                            var bonusObject = stats.getJSONObject(8)
                            var homeBonus = bonusObject.getJSONArray("h")
                            if (homeBonus.length() != 0){
                                for (x in 0 until homeBonus.length()){
                                    var bonusId = homeBonus.getJSONObject(x).getInt("element")
                                    var bonusValue = homeBonus.getJSONObject(x).getInt("value")
                                    weekBonus.add(RoundBonus(bonusId,bonusValue,true))
                                }
                            }
                            var awayBonus = bonusObject.getJSONArray("a")
                            if (awayBonus.length() != 0){
                                for (x in 0 until awayBonus.length()){
                                    var bonusId = awayBonus.getJSONObject(x).getInt("element")
                                    var bonusValue = awayBonus.getJSONObject(x).getInt("value")
                                    weekBonus.add(RoundBonus(bonusId,bonusValue,true))
                                }
                            }

                        }else if (started && !finished){
                            var stats = response.getJSONObject(x).getJSONArray("stats")
                            var bonusObject = stats.getJSONObject(9)
                            var homeBonus = bonusObject.getJSONArray("h")
                            var awayBonus = bonusObject.getJSONArray("a")
                            for (x in 0 until homeBonus.length()){
                                var bonusId = homeBonus.getJSONObject(x).getInt("element")
                                var bonusValue = homeBonus.getJSONObject(x).getInt("value")
                                bonusSystemArray.add(RoundBonus(bonusId,bonusValue,true))
                            }
                            for (x in 0 until awayBonus.length()){
                                var bonusId = awayBonus.getJSONObject(x).getInt("element")
                                var bonusValue = awayBonus.getJSONObject(x).getInt("value")
                                bonusSystemArray.add(RoundBonus(bonusId,bonusValue,true))
                            }

                        }
                        bonusSystemArray.sortByDescending { selectorBonusSystem(it) }
                        var currentBonus = 3
                        for (x in 0 until bonusSystemArray.size){
                            if (x == 0){
                                weekBonus.add(RoundBonus(bonusSystemArray[0].id,3,false))
                            }else{
                                if (bonusSystemArray[x].bonus == bonusSystemArray[x-1].bonus){
                                    weekBonus.add(RoundBonus(bonusSystemArray[x].id,currentBonus,false))
                                }else{
                                    currentBonus--
                                    if (x > 2){
                                        break
                                    }
                                    if (x == 2){
                                        currentBonus = 1
                                    }
                                    if (currentBonus == 0){
                                        break
                                    }
                                    weekBonus.add(RoundBonus(bonusSystemArray[x].id,currentBonus,false))
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
                    if (!weekFinished){
                        var date = Date();
                        val formatter = SimpleDateFormat("yyyy-MM-dd")
                        val time: String = formatter.format(date)
                        for (x in 0 until statusArray.length()) {
                            if (statusArray.getJSONObject(x).getString("date").equals(time)){
                                if (!statusArray.getJSONObject(x).getBoolean("bonus_added")){
                                    dayFinished = false
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
    fun selectorBonusSystem(p: RoundBonus): Int = p.bonus


}