package com.innovators.fantasyfootballfixtures.Controller

import Services.TeamsService
import Services.UserService
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innovators.fantasyfootballfixtures.R

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        TeamsService.findTeams {teamsSuccess ->
         if (teamsSuccess){
             var idsArray = App.prefs.playerIds.split("-").toTypedArray()
             var newArray = idsArray.map { it.toInt() }
             UserService.playersId = ArrayList(newArray)
             UserService.findTeamPreSeason{playersSuccess ->

                 if (playersSuccess){

                     var intent = Intent(this,MainActivity::class.java)
                     startActivity(intent)
                 }

             }



         }

        }
    }
}
