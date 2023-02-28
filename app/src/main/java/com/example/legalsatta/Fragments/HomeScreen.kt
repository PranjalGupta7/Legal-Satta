package com.example.legalsatta.Fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.legalsatta.R
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

        }

        var timerTextView = v.findViewById<TextView>(R.id.timer)

        object : CountDownTimer(50000, 1000) {

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