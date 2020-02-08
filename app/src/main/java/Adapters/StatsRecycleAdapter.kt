package Adapters

import Model.Player
import Services.DataService
import Services.PlayerService
import Services.StatsService
import Services.TeamsService
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.innovators.fantasyfootballfixtures.Controller.App
import com.innovators.fantasyfootballfixtures.Controller.PlayerStatsActivity
import com.innovators.fantasyfootballfixtures.Controller.SelectedPlayer
import kotlinx.android.synthetic.main.activity_selected_player.*
import com.innovators.fantasyfootballfixtures.R

class StatsRecycleAdapter (val context: Context, val players: ArrayList<Player>,val addPlayer:(Player)-> Unit) : RecyclerView.Adapter<StatsRecycleAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindPlayer(players[position], context)
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.player_layout, parent, false)
        return Holder(view,addPlayer)
    }

    inner class Holder(itemView: View?,val addPlayer:(Player)-> Unit) : RecyclerView.ViewHolder(itemView!!) {

        val playerName = itemView?.findViewById<TextView>(R.id.playerNameTextView)
        val playerPosition = itemView?.findViewById<TextView>(R.id.playerPositionTextView)
        val playerPoints = itemView?.findViewById<TextView>(R.id.ptsTextView)
        val teamShortName = itemView?.findViewById<TextView>(R.id.playerTeamTextView)
        val playerImage = itemView?.findViewById<ImageView>(R.id.playerImageView)
        val price = itemView?.findViewById<TextView>(R.id.costTextView)
        val selection = itemView?.findViewById<TextView>(R.id.selTextView)
        val statsbtn = itemView?.findViewById<ImageButton>(R.id.playerStatsBtn)
        val addbtn = itemView?.findViewById<ImageButton>(R.id.addPlayerBtn)
        val statusImage = itemView?.findViewById<ImageView>(R.id.statusImage)




        fun bindPlayer(player: Player, context: Context) {

            playerName?.text = player.name
            playerPoints?.text = player.totalPoints.toString()

            statsbtn?.setOnClickListener {
                if (DataService.checkInternetConncetion()) {
                    StatsService.findPlayerStats(player.id){ statsSuccess ->
                        if (statsSuccess){
                            val statsIntent = Intent(context,PlayerStatsActivity::class.java)
                            statsIntent.putExtra("player",player)
                            context.startActivity(statsIntent)
                        }
                    }
                }
                else {
                    Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()

                }


            }
            addbtn?.setOnClickListener {
               addPlayer(player)
            }


            var priceDivided = player.price/10
            price?.text = priceDivided.toString()
////////////////////////////////////////selection column
            if (PlayerService.selectedColumn.equals("Selected By")){
                selection?.text = player.percent.toString()
            }

            if (PlayerService.selectedColumn.equals("Goals Scored")){
                selection?.text = player.goalsScored.toString()
            }

            if (PlayerService.selectedColumn.equals("Assists")){
                selection?.text = player.assists.toString()
            }

            if (PlayerService.selectedColumn.equals("Clean Sheets")){
                selection?.text = player.cleanSheets.toString()
            }

            if (PlayerService.selectedColumn.equals("Goals Conceded")){
                selection?.text = player.goalsConceded.toString()
            }
            if (PlayerService.selectedColumn.equals("Penalties Saved")){
                selection?.text = player.penaltiesSaved.toString()
            }
            if (PlayerService.selectedColumn.equals("Yellow Cards")){
                selection?.text = player.yellowCards.toString()
            }
            if (PlayerService.selectedColumn.equals("Red Cards")){
                selection?.text = player.redCards.toString()
            }
            if (PlayerService.selectedColumn.equals("Saves")){
                selection?.text = player.saves.toString()
            }
            if (PlayerService.selectedColumn.equals("Bonus")){
                selection?.text = player.bonus.toString()
            }
////////////////////////////////////////player's position
            if(player.elementType == 1){
                playerPosition?.text = "GK"
            }
            if(player.elementType == 2){
                playerPosition?.text = "DEF"
            }
            if(player.elementType == 3){
                playerPosition?.text = "MID"
            }
            if(player.elementType == 4){
                playerPosition?.text = "FWD"
            }
//////////////////////////////////player image
            for(x in 0 until TeamsService.teams.size){
                if(TeamsService.teams[x].teamCode == player.teamCode){
                    var shortName = TeamsService.teams[x].shortName
                    var teamName = TeamsService.teams[x].name

                    teamShortName?.text = shortName
                    var imageShortName = teamName.toLowerCase().replace(" ","")
                    val resourceId = context.resources.getIdentifier(imageShortName,"drawable",context.packageName)
                    playerImage?.setImageResource(resourceId)

                }
            }
            playerImage?.setOnClickListener{

                if(statsbtn?.visibility == View.VISIBLE){
                    statsbtn?.visibility = View.INVISIBLE
                    addbtn?.visibility = View.VISIBLE

                }else{
                    statsbtn?.visibility = View.VISIBLE
                    addbtn?.visibility = View.INVISIBLE
                }

            }

            /////////////////////////////status
            var chance = player.chanceOfPlaying
            if (chance == 100){
                var imageName = "ic_sentiment_satisfied_green_24dp"
                val resourceId = context.resources.getIdentifier(imageName,"drawable",context.packageName)
                statusImage?.setImageResource(resourceId)
            }else if (chance == 75){
                var imageName = "ic_sentiment_neutral_yellow_24dp"
                val resourceId = context.resources.getIdentifier(imageName,"drawable",context.packageName)
                statusImage?.setImageResource(resourceId)
            }
            else if (chance == 50){
                var imageName = "ic_sentiment_neutral_orange_24dp"
                val resourceId = context.resources.getIdentifier(imageName,"drawable",context.packageName)
                statusImage?.setImageResource(resourceId)
            }
            else if (chance == 25){
                var imageName = "ic_sentiment_very_dissatisfied_dark_orange_24dp"
                val resourceId = context.resources.getIdentifier(imageName,"drawable",context.packageName)
                statusImage?.setImageResource(resourceId)
            }else{
                var imageName = "ic_sentiment_very_dissatisfied_red_24dp"
                val resourceId = context.resources.getIdentifier(imageName,"drawable",context.packageName)
                statusImage?.setImageResource(resourceId)
            }


        }
    }
}