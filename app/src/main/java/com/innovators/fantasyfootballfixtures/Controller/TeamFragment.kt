package com.innovators.fantasyfootballfixtures.Controller


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_team.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFragment : Fragment() {



    lateinit var tshirtImages : ArrayList<ImageView>
    lateinit var playerNames : ArrayList<TextView>
    lateinit var teamOpps : ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tshirtImages = arrayListOf(gk1teamimagebtn,gk2teamimagebtn,def1teamimagebtn,def2teamimagebtn,def3teamimagebtn,def4teamimagebtn,def5teamimagebtn,
            mid1teamimagebtn,mid2teamimagebtn,mid3teamimagebtn,mid4teamimagebtn,mid5teamimagebtn,fwd1teamimagebtn,fwd2teamimagebtn,fwd3teamimagebtn)

        playerNames = arrayListOf(gk1teamtextview,gk2teamtextview,def1teamtextview,def2teamtextview,def3teamtextview,def4teamtextview,def5teamtextview,
            mid1teamtextview,mid2teamtextview,mid3teamtextview,mid4teamtextview,mid5teamtextview,fwd1teamtextview,fwd2teamtextview,fwd3teamtextview)

        teamOpps = arrayListOf(gk1teamopp,gk2teamopp,def1teamopp,def2teamopp,def3teamopp,def4teamopp,def5teamopp,mid1teamopp,mid2teamopp,mid3teamopp,mid4teamopp,mid5teamopp,
            fwd1teamopp,fwd2teamopp,fwd3teamopp)

        for (x in 0 until tshirtImages.size){
            var position = 0
            if(x<2){
                position = 1
            }else if (x<7 && x>1){
                position = 2

            }
            else if (x<12&& x>6 ){
                position = 3
            }
            else{
                position = 4
            }

            tshirtImages[x].setOnClickListener{

                var intent = Intent(activity,SelectedPlayer::class.java)
                intent.putExtra("position",position)
                startActivity(intent)
            }
        }
    }


}
