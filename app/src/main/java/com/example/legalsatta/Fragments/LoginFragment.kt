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
import com.example.legalsatta.Activities.HomeActivity
import com.example.legalsatta.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        val loginEmail = v.findViewById<EditText>(R.id.loginEmail)
        val loginPassword = v.findViewById<EditText>(R.id.loginPassword)
        val loginBtn = v.findViewById<TextView>(R.id.loginBtn)
        val loginToRegisterBtn = v.findViewById<TextView>(R.id.loginToRegisterBtn)
        val sharedPef = activity?.getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)

        checkUserAlreadyExists()

        Log.d("Email", sharedPef?.getString("Email",null).toString())
        Log.d("Password", sharedPef?.getString("Password",null).toString())

        loginBtn?.setOnClickListener {
            if(checkCredentials(loginEmail.text.toString(), loginPassword.text.toString()))
                activity?.startActivity(Intent(activity, HomeActivity::class.java))
            else
                Toast.makeText(activity,"Wrong Credentials", Toast.LENGTH_SHORT).show()
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

    fun checkCredentials(e: String, p: String): Boolean{
        val sharedPef = activity?.getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        return e==sharedPef?.getString("Email",null)
                && p==sharedPef?.getString("Password",null)
    }

    fun checkUserAlreadyExists(){
        val sharedPef = activity?.getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        if(sharedPef?.getString("API Key", null)!=null)
            activity?.startActivity(Intent(activity, HomeActivity::class.java))
    }


}
