package com.innovators.fantasyfootballfixtures.Controller

import Services.PlayerService
import Services.TeamsService
import Services.UserService
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        aboutTextView.setOnClickListener {
            val aboutIntent =  Intent(this,GainingIDAcitvity::class.java)
            startActivity(aboutIntent)
        }
        idEnterButton.setOnClickListener {
            enableSpinner(true)
            UserService.userId = idEditText.text.toString().toInt()
            TeamsService.findTeams{teamsSuccess ->
                if (teamsSuccess){
                    UserService.findCurrentGameWeek{gwSuccess ->
                        if (gwSuccess){
                            UserService.findTeam{teamSuccess ->
                                if (teamSuccess){
                                    PlayerService.getAllPlayers{idsSuccess ->
                                        if(idsSuccess){
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
