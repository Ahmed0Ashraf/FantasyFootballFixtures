package com.innovators.fantasyfootballfixtures.Controller

import Model.Player
import Services.PlayerService
import Services.StatsService
import Services.TeamsService
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
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
        StatsService.player = player
        statsPlayerName.text = player.name
        statsPlayerTeam.text = TeamsService.findTeamName(player.teamCode)
        statsPlayerPosition.text = PlayerService.positionToString(player.elementType)
        statsPlayerPrice.text = (player.price/10).toString()
        statsPlayerTotalPoints.text = player.totalPoints.toString()
        statsPlayerGWPoints.text = player.lastPoints.toString()
        statsPlayerTeamSelectedBy.text = player.percent.toString()
        if (player.chanceOfPlaying == 0){
            playerStatsStatus.visibility = View.VISIBLE
            playerStatsStatus.setBackgroundColor(Color.parseColor("#C0020D"))
            playerStatsStatus.text = player.news
        }
        if (player.chanceOfPlaying == 25){
            playerStatsStatus.visibility = View.VISIBLE
            playerStatsStatus.setBackgroundColor(Color.parseColor("#D44401"))
            playerStatsStatus.text = player.news
        }
        if (player.chanceOfPlaying == 50){
            playerStatsStatus.visibility = View.VISIBLE
            playerStatsStatus.setBackgroundColor(Color.parseColor("#D47814"))
            playerStatsStatus.text = player.news
        }
        if (player.chanceOfPlaying == 75){
            playerStatsStatus.visibility = View.VISIBLE
            playerStatsStatus.setBackgroundColor(Color.parseColor("#FFE65B"))
            playerStatsStatus.text = player.news
        }


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
