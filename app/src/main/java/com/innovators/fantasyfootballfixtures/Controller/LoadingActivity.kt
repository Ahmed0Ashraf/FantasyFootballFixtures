package com.innovators.fantasyfootballfixtures.Controller

import Services.FixturesService
import Services.LiveService
import Services.TeamsService
import Services.UserService
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        TeamsService.findTeams{teamsSuccess ->
            if (teamsSuccess){
                println("team success")
                UserService.findCurrentGameWeek { gwSuccess ->
                    if (gwSuccess) {
                        println("GW success")
                        FixturesService.findGWFixtures { fixturesSuccess ->
                            if (fixturesSuccess) {
                                LiveService.findGWFixtures {liveSuccess ->
                                    if (liveSuccess){
                                        LiveService.findEventStatus {statusSuccess ->
                                            if (statusSuccess){

                                                var intent = Intent(this,LoginActivity::class.java)
                                                startActivity(intent)
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


        ///////////////////////////////////////////////
        /*tryAgainBtn.setOnClickListener{
            loadingProgressBar.visibility = View.VISIBLE

            TeamsService.findTeams {teamsSuccess ->
                if (teamsSuccess){
                    var idsArray = App.prefs.playerIds.split("-").toTypedArray()
                    var newArray = idsArray.map { it.toInt() }
                    UserService.playersId = ArrayList(newArray)
                    UserService.findTeamPreSeason{playersSuccess ->
                        if (playersSuccess){
                            FixturesService.findGWFixtures { fixturesSuccess->
                                if (fixturesSuccess){
                                    var intent = Intent(this,MainActivity::class.java)
                                    startActivity(intent)
                                }
                                else {
                                    loadingProgressBar.visibility = View.INVISIBLE
                                }
                            }
                        }
                        else {
                            loadingProgressBar.visibility = View.INVISIBLE

                        }
                    }
                }else {
                    loadingProgressBar.visibility = View.INVISIBLE
                }

            }

        }
        //////////////////////////////////////////////////////////////////////////////////
        TeamsService.findTeams {teamsSuccess ->
         if (teamsSuccess){
             var idsArray = App.prefs.playerIds.split("-").toTypedArray()
             var newArray = idsArray.map { it.toInt() }
             UserService.playersId = ArrayList(newArray)
             UserService.findTeamPreSeason{playersSuccess ->
                 if (playersSuccess){
                     FixturesService.findGWFixtures { fixturesSuccess->
                         if (fixturesSuccess){
                             var intent = Intent(this,MainActivity::class.java)
                             startActivity(intent)
                         }
                         else {
                             loadingProgressBar.visibility = View.INVISIBLE
                         }
                     }
                 }
                 else{
                     loadingProgressBar.visibility = View.INVISIBLE
                 }
             }
         }else {
             loadingProgressBar.visibility = View.INVISIBLE
             tryAgainBtn.visibility = View.VISIBLE

         }

        }*/

    }

}
