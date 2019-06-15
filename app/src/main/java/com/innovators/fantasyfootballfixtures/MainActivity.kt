package com.innovators.fantasyfootballfixtures

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var pointsFragment = PointsFragment()
    var fixturesFragment = FixturesFragment()


    var active : Fragment = pointsFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_Points -> {

                supportFragmentManager.beginTransaction().hide(active).show(pointsFragment).commit()
                active = pointsFragment

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Team -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Fixtures -> {

                supportFragmentManager.beginTransaction().hide(active).show(fixturesFragment).commit()
                active = fixturesFragment

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Statistics -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Leagues -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container,fixturesFragment,"2").hide(fixturesFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container,pointsFragment,"1").commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
