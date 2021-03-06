package com.innovators.fantasyfootballfixtures.Controller


import Services.StatsService
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_gw_fixtures.*
import kotlinx.android.synthetic.main.fragment_history.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        for (x in 0 until StatsService.previousSeason.size){
            var childlayout = LayoutInflater.from(this.context!!).inflate(R.layout.player_history_layout, previousHistoryContainer, false)

            val seasonText = childlayout.findViewById<View>(R.id.seasonTextView) as TextView
            val pointsText = childlayout.findViewById<View>(R.id.pointsTextView) as TextView
            val minutesText = childlayout.findViewById<View>(R.id.minutesPlayedTextView) as TextView
            val goalsScoredText = childlayout.findViewById<View>(R.id.goalsScoredTextView) as TextView
            val assistsText = childlayout.findViewById<View>(R.id.assistsTextView) as TextView
            val cleanSheetsText = childlayout.findViewById<View>(R.id.cleanSheetsTextView) as TextView
            val goalConcededTextView = childlayout.findViewById<View>(R.id.goalConcededTextView) as TextView
            val pennaltiesSavedTextView = childlayout.findViewById<View>(R.id.pennaltiesSavedTextView) as TextView
            val yellowCardsTextView = childlayout.findViewById<View>(R.id.yellowCardsTextView) as TextView
            val redCardsTextView = childlayout.findViewById<View>(R.id.redCardsTextView) as TextView
            val savesTextView = childlayout.findViewById<View>(R.id.savesTextView) as TextView
            val bonusTextView = childlayout.findViewById<View>(R.id.bonusTextView) as TextView
            val startPriceTextView = childlayout.findViewById<View>(R.id.startPriceTextView) as TextView
            val endPriceTextView = childlayout.findViewById<View>(R.id.endPriceTextView) as TextView


            seasonText.setText(StatsService.previousSeason[x].seasonName)
            pointsText.setText(StatsService.previousSeason[x].totalPoints.toString())
            minutesText.setText(StatsService.previousSeason[x].minutesPlayed.toString())
            goalsScoredText.setText(StatsService.previousSeason[x].goalsScored.toString())
            assistsText.setText(StatsService.previousSeason[x].assists.toString())
            cleanSheetsText.setText(StatsService.previousSeason[x].cleanSheets.toString())
            goalConcededTextView.setText(StatsService.previousSeason[x].goalsConceded.toString())
            pennaltiesSavedTextView.setText(StatsService.previousSeason[x].penaltiesSaved.toString())
            yellowCardsTextView.setText(StatsService.previousSeason[x].yellowCards.toString())
            redCardsTextView.setText(StatsService.previousSeason[x].redCards.toString())
            savesTextView.setText(StatsService.previousSeason[x].saves.toString())
            bonusTextView.setText(StatsService.previousSeason[x].bonus.toString())

            var dividedPrice = StatsService.previousSeason[x].seasonStartPrice/10
            startPriceTextView.setText(dividedPrice.toString())
            dividedPrice = StatsService.previousSeason[x].seasonEndPrice/10
            endPriceTextView.setText(dividedPrice.toString())

            previousHistoryContainer.addView(childlayout)

        }


        for (x in 0 until StatsService.thisSeason.size){
            var childlayout = LayoutInflater.from(this.context!!).inflate(R.layout.player_this_season_history_layout, presentHistoryContainer, false)

            val gwText = childlayout.findViewById<View>(R.id.gWTextView) as TextView
            val oppTextView = childlayout.findViewById<View>(R.id.oppTextView) as TextView
            val pointsText = childlayout.findViewById<View>(R.id.pointsTextView) as TextView
            val minutesText = childlayout.findViewById<View>(R.id.minutesPlayedTextView) as TextView
            val goalsScoredText = childlayout.findViewById<View>(R.id.goalsScoredTextView) as TextView
            val assistsText = childlayout.findViewById<View>(R.id.assistsTextView) as TextView
            val cleanSheetsText = childlayout.findViewById<View>(R.id.cleanSheetsTextView) as TextView
            val goalConcededTextView = childlayout.findViewById<View>(R.id.goalConcededTextView) as TextView
            val pennaltiesSavedTextView = childlayout.findViewById<View>(R.id.pennaltiesSavedTextView) as TextView
            val yellowCardsTextView = childlayout.findViewById<View>(R.id.yellowCardsTextView) as TextView
            val redCardsTextView = childlayout.findViewById<View>(R.id.redCardsTextView) as TextView
            val savesTextView = childlayout.findViewById<View>(R.id.savesTextView) as TextView
            val bonusTextView = childlayout.findViewById<View>(R.id.bonusTextView) as TextView
            val priceTextView = childlayout.findViewById<View>(R.id.priceTextView) as TextView


            gwText.setText(StatsService.thisSeason[x].gameWeek.toString())
            oppTextView.setText(StatsService.thisSeason[x].opp)

            pointsText.setText(StatsService.thisSeason[x].totalPoints.toString())
            minutesText.setText(StatsService.thisSeason[x].minutesPlayed.toString())
            goalsScoredText.setText(StatsService.thisSeason[x].goalsScored.toString())
            assistsText.setText(StatsService.thisSeason[x].assists.toString())
            cleanSheetsText.setText(StatsService.thisSeason[x].cleanSheets.toString())
            goalConcededTextView.setText(StatsService.thisSeason[x].goalsConceded.toString())
            pennaltiesSavedTextView.setText(StatsService.thisSeason[x].penaltiesSaved.toString())
            yellowCardsTextView.setText(StatsService.thisSeason[x].yellowCards.toString())
            redCardsTextView.setText(StatsService.thisSeason[x].redCards.toString())
            savesTextView.setText(StatsService.thisSeason[x].saves.toString())
            bonusTextView.setText(StatsService.thisSeason[x].bonus.toString())

            var dividedPrice = StatsService.thisSeason[x].price/10
            priceTextView.setText(dividedPrice.toString())


            presentHistoryContainer.addView(childlayout)

        }
        showTotal()

    }
fun showTotal(){
    var childlayout = LayoutInflater.from(this.context!!).inflate(R.layout.player_this_season_history_layout, presentHistoryContainer, false)

    val gwText = childlayout.findViewById<View>(R.id.gWTextView) as TextView
    val oppTextView = childlayout.findViewById<View>(R.id.oppTextView) as TextView
    val pointsText = childlayout.findViewById<View>(R.id.pointsTextView) as TextView
    val minutesText = childlayout.findViewById<View>(R.id.minutesPlayedTextView) as TextView
    val goalsScoredText = childlayout.findViewById<View>(R.id.goalsScoredTextView) as TextView
    val assistsText = childlayout.findViewById<View>(R.id.assistsTextView) as TextView
    val cleanSheetsText = childlayout.findViewById<View>(R.id.cleanSheetsTextView) as TextView
    val goalConcededTextView = childlayout.findViewById<View>(R.id.goalConcededTextView) as TextView
    val pennaltiesSavedTextView = childlayout.findViewById<View>(R.id.pennaltiesSavedTextView) as TextView
    val yellowCardsTextView = childlayout.findViewById<View>(R.id.yellowCardsTextView) as TextView
    val redCardsTextView = childlayout.findViewById<View>(R.id.redCardsTextView) as TextView
    val savesTextView = childlayout.findViewById<View>(R.id.savesTextView) as TextView
    val bonusTextView = childlayout.findViewById<View>(R.id.bonusTextView) as TextView
    val priceTextView = childlayout.findViewById<View>(R.id.priceTextView) as TextView


    gwText.setText("")
    oppTextView.setText("Total")

    pointsText.setText(StatsService.player.totalPoints.toString())
    minutesText.setText(StatsService.player.minutesPlayed.toString())
    goalsScoredText.setText(StatsService.player.goalsScored.toString())
    assistsText.setText(StatsService.player.assists.toString())
    cleanSheetsText.setText(StatsService.player.cleanSheets.toString())
    goalConcededTextView.setText(StatsService.player.goalsConceded.toString())
    pennaltiesSavedTextView.setText(StatsService.player.penaltiesSaved.toString())
    yellowCardsTextView.setText(StatsService.player.yellowCards.toString())
    redCardsTextView.setText(StatsService.player.redCards.toString())
    savesTextView.setText(StatsService.player.saves.toString())
    bonusTextView.setText(StatsService.player.bonus.toString())

    var dividedPrice = StatsService.player.price/10
    priceTextView.setText(dividedPrice.toString())


    presentHistoryContainer.addView(childlayout)
}


}
