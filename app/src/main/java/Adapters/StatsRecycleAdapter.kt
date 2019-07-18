package Adapters

import Model.Player
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
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.innovators.fantasyfootballfixtures.Controller.PlayerStatsActivity
import com.innovators.fantasyfootballfixtures.Controller.SelectedPlayer
import kotlinx.android.synthetic.main.activity_selected_player.*
import com.innovators.fantasyfootballfixtures.R

class StatsRecycleAdapter (val context: Context, val players: ArrayList<Player>) : RecyclerView.Adapter<StatsRecycleAdapter.Holder>() {

    var flag = 0
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindPlayer(players[position], context)
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.player_layout, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val playerName = itemView?.findViewById<TextView>(R.id.playerNameTextView)
        val playerPosition = itemView?.findViewById<TextView>(R.id.playerPositionTextView)
        val playerPoints = itemView?.findViewById<TextView>(R.id.ptsTextView)
        val teamShortName = itemView?.findViewById<TextView>(R.id.playerTeamTextView)
        val playerImage = itemView?.findViewById<ImageView>(R.id.playerImageView)
        val price = itemView?.findViewById<TextView>(R.id.costTextView)
        val selection = itemView?.findViewById<TextView>(R.id.selTextView)
        val statsbtn = itemView?.findViewById<ImageButton>(R.id.playerStatsBtn)
        val addbtn = itemView?.findViewById<ImageButton>(R.id.addPlayerBtn)




        fun bindPlayer(player: Player, context: Context) {

            playerName?.text = player.name
            playerPoints?.text = player.totalPoints.toString()
            statsbtn?.setOnClickListener {
                StatsService.findPlayerStats(player.id){ statsSuccess ->
                    if (statsSuccess){
                        val statsIntent = Intent(context,PlayerStatsActivity::class.java)
                        statsIntent.putExtra("player",player)
                        context.startActivity(statsIntent)
                    }
                }

            }
            addbtn?.setOnClickListener {
                var intent = Intent()
                intent.putExtra("player",player)
                (context as Activity).setResult(10,intent)
                    context .finish()
            }


            var priceDivided = player.price/10
            price?.text = priceDivided.toString()

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


        }
    }
}