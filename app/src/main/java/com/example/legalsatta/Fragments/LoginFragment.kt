package com.example.legalsatta.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.legalsatta.Activities.HomeActivity
import com.example.legalsatta.Models.*
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        val loginBtn = v.findViewById<TextView>(R.id.loginBtn)
        val loginToRegisterBtn = v.findViewById<TextView>(R.id.loginToRegisterBtn)

        loginBtn?.setOnClickListener {

            val loginEmail = v.findViewById<EditText>(R.id.loginEmail).text.toString()
            val loginPassword = v.findViewById<EditText>(R.id.loginPassword).text.toString()
            if (loginEmail.isEmpty()
                || loginPassword.isEmpty()
            ) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(LoginModel(loginEmail, loginPassword), null)
            }
        }

        loginToRegisterBtn.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.authFrame, RegisterFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        return v
    }


    fun loginUser(userData: LoginModel?, token: String?) {


        lifecycleScope.launchWhenCreated {
            var response: Response<GetUser>
            try {
                val retroService = RetrofitClass.buildService()
                if (token != null) {
                    response = retroService.verifyUser(TokenBody(token))
                } else {
                    Log.i("TAG", "loginUser: ")
                    response = retroService.postLoginDetails(userData!!)
                }
                if (response.isSuccessful) {
                    val token = response.body()!!.user.token.toString()
                    Log.i("token", "registerUser: $token")

                    var pref = activity?.getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                    )?.edit()

                    pref?.putString("token", token)?.commit()
//                    var intent=Intent(context,HomeActivity::class.java)
//                    intent.put
                    loginedUser = response.body()!!.user
                    startActivity(Intent(context, HomeActivity::class.java))

                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }

    fun checkCredentials(e: String, p: String): Boolean {
        val sharedPef = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return e == sharedPef?.getString("Email", null)
                && p == sharedPef.getString("Password", null)
    }

    fun checkUserAlreadyExists() {
        val sharedPef = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val token = sharedPef?.getString("token", "")
        Log.i("credential", "checkUserAlreadyExists: ${token} hbyug")

        if (token != "") {
//            activity?.startActivity(Intent(activity, HomeActivity::class.java))
            loginUser(null, token!!)

        }
    }


}
