package com.example.legalsatta.Fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.legalsatta.Models.Results
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass
import okhttp3.internal.UTC
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeScreen : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home_screen, container, false)
        val teamImg1 = v.findViewById<ImageView>(R.id.teamImg1)
        val teamImg2 = v.findViewById<ImageView>(R.id.teamImg2)
        val predictNowBtn = v.findViewById<TextView>(R.id.predictNowBtn)
        val retrofit = RetrofitClass.buildService()
        val matchesListView = v.findViewById<RecyclerView>(R.id.recycleViewUpcomingMatches)
        val mainCard = v.findViewById<View>(R.id.mainCard)
        val mainCardProgressBar = v.findViewById<ProgressBar>(R.id.mainCardProgressBar)
        val recyclerViewProgressBar = v.findViewById<ProgressBar>(R.id.recyclerViewProgressBar)

        val c = context
        var imgTeam1: String? = null
        var imgTeam2: String? = null



        matchesListView.layoutManager = LinearLayoutManager(context)
        Log.d("RespoCheck","No response")

        lifecycleScope.launchWhenCreated {
            try {
                mainCardProgressBar.visibility = View.GONE
                val response= retrofit.getLatestMatch()
                if (response.isSuccessful) {
                    imgTeam1 =  response.body()?.result?.team1?.coverimg.toString()
                    imgTeam2 =  response.body()?.result?.team2?.coverimg.toString()
                    Log.d("RespoCheck","Got response")
                    setImage(c, teamImg1, imgTeam1!!)
                    setImage(c, teamImg2, imgTeam2!!)
                    predictNowBtn.setOnClickListener {
                        activity?.let { it1 -> showDialogBox(it1, imgTeam1!!, imgTeam2!!) }
                    }
                }
                else {
                    Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e :Exception){
                Log.e("Error",e.localizedMessage)
            }
        }

        lifecycleScope.launchWhenCreated {
            try {
                recyclerViewProgressBar.visibility = View.GONE
                val response = retrofit.getUpcomingMatches()
                if(response.isSuccessful){
                    val upcomingList = response.body()?.result as ArrayList<Results>
                    matchesListView.adapter = context?.let { upcomingMatchesAdapter(it, upcomingList)}
                }
                else
                    Toast.makeText(context, "Response Not Received", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception){
                Log.e("Error",e.localizedMessage)
            }
        }


        var timerTextView = v.findViewById<TextView>(R.id.timer)

         var currentTime = System.currentTimeMillis();
         val now1 = Calendar.getInstance()
         val now2 = Calendar.getInstance()
         now2.set(now1.weekYear, now1.time.month, now1.time.date, 16, 30,0)
         var epoch = now2.timeInMillis
         var timeForCountDown = epoch - currentTime ;


        object : CountDownTimer(timeForCountDown, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                timerTextView.setText(
                    (f.format(hour) + ":" + f.format(min)).toString() + ":" + f.format(sec)
                )
            }
            override fun onFinish() {
                timerTextView.setText("00:00:00")
            }
        }.start()

        return v
    }

    private fun showDialogBox(activity: Activity, imgTeam1: String, imgTeam2: String){
        val dialog = Dialog(activity)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dailog_box_team_selection)
        val team1 = dialog.findViewById<ImageView>(R.id.dialog_team1)
        val team2 = dialog.findViewById<ImageView>(R.id.dialog_team2)
        val dialog_cnf_btn = dialog.findViewById<TextView>(R.id.dialog_cnf_btn)

        setImage(context,team1, imgTeam1)
        setImage(context,team2, imgTeam2)
        team1.setOnClickListener {
            Log.d("clickedT1","click")
            team1.background = resources.getDrawable(R.drawable.orange_stroke_boder)
            team2.background = null
        }
        team2.setOnClickListener {
            Log.d("clickedT1","click")
            team2.background = resources.getDrawable(R.drawable.orange_stroke_boder)
            team1.background = null
        }
        dialog_cnf_btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    class upcomingMatchesAdapter(var context: Context, var MatchList: ArrayList<Results>) : RecyclerView.Adapter<upcomingMatchesAdapter.MatchViewHolder>(){

        class MatchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            var matchDateTextView = itemView.findViewById<TextView>(R.id.date)
            var teamAImg = itemView.findViewById<ImageView>(R.id.upcomingTeamImg1)
            var teamBImg = itemView.findViewById<ImageView>(R.id.upcomingTeamImg2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
            val view = LayoutInflater.from(context). inflate(
                R.layout.card_upcoming_matches, parent,
                false
            )
            return MatchViewHolder(view)
        }

        override fun getItemCount(): Int {
            return MatchList.size
        }

        override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
            var matchModel =  MatchList.get(position)
            holder.matchDateTextView.text = getDate(matchModel.date.toString())
            setImage(context, holder.teamAImg, matchModel.team1?.coverimg.toString())
            setImage(context, holder.teamBImg, matchModel.team2?.coverimg.toString())
        }
    }

}

private fun setImage(context: Context?, imageView: ImageView, imgURl: String){
    Glide
        .with(context!!)
        .load(imgURl)
        .fitCenter()
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

fun getDate(timeInMillis: String): String{
    val sdf = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
    val c = Calendar.getInstance()
    c.timeInMillis = Integer.parseInt(timeInMillis).toLong()
    return sdf.format(c.time).toString()
}
