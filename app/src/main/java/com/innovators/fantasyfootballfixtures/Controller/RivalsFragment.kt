package com.innovators.fantasyfootballfixtures.Controller


import Services.*
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*

import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_rivals.*
import kotlinx.android.synthetic.main.rival_compare.*
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RivalsFragment : Fragment() {
    var rivalsIds = arrayListOf<String>()
    var spinnerArray = arrayListOf<String>()
    var infoHidden = false
    var bonus = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rivals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // App.prefs.rivalsIds = ""

        if (App.prefs.rivalsIds.isEmpty()){
            infoHidden = true
            chooseRivalText.visibility = View.INVISIBLE
            //rivalDropIds.visibility = View.GONE
            rivalTeamName.visibility = View.INVISIBLE
            lineUpText.visibility = View.INVISIBLE
            subsText.visibility = View.INVISIBLE
            rivalDivider1.visibility = View.INVISIBLE
            rivalDividerBot.visibility = View.INVISIBLE
            rivalDividerMid.visibility = View.INVISIBLE
            rivalDividerRightBot.visibility = View.INVISIBLE
            rivalDividerLeftBot.visibility = View.INVISIBLE
            include.visibility = View.INVISIBLE
        }else{
            var idsArray = App.prefs.rivalsIds.split("-").toTypedArray()
            rivalsIds = idsArray.toCollection(ArrayList())
            println(RivalsService.rivalsInfo.size.toString() + "sizeeeeeeeeeeeeeeeeee")
            for (x in 0 until RivalsService.rivalsInfo.size){
                var text = RivalsService.rivalsInfo[x].playerName + " (" + RivalsService.rivalsInfo[x].id +")"
                spinnerArray.add(text)
            }
        }

        spinnerArray.reverse()
       val adapter = ArrayAdapter<String>(
            activity,
            android.R.layout.simple_spinner_item,
           spinnerArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rivalDropIds.adapter = adapter

        rivalDropIds.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("zzzzzzz")

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
                var rivalPosition = RivalsService.rivalsInfo.size -1 -position
                rivalTeamName.text =  RivalsService.rivalsInfo[rivalPosition].teamName
                RivalsService.findRivalTeam(RivalsService.rivalsInfo[rivalPosition].id){success ->
                    if (success){
                        RivalsService.findRivalsPlayerInfo{infoSuccess ->
                            if (infoSuccess){
                                rivalInflator()
                                if ( infoHidden){
                                    chooseRivalText.visibility = View.VISIBLE
                                    //rivalDropIds.visibility = View.VISIBLE
                                    rivalTeamName.visibility = View.VISIBLE
                                    lineUpText.visibility = View.VISIBLE
                                    subsText.visibility = View.VISIBLE
                                    rivalDivider1.visibility = View.VISIBLE
                                    rivalDividerBot.visibility = View.VISIBLE
                                    rivalDividerMid.visibility = View.VISIBLE
                                    rivalDividerRightBot.visibility = View.VISIBLE
                                    rivalDividerLeftBot.visibility = View.VISIBLE
                                    include.visibility = View.VISIBLE

                                    linearLayoutLineUp.visibility = View.VISIBLE
                                    linearLayoutSubs.visibility = View.VISIBLE
                                    rivalComparison.visibility = View.VISIBLE
                                    infoHidden = false
                                }
                                    if (LiveService.weekFinished){
                                        BonusService.calculateRivalFinishedBonus()
                                    }else{
                                        BonusService.calculateRivalProvisionedBonus()
                                    }
                                myTotalPoints.text = UserService.totalPoints.toString()
                                rivalTotalPoints.text = RivalsService.rivalTotalPoints.toString()
                                myPoints.text = UserService.points.toString()
                                myBonus.text = BonusService.playersBonus.toString()
                                rivalPoints.text = RivalsService.rivalPoints.toString()
                                rivalBonus.text = BonusService.rivalBonus.toString()
                                //myPointsAndBonus.text = BonusService.weeklyPoints.toString() + "+" + BonusService.playersBonus.toString()
                                //rivalPointsAndBonus.text = BonusService.rivalWeeklyPoints.toString() + "+" + BonusService.rivalBonus.toString()

                            }
                        }
                    }

                }

            }

        }

        RivalsService.requestedRivalGameWeek = UserService.currentGW

        deleteRivalBtn.setOnClickListener{
            var rivalPositionSpinner = rivalDropIds.selectedItemPosition
            var rivalArrayPosition = RivalsService.rivalsInfo.size -1 -rivalPositionSpinner
            spinnerArray.removeAt(rivalPositionSpinner)
            RivalsService.rivalsInfo.removeAt(rivalArrayPosition)
            var rivalsPrefs = ""
            for (x in 0 until RivalsService.rivalsInfo.size){
                if (x == 0){
                    rivalsPrefs += RivalsService.rivalsInfo[x].id
                }else{
                    rivalsPrefs += "-"

                    rivalsPrefs += RivalsService.rivalsInfo[x].id
                }
            }
            App.prefs.rivalsIds = rivalsPrefs
            infoHidden = true
            rivalTeamName.visibility = View.INVISIBLE
            lineUpText.visibility = View.INVISIBLE
            subsText.visibility = View.INVISIBLE
            rivalDivider1.visibility = View.INVISIBLE
            rivalDividerBot.visibility = View.INVISIBLE
            rivalDividerMid.visibility = View.INVISIBLE
            rivalDividerRightBot.visibility = View.INVISIBLE
            rivalDividerLeftBot.visibility = View.INVISIBLE
            include.visibility = View.INVISIBLE
            linearLayoutLineUp.visibility = View.INVISIBLE
            linearLayoutSubs.visibility = View.INVISIBLE
            rivalComparison.visibility = View.INVISIBLE

        }
        rivalAddBtn.setOnClickListener{
            //App.prefs.rivalsIds = ""
            if (App.prefs.rivalsIds.isEmpty() || infoHidden){
                chooseRivalText.visibility = View.VISIBLE
                //rivalDropIds.visibility = View.VISIBLE
                rivalTeamName.visibility = View.VISIBLE
                lineUpText.visibility = View.VISIBLE
                subsText.visibility = View.VISIBLE
                rivalDivider1.visibility = View.VISIBLE
                rivalDividerBot.visibility = View.VISIBLE
                rivalDividerMid.visibility = View.VISIBLE
                rivalDividerRightBot.visibility = View.VISIBLE
                rivalDividerLeftBot.visibility = View.VISIBLE
                include.visibility = View.VISIBLE
                linearLayoutLineUp.visibility = View.VISIBLE
                linearLayoutSubs.visibility = View.VISIBLE
                rivalComparison.visibility = View.VISIBLE
                infoHidden = false
            }
            var rivalsArray = rivalsIds.toArray()
           if (rivalEditText.text.isEmpty()){
               Toast.makeText(context,"Please Enter Rival's ID",Toast.LENGTH_LONG).show()
           }else if (rivalEditText.text.toString().equals(UserService.userId.toString())){
               Toast.makeText(context,"You Can't Add Your ID To Rivals",Toast.LENGTH_LONG).show()

           }else if (rivalsArray.contains(rivalEditText.text.toString())){
               Toast.makeText(context,"This ID Already Exists In Your Rivals List",Toast.LENGTH_LONG).show()

           }else{

               hideKeyBoard()
               var rivalId = rivalEditText.text.toString()
               rivalsIds.add(rivalId)
               if (App.prefs.rivalsIds.isNotEmpty()){
                   App.prefs.rivalsIds += "-"
               }
               App.prefs.rivalsIds += rivalId
               println(App.prefs.rivalsIds)
               rivalEditText.text.clear()
               RivalsService.findRivalInfo(rivalId){rivalInfoSuccess ->
                   if (rivalInfoSuccess){
                       rivalTeamName.text = RivalsService.rivalsInfo.last().teamName
                       var text = RivalsService.rivalsInfo.last().playerName + " (" + RivalsService.rivalsInfo.last().id +")"
                       spinnerArray.add(0,text)
                       RivalsService.findRivalTeam(rivalId){success ->
                           if (success){
                               RivalsService.findRivalsPlayerInfo{infoSuccess ->
                                   if (infoSuccess){
                                       rivalInflator()
                                       if (LiveService.weekFinished){
                                           BonusService.calculateRivalFinishedBonus()
                                       }else {
                                           BonusService.calculateRivalProvisionedBonus()
                                       }
                                       myTotalPoints.text = UserService.totalPoints.toString()
                                       rivalTotalPoints.text = RivalsService.rivalTotalPoints.toString()
                                       myPoints.text = UserService.points.toString()
                                       myBonus.text = BonusService.playersBonus.toString()
                                       rivalPoints.text = RivalsService.rivalPoints.toString()
                                       rivalBonus.text = BonusService.rivalBonus.toString()
                                       //myPointsAndBonus.text = BonusService.weeklyPoints.toString() + "+" + BonusService.playersBonus.toString()
                                       //rivalPointsAndBonus.text = BonusService.rivalWeeklyPoints.toString() + "+" + BonusService.rivalBonus.toString()

                                   }
                               }
                           }

                       }
                   }
               }

           }
        }


        playerConditionBtn.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.player_condition_dialog,null)

            builder.setView(dialogView)

                .show()

        }

    }

    fun rivalInflator(){
        linearLayoutLineUp.removeAllViews()
        linearLayoutSubs.removeAllViews()

        for (x in 0 until 11){
            var childlayout = LayoutInflater.from(context).inflate(R.layout.rivals, linearLayoutLineUp, false)
            val rivalNameText = childlayout.findViewById<View>(R.id.rivalPlayerName) as TextView
            val rivalPlayerPointsText = childlayout.findViewById<View>(R.id.rivalPlayerPoints) as TextView
            val rivalPlayerStatsBtn =childlayout.findViewById<View>(R.id.rivalPlayerStatsBtn) as ImageButton

            rivalNameText.text = RivalsService.rivalPlayers[x].name

            rivalNameText.setTextColor(Color.parseColor(teamCondition(TeamsService.findTeamidByCode(RivalsService.rivalPlayers[x].teamCode),RivalsService.rivalPlayers[x].id)))
            if(bonus == -1){
                rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString()
            }
            else{
                rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString() +"+"+bonus
                bonus = -1
            }



            rivalPlayerStatsBtn?.setOnClickListener {
                if (DataService.checkInternetConncetion()) {
                    StatsService.findPlayerStats(RivalsService.rivalPlayers[x].id){ statsSuccess ->
                        if (statsSuccess){
                            val statsIntent = Intent(context,PlayerStatsActivity::class.java)
                            statsIntent.putExtra("player",RivalsService.rivalPlayers[x])
                            startActivity(statsIntent)
                        }
                    }
                }
                else {
                    Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()
                }
            }

            linearLayoutLineUp.addView(childlayout)


        }
        for (x in 11 until 15){
            var childlayout = LayoutInflater.from(context).inflate(R.layout.rivals, linearLayoutSubs, false)
            val rivalNameText = childlayout.findViewById<View>(R.id.rivalPlayerName) as TextView
            val rivalPlayerPointsText = childlayout.findViewById<View>(R.id.rivalPlayerPoints) as TextView
            val rivalPlayerStatsBtn =childlayout.findViewById<View>(R.id.rivalPlayerStatsBtn) as ImageButton

            rivalNameText.text = RivalsService.rivalPlayers[x].name
            rivalNameText.setTextColor(Color.parseColor(teamCondition(TeamsService.findTeamidByCode(RivalsService.rivalPlayers[x].teamCode),RivalsService.rivalPlayers[x].id)))
            if(bonus == -1){
                rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString()
            }
            else{
                rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString() +"+"+bonus
                bonus = -1
            }
            rivalPlayerStatsBtn?.setOnClickListener {
                if (DataService.checkInternetConncetion()) {
                    StatsService.findPlayerStats(RivalsService.rivalPlayers[x].id){ statsSuccess ->
                        if (statsSuccess){
                            val statsIntent = Intent(context,PlayerStatsActivity::class.java)
                            statsIntent.putExtra("player",RivalsService.rivalPlayers[x])
                            startActivity(statsIntent)
                        }
                    }
                }
                else {
                    Toast.makeText(App.appContext,"Connecntion Problems Please Try Again", Toast.LENGTH_LONG).show()
                }
            }
            linearLayoutSubs.addView(childlayout)


        }
    }
    fun hideKeyBoard(){
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(activity!!.currentFocus.windowToken,0)
        }
    }

    fun teamCondition(teamid:Int,playerId:Int):String{
        for (x in 0 until LiveService.fixtures.size){
            if (teamid == LiveService.fixtures[x].homeTeam || teamid == LiveService.fixtures[x].awayTeam){
                if ( LiveService.fixtures[x].finished){
                    return "#000000"
                }else if ( !LiveService.fixtures[x].started){
                    return "#CE0001"
                }else if ( LiveService.fixtures[x].started && !LiveService.fixtures[x].finishedProvisional ){
                    println(LiveService.weekBonus.size.toString() +"bbbbbbbbbbbbbbbbbb")
                    for (y in 0 until LiveService.weekBonus.size){
                        if (playerId == LiveService.weekBonus[y].id){
                            bonus = LiveService.weekBonus[y].bonus
                            println("bonaaaaaaaaaaas")
                        }
                    }
                    return "#00AE55"
                }else{
                    return "#889487"
                }
            }

        }
        return ""
    }





}
