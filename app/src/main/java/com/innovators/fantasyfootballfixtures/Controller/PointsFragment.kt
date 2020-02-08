package com.innovators.fantasyfootballfixtures.Controller


import Services.PlayerService
import Services.TeamsService
import Services.UserService
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.fragment_points.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PointsFragment : Fragment() {

    lateinit var defTshirtImages : ArrayList<ImageView>
    lateinit var defPlayerNames : ArrayList<TextView>
    lateinit var defPoints : ArrayList<TextView>
    lateinit var midTshirtImages : ArrayList<ImageView>
    lateinit var midPlayerNames : ArrayList<TextView>
    lateinit var midPoints : ArrayList<TextView>
    lateinit var fwdTshirtImages : ArrayList<ImageView>
    lateinit var fwdPlayerNames : ArrayList<TextView>
    lateinit var fwdPoints : ArrayList<TextView>
    lateinit var subTshirtImages : ArrayList<ImageView>
    lateinit var subPlayerNames : ArrayList<TextView>
    lateinit var subPoints : ArrayList<TextView>
    lateinit var captainImages : ArrayList<ImageView>
    lateinit var captainText : ArrayList<TextView>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_points, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        defTshirtImages = arrayListOf(def1imagebtn,def2imagebtn,def3imagebtn,def4imagebtn,def5imagebtn)
        midTshirtImages = arrayListOf(mid1imagebtn,mid2imagebtn,mid3imagebtn,mid4imagebtn,mid5imagebtn)
        fwdTshirtImages = arrayListOf(fwd1imagebtn,fwd2imagebtn,fwd3imagebtn)
        subTshirtImages = arrayListOf(sub1imagebtn,sub2imagebtn,sub3imagebtn,sub4imagebtn)
         //////////////////////////////////////////////
        defPlayerNames = arrayListOf(def1textview,def2textview,def3textview,def4textview,def5textview)
        midPlayerNames = arrayListOf(mid1textview,mid2textview,mid3textview,mid4textview,mid5textview)
        fwdPlayerNames = arrayListOf(fwd1textview,fwd2textview,fwd3textview)
        subPlayerNames = arrayListOf(sub1textview,sub2textview,sub3textview,sub4textview)
        ///////////////////////////////////////////////////
        defPoints = arrayListOf(def1points,def2points,def3points,def4points,def5points)
        midPoints = arrayListOf(mid1points,mid2points,mid3points,mid4points,mid5points)
        fwdPoints = arrayListOf(fwd1points,fwd2points,fwd3points)
        subPoints = arrayListOf(sub1points,sub2points,sub3points,sub4points)
        /////////////////////////////////////////////c
        captainImages = arrayListOf(captainDef1ImageView,captainDef2ImageView,captainDef3ImageView,captainDef4ImageView,captainDef5ImageView,
            captainMid1ImageView,captainMid2ImageView,captainMid3ImageView,captainMid4ImageView,captainMid5ImageView,captainFwd1ImageView,captainFwd2ImageView,captainFwd3ImageView)

        captainText = arrayListOf(captianDef1TextView,captianDef2TextView,captianDef3TextView,captianDef4TextView,captianDef5TextView,
            captianMid1TextView,captianMid2TextView,captianMid3TextView,captianMid4TextView,captianMid5TextView,captianFwd1TextView,captianFwd2TextView,captianFwd3TextView)
        pointsText.text =UserService.points.toString()
////////goal keeper
        var gkimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[0]!!.teamCode).toLowerCase().replace(" ","")
        val resourceId = this.resources.getIdentifier(gkimageShortName,"drawable",activity?.packageName)
        gk1imagebtn?.setImageResource(resourceId)
        gk1textview.text = PlayerService.myPlayersPoints[0].name
        gk1points.text = PlayerService.myPlayersPoints[0].lastPoints.toString()
        if (PlayerService.myPlayersPoints[0].isCaptian){
            captianGkTextView.visibility = View.VISIBLE
            captainGkImageView.visibility = View.VISIBLE
            gk1points.text = (PlayerService.myPlayersPoints[0].lastPoints*2).toString()
        }
        if (PlayerService.myPlayersPoints[0].isViceCaptian){
            captianGkTextView.visibility = View.VISIBLE
            captainGkImageView.visibility = View.VISIBLE
            captianGkTextView.text = "V"
        }
        ///////////formation
        var playerCounter = 1
        var defNum = 0
        var midNum = 0
        var fwdNum = 0

        for (x in 1 until 11){
            var position = PlayerService.myPlayersPoints[x].elementType
            if (position == 2){
                defNum++
            }else if (position == 3){
                midNum++
            }else if (position == 4){
                fwdNum++

            }

        }
        for (x in 0 until  PlayerService.myPlayersPoints.size){
            println(" "+PlayerService.myPlayersPoints[x].name)
        }
        println("defenders"+defNum)
        println("mids"+midNum)
        println("fwd"+fwdNum)

        //////////////def
        if (defNum == 3){
            for (x in 1 until 4){
                var defimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                val resourceId = this.resources.getIdentifier(defimageShortName,"drawable",activity?.packageName)
                defTshirtImages[x]?.setImageResource(resourceId)
                defPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
                defPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                    captainText[x].visibility = View.VISIBLE
                    captainImages[x].visibility = View.VISIBLE
                    defPoints[x].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()
                }
                if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                    captainText[x].visibility = View.VISIBLE
                    captainImages[x].visibility = View.VISIBLE
                    captainText[x].text = "V"
                }
                defTshirtImages[x].visibility = View.VISIBLE
                defPlayerNames[x].visibility = View.VISIBLE
                defPoints[x].visibility = View.VISIBLE
                playerCounter++
            }
        }
        if (defNum == 4 || defNum == 5){
            for (x in 0 until defTshirtImages.size){
                if (defNum == 4 && x == 2){

                }else{
                    var defimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                    val resourceId = this.resources.getIdentifier(defimageShortName,"drawable",activity?.packageName)
                    defTshirtImages[x]?.setImageResource(resourceId)
                    defPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
                    defPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                    if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                        captainText[x].visibility = View.VISIBLE
                        captainImages[x].visibility = View.VISIBLE
                        defPoints[x].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()
                    }
                    if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                        captainText[x].visibility = View.VISIBLE
                        captainImages[x].visibility = View.VISIBLE
                        captainText[x].text = "V"
                    }
                    defTshirtImages[x].visibility = View.VISIBLE
                    defPlayerNames[x].visibility = View.VISIBLE
                    defPoints[x].visibility = View.VISIBLE
                    playerCounter++
                }
            }
        }
        ///////////////////////////////mid
        if (midNum == 2 || midNum == 3){
            for (x in 1 until 4){
                if (midNum == 2 && x == 2) {

                }else{
                    var midimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                    val resourceId = this.resources.getIdentifier(midimageShortName,"drawable",activity?.packageName)
                    midTshirtImages[x]?.setImageResource(resourceId)
                    midPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
                    midPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                    if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                        captainText[x+5].visibility = View.VISIBLE
                        captainImages[x+5].visibility = View.VISIBLE
                        midPoints[x].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()
                    }
                    if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                        captainText[x+5].visibility = View.VISIBLE
                        captainImages[x+5].visibility = View.VISIBLE
                        captainText[x+5].text = "V"
                    }
                    midTshirtImages[x].visibility = View.VISIBLE
                    midPlayerNames[x].visibility = View.VISIBLE
                    midPoints[x].visibility = View.VISIBLE
                    playerCounter++
                }

            }
        }
        if (midNum == 4 || midNum == 5){
            for (x in 0 until midTshirtImages.size){
                if (midNum == 4 && x == 2){

                }else{
                    var midimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                    val resourceId = this.resources.getIdentifier(midimageShortName,"drawable",activity?.packageName)
                    midTshirtImages[x]?.setImageResource(resourceId)
                    midPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
                    midPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                    if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                        captainText[x+5].visibility = View.VISIBLE
                        captainImages[x+5].visibility = View.VISIBLE
                        midPoints[x].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()
                    }
                    if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                        captainText[x+5].visibility = View.VISIBLE
                        captainImages[x+5].visibility = View.VISIBLE
                        captainText[x+5].text = "V"
                    }
                    midTshirtImages[x].visibility = View.VISIBLE
                    midPlayerNames[x].visibility = View.VISIBLE
                    midPoints[x].visibility = View.VISIBLE
                    playerCounter++
                }
            }
        }
        /////////////////////////fwd
        if (fwdNum == 2 || fwdNum == 3){
            for (x in 0 until fwdPlayerNames.size){
                if (fwdNum == 2 && x == 1) {

                }else{
                    var fwdimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                    val resourceId = this.resources.getIdentifier(fwdimageShortName,"drawable",activity?.packageName)
                    fwdTshirtImages[x]?.setImageResource(resourceId)
                    fwdPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
                    fwdPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                    if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                        captainText[x+10].visibility = View.VISIBLE
                        captainImages[x+10].visibility = View.VISIBLE
                        fwdPoints[x].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()

                    }
                    if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                        captainText[x+10].visibility = View.VISIBLE
                        captainImages[x+10].visibility = View.VISIBLE
                        captainText[x+10].text = "V"
                    }
                    fwdTshirtImages[x].visibility = View.VISIBLE
                    fwdPlayerNames[x].visibility = View.VISIBLE
                    fwdPoints[x].visibility = View.VISIBLE
                    playerCounter++
                }

            }
        }else {
            var fwdimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
            val resourceId = this.resources.getIdentifier(fwdimageShortName,"drawable",activity?.packageName)
            fwdTshirtImages[1]?.setImageResource(resourceId)
            fwdPlayerNames[1].text = PlayerService.myPlayersPoints[playerCounter].name
            fwdPoints[1].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
            if (PlayerService.myPlayersPoints[playerCounter].isCaptian){
                captainText[11].visibility = View.VISIBLE
                captainImages[11].visibility = View.VISIBLE
                fwdPoints[1].text = (PlayerService.myPlayersPoints[playerCounter].lastPoints*2).toString()

            }
            if (PlayerService.myPlayersPoints[playerCounter].isViceCaptian){
                captainText[11].visibility = View.VISIBLE
                captainImages[11].visibility = View.VISIBLE
                captainText[11].text = "V"
            }
            fwdTshirtImages[1].visibility = View.VISIBLE
            fwdPlayerNames[1].visibility = View.VISIBLE
            fwdPoints[1].visibility = View.VISIBLE
            playerCounter++
        }
/////////////////////////////////////sub
        for (x in 0 until subPlayerNames.size){
                var subimageShortName = TeamsService.findTeamName(PlayerService.myPlayersPoints[playerCounter]!!.teamCode).toLowerCase().replace(" ","")
                val resourceId = this.resources.getIdentifier(subimageShortName,"drawable",activity?.packageName)
            subTshirtImages[x]?.setImageResource(resourceId)
            subPlayerNames[x].text = PlayerService.myPlayersPoints[playerCounter].name
            subPoints[x].text = PlayerService.myPlayersPoints[playerCounter].lastPoints.toString()
                playerCounter++

        }
        ////////////////////////////////////c
        gWPointstext.text = "GameWeek "+UserService.currentGW


    }


}
