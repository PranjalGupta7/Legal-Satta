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
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.legalsatta.Interface.UrlEndpoints
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass
import retrofit2.Retrofit
import java.text.DecimalFormat
import java.text.NumberFormat


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
            lifecycleScope.launchWhenCreated {
                try {
                    val response= retrofit.getLatestMatch()
                    if (response.isSuccessful) {
                            Glide
                                .with(this@HomeScreen)
                                .load(response.body()?.result?.team1?.get(0)?.coverimg)
                                .fitCenter()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(teamImg1);

                            Glide
                                .with(this@HomeScreen)
                                .load(response.body()?.result?.team2?.get(0)?.coverimg)
                                .fitCenter()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(teamImg2);
                    } else {
                        Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    }
                }catch (e :Exception){
                    Log.e("Error",e.localizedMessage)
                }
            }


        val teamImg1 = v.findViewById<ImageView>(R.id.teamImg1)
        val teamImg2 = v.findViewById<ImageView>(R.id.teamImg2)
        val predictNowBtn = v.findViewById<TextView>(R.id.predictNowBtn)

        Glide
            .with(this)
            .load("https://i.pinimg.com/736x/70/15/05/701505a5bbe24320e3c33f26808cdaac.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(teamImg1);
        Glide
            .with(this)
            .load("https://i.pinimg.com/474x/9b/c1/5b/9bc15be6369be54c64d032cd68a5526a.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(teamImg2);

        predictNowBtn.setOnClickListener {
            activity?.let { it1 -> showDialogBox(it1) }
        }

        var timerTextView = v.findViewById<TextView>(R.id.timer)

         var currentTime = System.currentTimeMillis();
         var timeForCountDown =  currentTime - 59400000 ;
        Log.d("------", currentTime.toString())
        Log.d("-----t", timeForCountDown.toString())

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

        var matchesListView = v.findViewById<RecyclerView>(R.id.recycleViewUpcomingMatches)
        matchesListView .layoutManager = LinearLayoutManager(context)
//        matchesListView .adapter = upcomingMatchesAdapter(context, //list)
//        )
        return v;
    }

    private fun showDialogBox(activity: Activity){
        val dialog = Dialog(activity)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dailog_box_team_selection)
        val team1 = dialog.findViewById<ImageView>(R.id.dialog_team1)
        val team2 = dialog.findViewById<ImageView>(R.id.dialog_team2)
        val dialog_cnf_btn = dialog.findViewById<TextView>(R.id.dialog_cnf_btn)

        setImage(team1,"")
        setImage(team2,"")
        team1.setOnClickListener {
            team1.background = resources.getDrawable(R.drawable.orange_stroke_boder)
            team2.background = null
        }
        team2.setOnClickListener {
            team2.background = resources.getDrawable(R.drawable.orange_stroke_boder)
            team1.background = null
        }
        dialog_cnf_btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun setImage(imageView: ImageView, imgURl: String){
        Glide
            .with(this)
            .load(imgURl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }

    class upcomingMatchesAdapter(var context: Context, var MatchList: ArrayList<UpcomingMatches>) : RecyclerView.Adapter<upcomingMatchesAdapter.MatchViewHolder>(){

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
            holder.matchDateTextView.text = matchModel.date
            holder.teamAImg.setImageResource(matchModel.teamA)
            holder.teamBImg.setImageResource(matchModel.teamB)
        }
    }
}

data class UpcomingMatches(val id: Int, val date: String, val teamA: Int, val teamB: Int)