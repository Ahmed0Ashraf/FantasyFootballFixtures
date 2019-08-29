package Services

import Model.Fixture
import Model.Player
import Utilities.URL_DATA
import Utilities.URL_FIXTURES
import android.util.Log
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object FixturesService {
    var gwFixtures = arrayListOf<Fixture>()
    var fixtures = arrayListOf<Fixture>()


    fun findGWFixtures(complete:(Boolean)-> Unit){

        val fixtureRequest = object: JsonArrayRequest(Method.GET, URL_FIXTURES,null, Listener { response ->
            try{
                gwFixtures.clear()

                for (x in 0 until response.length()){
                    if (response.getJSONObject(x).getInt("event") == UserService.currentGW ){

                        var homeTeam = response.getJSONObject(x).getInt("team_h")
                        var awayTeam = response.getJSONObject(x).getInt("team_a")
                        var kickOff = response.getJSONObject(x).getString("kickoff_time")
                        var started = response.getJSONObject(x).getBoolean("started")
                        var homeScore = 0
                        var awayScore = 0
                        if(started){
                            homeScore = response.getJSONObject(x).getInt("team_h_score")
                            awayScore = response.getJSONObject(x).getInt("team_a_score")
                        }

                        var date = returnDateString(kickOff)
                        var time = returnTimeString(kickOff)


                        gwFixtures.add(Fixture(homeTeam, awayTeam,date, time, started,homeScore,awayScore))
                        fixtures.add(Fixture(homeTeam, awayTeam,date, time, started,homeScore,awayScore))


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

        App.prefs.requestQueue.add(fixtureRequest)

    }

    fun findRequestedGWFixturesTeam(complete:(Boolean)-> Unit){

        val fixtureRequest = object: JsonArrayRequest(Method.GET, URL_FIXTURES,null, Listener { response ->
            try{
                gwFixtures.clear()

                for (x in 0 until response.length()){
                    if (response.getJSONObject(x).getInt("event") == UserService.requestedGW ){

                        var homeTeam = response.getJSONObject(x).getInt("team_h")
                        var awayTeam = response.getJSONObject(x).getInt("team_a")
                        var kickOff = response.getJSONObject(x).getString("kickoff_time")
                        var started = response.getJSONObject(x).getBoolean("started")
                        var homeScore = 0
                        var awayScore = 0
                        if(started){
                            homeScore = response.getJSONObject(x).getInt("team_h_score")
                            awayScore = response.getJSONObject(x).getInt("team_a_score")
                        }
                        var date = returnDateString(kickOff)
                        var time = returnTimeString(kickOff)


                        gwFixtures.add(Fixture(homeTeam, awayTeam,date, time, started,homeScore,awayScore))

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

        App.prefs.requestQueue.add(fixtureRequest)

    }
    fun findRequestedGWFixtures(gw:Int,complete:(Boolean)-> Unit){

        val fixtureRequest = object: JsonArrayRequest(Method.GET, URL_FIXTURES,null, Listener { response ->
            try{
                fixtures.clear()

                for (x in 0 until response.length()){
                    if (response.getJSONObject(x).getInt("event") == gw ){

                        var homeTeam = response.getJSONObject(x).getInt("team_h")
                        var awayTeam = response.getJSONObject(x).getInt("team_a")
                        var kickOff = response.getJSONObject(x).getString("kickoff_time")
                        var started = response.getJSONObject(x).getBoolean("started")
                        var homeScore = 0
                        var awayScore = 0
                        if(started){
                            homeScore = response.getJSONObject(x).getInt("team_h_score")
                            awayScore = response.getJSONObject(x).getInt("team_a_score")
                        }
                        var date = returnDateString(kickOff)
                        var time = returnTimeString(kickOff)


                        fixtures.add(Fixture(homeTeam, awayTeam,date, time, started,homeScore,awayScore))

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

        App.prefs.requestQueue.add(fixtureRequest)

    }
    fun returnDateString (isoString:String):String{

        val isoFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        isoFormater.timeZone = TimeZone.getTimeZone("UTC")

        var convertDate = Date()
        try{

            convertDate = isoFormater.parse(isoString)
        }
        catch (e: ParseException){

            Log.d("PARSE","CANNOT PARSE DATE")
        }

        val newStringFormat = SimpleDateFormat("E dd LLL yyyy", Locale.getDefault())

        return newStringFormat.format(convertDate)
    }
    fun returnTimeString (isoString:String):String{

        val isoFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        isoFormater.timeZone = TimeZone.getTimeZone("UTC")

        var convertDate = Date()
        try{

            convertDate = isoFormater.parse(isoString)
        }
        catch (e: ParseException){

            Log.d("PARSE","CANNOT PARSE DATE")
        }

        val newStringFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return newStringFormat.format(convertDate)
    }


}