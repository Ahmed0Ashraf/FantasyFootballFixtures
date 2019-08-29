package com.innovators.fantasyfootballfixtures.Controller

import Services.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.innovators.fantasyfootballfixtures.Controller.App.Companion.prefs
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        aboutTextView.setOnClickListener {
            val aboutIntent =  Intent(this,GainingIDAcitvity::class.java)
            startActivity(aboutIntent)
        }
        idEnterButton.setOnClickListener {
            idEnterButton.isEnabled = false
            enableSpinner(true)
            UserService.userId = idEditText.text.toString().toInt()
            println("ay 5ara")

            TeamsService.findTeams{teamsSuccess ->
                if (teamsSuccess){
                    println("team success")
                    UserService.findCurrentGameWeek{gwSuccess ->
                        if (gwSuccess){
                            println("GW success")

                            UserService.findTeam{teamSuccess ->
                                if (teamSuccess){
                                    println("findteam success")

                                    PlayerService.getAllPlayers{idsSuccess ->
                                        if(idsSuccess){
                                            println("players success")

                                            FixturesService.findGWFixtures { fixturesSuccess->
                                                if (fixturesSuccess){
                                                    var idsArray = App.prefs.rivalsIds.split("-").toTypedArray()
                                                    for (x in 0 until idsArray.size){
                                                        RivalsService.findRivalInfo(idsArray[x]){rivalsInfosuccess ->
                                                            if (rivalsInfosuccess){
                                                                if (x == (idsArray.size -1)){
                                                                    var intent = Intent(this,MainActivity::class.java)
                                                                    startActivity(intent)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            enableSpinner(false)
                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }

        }
    }
    fun enableSpinner(enable:Boolean){
        if (enable){
            loginProgressBar.visibility = View.VISIBLE
            idEditText.isEnabled = false
            idEnterButton.isEnabled = false
            aboutTextView.isEnabled = false
        }else{
            loginProgressBar.visibility = View.INVISIBLE
            idEditText.isEnabled = true
            idEnterButton.isEnabled = true
            aboutTextView.isEnabled = true
        }
    }
}
