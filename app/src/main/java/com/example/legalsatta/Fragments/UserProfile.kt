package com.example.legalsatta.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.legalsatta.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.legalsatta.Models.User
import com.example.legalsatta.Models.loginedUser
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass


class UserProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val profileUserName = v.findViewById<TextView>(R.id.profileUserName)
        val profileUserEmail = v.findViewById<TextView>(R.id.profileUserEmail)
        val totalPlayed = v.findViewById<TextView>(R.id.profileTotalPlayed)
        val totalWins = v.findViewById<TextView>(R.id.profileWins)
        val totalLosses = v.findViewById<TextView>(R.id.profileLoss)
        val userHistory = v.findViewById<RecyclerView>(R.id.profileUserBets)

        val list = arrayListOf<MockBets>()
        list.add(MockBets("1 March, 2023", "CSK", "WON", 10))
        list.add(MockBets("28 February, 2023", "RCB", "LOST", 0))
        list.add(MockBets("27 February, 2023", "RR", "LOST", 0))
        list.add(MockBets("26 February, 2023", "DareDevils", "DRAW", 0))

        userHistory.layoutManager = LinearLayoutManager(context)
        userHistory.adapter = ProfileBetAdapter(context, list)


        return v
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        view.findViewById<TextView>(R.id.profileUserName).text = loginedUser.username
        view.findViewById<TextView>(R.id.profileUserEmail).text = loginedUser.email
        getPredictionHistory(
            loginedUser,
            view.findViewById<TextView>(R.id.profileTotalPlayed),
            view.findViewById<TextView>(R.id.profileWins)
        )

        return view
    }

    fun getPredictionHistory(userData: User, profileTotalPlayed: TextView, profileWin: TextView) {

        lifecycleScope.launchWhenCreated {
            try {
                val response = RetrofitClass.buildService().getPredictionHistory(userData)
                if (response.isSuccessful) {


                    profileTotalPlayed.text = response?.body()?.result?.totalPredictions.toString()
                    profileWin.text = response?.body()?.result?.winPredictions.toString()

                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }
}

class ProfileBetAdapter(var context: Context?, var listOfBets: ArrayList<MockBets>):RecyclerView.Adapter<ProfileBetAdapter.BetViewHolder>(){

    class BetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val matchDate = itemView.findViewById<TextView>(R.id.matchDate)
        val teamNameBettedOn = itemView.findViewById<TextView>(R.id.teamNameBettedOn)
        val bettingStatus = itemView.findViewById<TextView>(R.id.bettingStatus)
        val bettingScore = itemView.findViewById<TextView>(R.id.bettingScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val v =  LayoutInflater.from(context).inflate(R.layout.card_layout_profile_bets, parent, false)
        return BetViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listOfBets.size
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val model = listOfBets.get(position)
        holder.matchDate.text = model.date
        holder.teamNameBettedOn.text = model.teamName

        holder.bettingStatus.text = model.status
        setColorToStatus(holder.bettingStatus)

        holder.bettingScore.text = model.score.toString()
    }
}

fun setColorToStatus(view: TextView){
    val status = view.text

    if(status == "WON")
        view.setTextColor(Color.GREEN)
    else if(status == "LOST")
        view.setTextColor(Color.RED)
    else
        view.setTextColor(Color.CYAN)
}

fun getDateForProfile(timeInMillis: String): String{
    val sdf = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
    val c = Calendar.getInstance()
    c.timeInMillis = Integer.parseInt(timeInMillis).toLong()
    return sdf.format(c.time).toString()
}

data class MockBets(var date: String, var teamName: String, var status: String, var score: Int)