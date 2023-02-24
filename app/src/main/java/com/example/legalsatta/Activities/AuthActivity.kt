package com.example.legalsatta.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.legalsatta.Fragments.LoginFragment
import com.example.legalsatta.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.authFrame, LoginFragment())
            .commit()
    }
}