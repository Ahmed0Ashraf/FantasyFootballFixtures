package com.innovators.fantasyfootballfixtures.Controller


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fixtureInflator(this.context!!)

    }
    fun fixtureInflator (context: Context){
        for (i in 0 until 10) {
            var childlayout = LayoutInflater.from(context).inflate(R.layout.fixture, frameLayout2, false)
            //val txt = childlayout.findViewById<View>(R.id.textView1) as TextView
            //txt.setText("gg "+i)
            frameLayout2.addView(childlayout)
        }
    }


}
