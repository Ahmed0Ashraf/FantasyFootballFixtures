package com.innovators.fantasyfootballfixtures.Controller


import Services.StatsService
import android.graphics.Color
import android.graphics.Color.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_fixtures.*
import kotlinx.android.synthetic.main.fragment_gw_fixtures.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GwFixturesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gw_fixtures, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        for (x in 0 until StatsService.fixtures.size){
            var childlayout = LayoutInflater.from(this.context!!).inflate(R.layout.gw_fixtures_graph, statsFixturesContainer, false)

            val dateText = childlayout.findViewById<View>(R.id.dateTextView) as TextView
            val roundText = childlayout.findViewById<View>(R.id.roundTextView) as TextView
            val opponentText = childlayout.findViewById<View>(R.id.opponentTextView) as TextView
            val difficultyText = childlayout.findViewById<View>(R.id.difficultyTextView) as TextView

            dateText.setText(returnDateString(StatsService.fixtures[x].date))
            roundText.setText(StatsService.fixtures[x].round.toString())
            opponentText.setText(StatsService.fixtures[x].opponent)
            difficultyText.setText(StatsService.fixtures[x].difficulty.toString())
            if (StatsService.fixtures[x].difficulty.equals(2)){
                difficultyText.setBackgroundColor(Color.parseColor("#00AE55"))
            }
            else if (StatsService.fixtures[x].difficulty.equals(3)){
                difficultyText.setBackgroundColor(GRAY)
            }
            else if (StatsService.fixtures[x].difficulty.equals(4)){
                difficultyText.setBackgroundColor(Color.parseColor("#CE0001"))
                difficultyText.setTextColor(WHITE)

            }
            else {
                difficultyText.setBackgroundColor(Color.parseColor("#3b0d0d"))
                difficultyText.setTextColor(WHITE)

            }

            statsFixturesContainer.addView(childlayout)

        }

    }

    fun returnDateString (isoString:String):String{

        val isoFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        isoFormater.timeZone = TimeZone.getTimeZone("UTC")

        var convertDate = Date()
        try{

            convertDate = isoFormater.parse(isoString)
        }
        catch (e:ParseException){

            Log.d("PARSE","CANNOT PARSE DATE")
        }

        val newStringFormat = SimpleDateFormat("E dd LLL HH:mm", Locale.getDefault())

        return newStringFormat.format(convertDate)
    }


}
