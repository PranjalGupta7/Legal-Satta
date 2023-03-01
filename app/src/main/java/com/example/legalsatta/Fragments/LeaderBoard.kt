package com.example.legalsatta.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.legalsatta.Models.LeaderBoardResult
import com.example.legalsatta.R
import com.example.legalsatta.Services.RetrofitClass


class LeaderBoard : Fragment() {
    lateinit var rankListView: RecyclerView;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_leader_board, container, false)



        rankListView = v.findViewById<RecyclerView>(R.id.recycleViewLeaderboard)
        rankListView.layoutManager = LinearLayoutManager(context)
//        rankListView .adapter = leaderboardAdapter(context, //list)
        fetchLeaderBoard();
        return v;
    }

    fun fetchLeaderBoard() {
        val retrofit = RetrofitClass.buildService()

        lifecycleScope.launchWhenCreated {
            try {
                val response = retrofit.getLeaderBoardList()
                if (response.isSuccessful) {
                    rankListView.adapter = RankAdapter(
                        requireContext(),
                        response?.body()?.result as ArrayList<LeaderBoardResult>
                    )
                } else {
                    Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage)
            }
        }

    }
}


class RankAdapter(var context: Context, var leaderboardList: ArrayList<LeaderBoardResult>) :
    RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView = itemView.findViewById<TextView>(R.id.name)
        var rank = itemView.findViewById<TextView>(R.id.rank)
        var score = itemView.findViewById<TextView>(R.id.score)
        var card = itemView.findViewById<View>(R.id.rank_card)
        var img=itemView.findViewById<ImageView>(R.id.rank_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.rank_card, parent,
            false
        )
        return RankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        var rankModel = leaderboardList.get(position)
        if (position == 0) {
            holder.card.setBackgroundTintList(
                context.getResources().getColorStateList(R.color.gold)

            );
            holder.img.setImageResource(R.drawable.first_1)

        } else if (position == 1) {
            holder.card.setBackgroundTintList(
                context.getResources().getColorStateList(R.color.silver)
            );
            holder.img.setImageResource(R.drawable.second_2)

        } else if (position == 2) {
            holder.card.setBackgroundTintList(
                context.getResources().getColorStateList(R.color.bronze)
            );
            holder.img.setImageResource(R.drawable.third_3)

        }
        holder.nameTextView.text = rankModel.username
        holder.rank.text = (position + 1).toString()
        holder.score.text = rankModel.score.toString()
    }
}


//
//data class Leaderboard(val id: Int, val name: String, val rank: Int, val score: Int)