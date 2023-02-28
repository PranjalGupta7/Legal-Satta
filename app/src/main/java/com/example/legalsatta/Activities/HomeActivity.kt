package com.example.legalsatta.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.legalsatta.Fragments.HomeScreen
import com.example.legalsatta.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeBottomNavigator = findViewById<BottomNavigationView>(R.id.homeBottomNavigator)
        val main_nav_graph = findNavController(R.id.homeNavFrame)

        homeBottomNavigator.setupWithNavController(main_nav_graph)

    }

}