package Services

import Model.Team
import Utilities.URL_DATA
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.innovators.fantasyfootballfixtures.Controller.App
import org.json.JSONException

object TeamsService {
    var teams = arrayListOf<Team>()

    fun findTeamCode (teamName:String):Int{
        for(x in 0 until teams.size){
            if(teams [x].name.equals(teamName)){
                return teams[x].teamCode
            }
        }
        return 0
    }
    fun findTeamidByCode (code:Int):Int{
        for(x in 0 until teams.size){
            if(teams [x].teamCode.equals(code)){
                return teams[x].id
            }
        }
        return 0
    }

    fun findTeamName (teamCode:Int):String{
        for(x in 0 until teams.size){
            if(teams [x].teamCode.equals(teamCode)){
                return teams[x].name
            }
        }
        return ""
    }
    fun findTeamShortNameById (id:Int):String{
        for(x in 0 until teams.size){
            if(teams [x].id.equals(id)){
                return teams[x].shortName
            }
        }
        return ""
    }
    fun findTeamNameById (id:Int):String{
        for(x in 0 until teams.size){
            if(teams [x].id.equals(id)){
                return teams[x].name
            }
        }
        return ""
    }
    fun findTeams(complete:(Boolean)-> Unit){

        val teamRequest = object: JsonObjectRequest(Method.GET, URL_DATA,null, Response.Listener { response ->
            try{
                var teamsObject = response.getJSONArray("teams")

                for(x in 0 until teamsObject.length()){
                    val team = teamsObject.getJSONObject(x)
                    val id = team.getInt("id")
                    val teamCode = team.getInt("code")
                    val name = team.getString("name")
                    val shortName = team.getString("short_name")
                    teams.add(Team(id,teamCode, name, shortName))

                }
                for(x in 0 until teamsObject.length()){
                    println( teams[x].name)

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

        App.prefs.requestQueue.add(teamRequest)

    }
}