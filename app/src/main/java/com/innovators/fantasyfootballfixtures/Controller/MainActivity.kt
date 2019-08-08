package com.innovators.fantasyfootballfixtures.Controller

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.innovators.fantasyfootballfixtures.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var pointsFragment = PointsFragment()
    var fixturesFragment = FixturesFragment()
    var teamFragment = TeamFragment()
    var leaguesFragment = LeaguesFragment()




    var active : Fragment = pointsFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_Points -> {

                supportFragmentManager.beginTransaction().hide(active).show(pointsFragment).commit()
                active = pointsFragment

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Team -> {

                supportFragmentManager.beginTransaction().hide(active).show(teamFragment).commit()
                active = teamFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Fixtures -> {

                supportFragmentManager.beginTransaction().hide(active).show(fixturesFragment).commit()
                active = fixturesFragment

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_Leagues -> {
                supportFragmentManager.beginTransaction().hide(active).show(leaguesFragment).commit()
                active = leaguesFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //navigation.selectedItemId = R.id.navigation_Team

        supportFragmentManager.beginTransaction().add(R.id.newcont,leaguesFragment).hide(leaguesFragment).commit()

        supportFragmentManager.beginTransaction().add(R.id.newcont,fixturesFragment).hide(fixturesFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.newcont,teamFragment).hide(teamFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.newcont,pointsFragment).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
    }
}
