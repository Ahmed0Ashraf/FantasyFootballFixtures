package com.innovators.fantasyfootballfixtures.Controller

import Model.Player
import Services.PlayerService
import Services.StatsService
import Services.TeamsService
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_player_stats.*

class PlayerStatsActivity : AppCompatActivity() {

    lateinit var player : Player
    var fixturesFragment = GwFixturesFragment()
    var historyFragment = HistoryFragment()

    var active : Fragment = historyFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_stats)

        player = intent.getParcelableExtra("player")
        statsPlayerName.text = player.name
        statsPlayerTeam.text = TeamsService.findTeamName(player.teamCode)
        statsPlayerPosition.text = PlayerService.positionToString(player.elementType)
        statsPlayerPrice.text = (player.price/10).toString()
        statsPlayerTotalPoints.text = player.totalPoints.toString()
        statsPlayerGWPoints.text = player.lastPoints.toString()
        statsPlayerTeamSelectedBy.text = player.percent.toString()


        var teamName = TeamsService.findTeamName(player.teamCode).toLowerCase().replace(" ","")
        val resourceId = this.resources.getIdentifier(teamName,"drawable",this.packageName)
        statsPlayerImage?.setImageResource(resourceId)




        supportFragmentManager.beginTransaction().add(R.id.statsContainer,fixturesFragment).hide(fixturesFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.statsContainer,historyFragment).commit()

        historyBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(active).show(historyFragment).commit()
            active = historyFragment
        }

        FixturesBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(active).show(fixturesFragment).commit()
            active = fixturesFragment
        }


    }


}
