package com.example.legalsatta.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.legalsatta.Fragments.HomeScreen
import com.example.legalsatta.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeBottomNavigator = findViewById<BottomNavigationView>(R.id.homeBottomNavigator)

        setFragment(HomeScreen())

    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .commit()
    }
}