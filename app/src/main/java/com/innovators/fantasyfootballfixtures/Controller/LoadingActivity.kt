package com.innovators.fantasyfootballfixtures.Controller

import Services.TeamsService
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
             var intent = Intent(this,MainActivity::class.java)
             startActivity(intent)
         }

        }
    }
}
