package com.example.legalsatta.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.legalsatta.Fragments.HomeScreen
import com.example.legalsatta.Fragments.LeaderBoard
import com.example.legalsatta.Fragments.UserProfile
import com.example.legalsatta.Models.loginedUser
import com.example.legalsatta.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.sql.Timestamp
import java.util.*

class HomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        findViewById<TextView>(R.id.home_score).text = loginedUser.score.toString()
        val homeBottomNavigator = findViewById<BottomNavigationView>(R.id.homeBottomNavigator)
//        val navController = this.findNavController(R.id.homeNavFrame)

//        homeBottomNavigator.setupWithNavController(navController)

        frameTransaction(HomeScreen())
        homeBottomNavigator.setOnItemSelectedListener{ menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.home_fragment-> {
                    frameTransaction(HomeScreen())
                    true
                }
                R.id.leaderBoard_fragment -> {
                    frameTransaction(LeaderBoard())
                    true
                }
                R.id.profile_fragment -> {
                    frameTransaction(UserProfile())
                    true
                }
                else -> false
            }
        }
    }

    fun frameTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.homeNavFrame, fragment)
            .commit()
    }

}