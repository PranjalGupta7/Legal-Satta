package com.example.legalsatta.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.legalsatta.Activities.HomeActivity
import com.example.legalsatta.Models.RegistrationModel
import com.example.legalsatta.Models.UsernameVerificationModel
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        val registerEmail = v.findViewById<EditText>(R.id.emailTextBox)
        val registerUsername = v.findViewById<EditText>(R.id.nameTextBox)
        val registerPassword = v.findViewById<EditText>(R.id.passwordTextBox)
        val cnfRegisterPassword = v.findViewById<EditText>(R.id.confirmPasswordTextBox)
        val verifyUsernameBtn = v.findViewById<TextView>(R.id.verifyUsernameBtn)
        val registerBtn = v.findViewById<TextView>(R.id.registerBtn)
//        val registerToLogin = v.findViewById<TextView>(R.id.registerTo)


        registerBtn.setOnClickListener {
            if(registerEmail.text.isEmpty()||registerUsername.text.isEmpty()||registerPassword.text.isEmpty()
                ||cnfRegisterPassword.text.isEmpty()){
                Toast.makeText(context,"Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail.text).matches()){
                registerEmail.error = "Invalid Email"
            }
//            else if(cnfRegisterPassword.text!=registerPassword.text){
//                registerPassword.text.clear()
//                cnfRegisterPassword.text.clear()
//                Toast.makeText(context, "Password doesn't match", Toast.LENGTH_SHORT).show()
//            }
            else {
                val userRegistrationData: RegistrationModel = RegistrationModel(
                    registerEmail.text.toString(),
                    registerPassword.text.toString(),
                    registerUsername.text.toString(),
                "")
                registerUser(userRegistrationData)
            }
        }

        verifyUsernameBtn.setOnClickListener {
            val retrofit = RetrofitClass.buildService()
            lifecycleScope.launchWhenCreated {
                try {
                    val response= retrofit.getUsernameAuthentication(registerUsername.text.toString())
                    if (response.isSuccessful) {
                        if(response.body()!!.isAvailable){
                            verifyUsernameBtn.text = "Verified"
                            verifyUsernameBtn.isClickable = false
                            verifyUsernameBtn.background = resources.getDrawable(R.drawable.btn_bg_blue)
                            registerUsername.isClickable = false
                        }
                        else{
                            Toast.makeText(context, "Name Already Exists", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    }
                }catch (e :Exception){
                    Log.e("Error",e.localizedMessage)
                }
            }
        }

        return v
    }

    fun registerUser(userData: RegistrationModel){

        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        lifecycleScope.launchWhenCreated {
            try {
                val response = RetrofitClass.buildService().postRegistrationDetails(userData)
                if(response.isSuccessful){
                    sharedPref?.edit()
                        ?.putString("API Key", response.body()!!.token.toString())
                }
            }catch (e: Exception){
                Log.e("Error", e.localizedMessage)
            }
            startActivity(Intent(context, HomeActivity::class.java))
        }
    }
}