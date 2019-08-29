package com.innovators.fantasyfootballfixtures.Controller


import Services.DataService
import Services.FixturesService
import Services.TeamsService
import Services.UserService
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_fixtures.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FixturesFragment : Fragment() {

    var fixtureGW = UserService.currentGW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fixtureInflator(this.context!!)
        gWPointstext.text = "Gameweek "+UserService.currentGW

        nextGWFixtureBtn.setOnClickListener {
            if (DataService.checkInternetConncetion()) {
                if (fixtureGW + 1 <= 38){
                    fixtureGW ++
                    gWPointstext.setText("Gameweek "+fixtureGW)
                    FixturesService.findRequestedGWFixtures(fixtureGW){gwFixtureSuccess ->
                        if (gwFixtureSuccess){
                            fixtureInflator(this.context!!)
                        }
                    }

                }
            }
            else {
                Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()

            }

        }
        previousGWFixtureBtn.setOnClickListener {
            if (DataService.checkInternetConncetion()) {
                if (fixtureGW - 1 >= 1){
                    fixtureGW --
                    gWPointstext.setText("Gameweek "+fixtureGW)
                    FixturesService.findRequestedGWFixtures(fixtureGW){gwFixtureSuccess ->
                        if (gwFixtureSuccess){
                            fixtureInflator(this.context!!)
                        }
                    }

                }
            }
            else {
                Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()

            }

        }



    }
    fun fixtureInflator (context: Context){
        fixtureLayout.removeAllViews()
        for (i in 0 until FixturesService.fixtures.size) {
            if (i == 0 ){
                var daylayout = LayoutInflater.from(context).inflate(R.layout.fixture_day, fixtureLayout, false)
                val dayText = daylayout.findViewById<View>(R.id.dayText) as TextView
                dayText.setText(FixturesService.fixtures[i].date)
                fixtureLayout.addView(daylayout)

            }else if (FixturesService.fixtures[i].date != FixturesService.fixtures[i-1].date){
                var daylayout = LayoutInflater.from(context).inflate(R.layout.fixture_day, fixtureLayout, false)
                val dayText = daylayout.findViewById<View>(R.id.dayText) as TextView
                dayText.setText(FixturesService.fixtures[i].date)
                fixtureLayout.addView(daylayout)

            }

            var childlayout = LayoutInflater.from(context).inflate(R.layout.fixture, fixtureLayout, false)

            val homeText = childlayout.findViewById<View>(R.id.homeTeamText) as TextView
            val awayText = childlayout.findViewById<View>(R.id.awayTeamText) as TextView
            val resultText = childlayout.findViewById<View>(R.id.resultText) as TextView

            val homeName = TeamsService.findTeamNameById(FixturesService.fixtures[i].homeTeam)
            val awayName = TeamsService.findTeamNameById(FixturesService.fixtures[i].awayTeam)

            homeText.setText(homeName)
            awayText.setText(awayName)
            if(FixturesService.fixtures[i].started){
                resultText.text = FixturesService.fixtures[i].homeScore.toString() + "-" + FixturesService.fixtures[i].awayScore.toString()

            }else{
                resultText.text = FixturesService.fixtures[i].time

            }

            fixtureLayout.addView(childlayout)
        }

    }


}
