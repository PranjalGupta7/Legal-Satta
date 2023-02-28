package com.example.legalsatta.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.legalsatta.Fragments.HomeScreen
import com.example.legalsatta.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.sql.Timestamp
import java.util.*

class HomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeBottomNavigator = findViewById<BottomNavigationView>(R.id.homeBottomNavigator)
        val navController = this.findNavController(R.id.homeNavFrame)

//        homeBottomNavigator.setupWithNavController(navController)
//        homeBottomNavigator.setOnItemSelectedListener{ menuItem: MenuItem ->
//            when(menuItem.itemId){
//                R.id.home_fragment-> { frameTransaction(HomeScreen())}
//            }
//        }
    }

    fun frameTransaction(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.homeNavFrame, fragment)
            .commit()
    }

}