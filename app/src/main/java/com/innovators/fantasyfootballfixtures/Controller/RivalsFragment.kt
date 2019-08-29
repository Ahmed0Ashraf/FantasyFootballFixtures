package com.innovators.fantasyfootballfixtures.Controller


import Services.RivalsService
import Services.UserService
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_fixtures.*
import kotlinx.android.synthetic.main.fragment_rivals.*
import kotlinx.android.synthetic.main.rival_compare.*
import java.util.ArrayList
import android.widget.ArrayAdapter



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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rivals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (App.prefs.rivalsIds.isEmpty()){
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
        RivalsService.requestedRivalGameWeek = UserService.currentGW

        rivalAddBtn.setOnClickListener{
            //App.prefs.rivalsIds = ""
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
                                       myTotalPoints.text = UserService.totalPoints.toString()
                                       rivalTotalPoints.text = RivalsService.rivalTotalPoints.toString()
                                       myRoundPoints.text = UserService.points.toString()
                                       rivalRoundPoints.text = RivalsService.rivalPoints.toString()

                                   }
                               }
                           }

                       }
                   }
               }

           }
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
            rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString()

            linearLayoutLineUp.addView(childlayout)


        }
        for (x in 11 until 15){
            var childlayout = LayoutInflater.from(context).inflate(R.layout.rivals, linearLayoutSubs, false)
            val rivalNameText = childlayout.findViewById<View>(R.id.rivalPlayerName) as TextView
            val rivalPlayerPointsText = childlayout.findViewById<View>(R.id.rivalPlayerPoints) as TextView
            val rivalPlayerStatsBtn =childlayout.findViewById<View>(R.id.rivalPlayerStatsBtn) as ImageButton

            rivalNameText.text = RivalsService.rivalPlayers[x].name
            rivalPlayerPointsText.text = RivalsService.rivalPlayers[x].lastPoints.toString()

            linearLayoutSubs.addView(childlayout)


        }
    }
    fun hideKeyBoard(){
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(activity!!.currentFocus.windowToken,0)
        }
    }





}
