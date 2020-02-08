package com.innovators.fantasyfootballfixtures.Controller

import Adapters.StatsRecycleAdapter
import Model.Player
import Services.DataService
import Services.PlayerService
import Services.TeamsService
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_selected_player.*
import kotlinx.android.synthetic.main.stats_header.*

class SelectedPlayer : AppCompatActivity() {
    lateinit var adapter:StatsRecycleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_player)

        var position = intent.getIntExtra("position", 0)
        var index = intent.getIntExtra("index", 0)

        playersCategorypinner.setSelection(position)
        PlayerService.searchPlayersByPosition(position) { searchSucces ->
            if (searchSucces) {

                adapter = StatsRecycleAdapter(this!!, PlayerService.searchedPlayers){player ->
                    if (DataService.checkInternetConncetion()) {
                        if (position == player.elementType){
                            var intent = Intent()
                            intent.putExtra("index",index)
                            intent.putExtra("player",player)
                            this.setResult(10,intent)
                            finish()
                        }else{

                            Toast.makeText(this,"Please Select A Player According To The Selected Position",Toast.LENGTH_LONG).show()

                        }
                    }else {
                        Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()

                    }



                }
                searchRecycleView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                searchRecycleView.layoutManager = layoutManager as RecyclerView.LayoutManager?
                searchRecycleView.setHasFixedSize(true)

            }

        }
        var categoryAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.viewDrop)
        )

        playersCategorypinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var itemName = parent!!.getItemAtPosition(position).toString()
                println(itemName)

                if (itemName.equals("GoalKeepers") || itemName.equals("Defenders") || itemName.equals("Midfilders") || itemName.equals(
                        "Forwards"
                    )
                ) {

                    var position = 0
                    if (itemName.equals("GoalKeepers")) {
                        position = 1
                    } else if (itemName.equals("Defenders")) {
                        position = 2

                    } else if (itemName.equals("Midfilders")) {
                        position = 3
                    } else {
                        position = 4
                    }

                    PlayerService.searchPlayersByPosition(position) { searchSucces ->
                        if (searchSucces) {
                            sortPlayers()
                            adapter.notifyDataSetChanged()
                        }
                    }
                } else if (itemName.equals("All Players")) {
                    PlayerService.searchAllPlayers { searchAllPlayersSuccess ->
                        if (searchAllPlayersSuccess) {
                            sortPlayers()

                            adapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    var code = TeamsService.findTeamCode(itemName)
                    PlayerService.searchPlayersByTeam(code) { searchCodeSuccess ->
                        if (searchCodeSuccess) {
                            sortPlayers()

                            adapter.notifyDataSetChanged()

                        }
                    }
                }
//sort


            }

        }
        var sortAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.SortedBy)
        )


        var firstTime = true
        searchSortedSpinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var itemName = parent!!.getItemAtPosition(position).toString()
                println(itemName)

                if (firstTime) {
                    firstTime = false
                } else {
                    if (itemName.equals("Total Points")) {
                        PlayerService.searchedPlayers.sortByDescending { selectorTotalPoints(it) }
                        adapter.notifyDataSetChanged()

                    }
                    if (itemName.equals("Price")) {
                        PlayerService.searchedPlayers.sortByDescending { selectorPrice(it) }
                        adapter.notifyDataSetChanged()

                    }
                    if (itemName.equals("Selected By")) {
                        headerTextView.text = "Sel."
                        PlayerService.selectedColumn = itemName
                        PlayerService.searchedPlayers.sortByDescending { selectorPercent(it) }
                        adapter.notifyDataSetChanged()

                    }
                    if (itemName.equals("Goals Scored")) {
                        headerTextView.text = "GS"
                        PlayerService.selectedColumn = itemName
                        PlayerService.searchedPlayers.sortByDescending { goalsScored(it) }
                        adapter.notifyDataSetChanged()

                    }
                    if (itemName.equals("Assists")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "A"
                        PlayerService.searchedPlayers.sortByDescending { assists(it) }
                        adapter.notifyDataSetChanged()

                    }
                    if (itemName.equals("Clean Sheets")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "CS"
                        PlayerService.searchedPlayers.sortByDescending { cleanSheets(it) }
                        adapter.notifyDataSetChanged()


                    }
                    if (itemName.equals("Goals Conceded")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "GC"
                        PlayerService.searchedPlayers.sortByDescending { goalsConceded(it) }
                        adapter.notifyDataSetChanged()
                    }
                    if (itemName.equals("Penalties Saved")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "PS"
                        PlayerService.searchedPlayers.sortByDescending { penaltiesSaved(it) }
                        adapter.notifyDataSetChanged()
                    }
                    if (itemName.equals("Yellow Cards")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "YC"
                        PlayerService.searchedPlayers.sortByDescending { yellowCards(it) }
                        adapter.notifyDataSetChanged()
                    }
                    if (itemName.equals("Red Cards")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "RC"
                        PlayerService.searchedPlayers.sortByDescending { redCards(it) }
                        adapter.notifyDataSetChanged()
                    }
                    if (itemName.equals("Saves")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "S"
                        PlayerService.searchedPlayers.sortByDescending { saves(it) }
                        adapter.notifyDataSetChanged()
                    }
                    if (itemName.equals("Bonus")) {
                        PlayerService.selectedColumn = itemName
                        headerTextView.text = "B"
                        PlayerService.searchedPlayers.sortByDescending { bonus(it) }
                        adapter.notifyDataSetChanged()
                    }

                }
            }




        }

    }
    fun selectorTotalPoints(p: Player): Int = p.totalPoints
    fun selectorPrice(p: Player): Double = p.price
    fun selectorPercent(p: Player): Double = p.percent
    fun goalsScored(p: Player): Int = p.goalsScored
    fun assists(p: Player): Int = p.assists
    fun cleanSheets(p: Player): Int = p.cleanSheets
    fun goalsConceded(p: Player): Int = p.goalsConceded
    fun penaltiesSaved(p: Player): Int = p.penaltiesSaved
    fun yellowCards(p: Player): Int = p.yellowCards
    fun redCards(p: Player): Int = p.redCards
    fun saves(p: Player): Int = p.saves
    fun bonus(p: Player): Int = p.bonus


    fun sortPlayers(){
        if (searchSortedSpinner.selectedItem.toString().equals("Total Points")) {
            PlayerService.searchedPlayers.sortByDescending { selectorTotalPoints(it) }
        }
        if (searchSortedSpinner.selectedItem.toString().equals("Price")) {
            PlayerService.searchedPlayers.sortByDescending { selectorPrice(it) }
        }
        if (searchSortedSpinner.selectedItem.toString().equals("Selected By")) {
            PlayerService.searchedPlayers.sortByDescending { selectorPercent(it) }
        }

        if (searchSortedSpinner.selectedItem.toString().equals("Goals Scored")) {

            PlayerService.searchedPlayers.sortByDescending { goalsScored(it) }
        }
        if (searchSortedSpinner.selectedItem.toString().equals("Assists")) {
            PlayerService.searchedPlayers.sortByDescending { assists(it) }
        }
        if (searchSortedSpinner.selectedItem.toString().equals("Clean Sheets")) {
            PlayerService.searchedPlayers.sortByDescending { cleanSheets(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Goals Conceded")) {
            PlayerService.searchedPlayers.sortByDescending { goalsConceded(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Penalties Saved")) {
            PlayerService.searchedPlayers.sortByDescending { penaltiesSaved(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Yellow Cards")) {
            PlayerService.searchedPlayers.sortByDescending { yellowCards(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Red Cards")) {
            PlayerService.searchedPlayers.sortByDescending { redCards(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Saves")) {
            PlayerService.searchedPlayers.sortByDescending { saves(it) }

        }
        if (searchSortedSpinner.selectedItem.toString().equals("Bonus")) {
            PlayerService.searchedPlayers.sortByDescending { bonus(it) }

        }

    }
}

