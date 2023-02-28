package com.example.legalsatta.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.legalsatta.R


class LeaderBoard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_leader_board, container, false)


//        var rankListView = v.findViewById<RecyclerView>(R.id.recycleViewLeaderboard)
//        rankListView .layoutManager = LinearLayoutManager(context)
//        rankListView .adapter = leaderboardAdapter(context, //list)

        return v;
    }
}
//    class leaderboardAdapter(var context: Context, var leaderboardList: ArrayList<Leaderboard>) : RecyclerView.Adapter<leaderboardAdapter.rankViewHolder>(){
//
//        class rankViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//            var nameTextView = itemView.findViewById<TextView>(R.id.name)
//            var rank = itemView.findViewById<TextView>(R.id.rank)
//            var score = itemView.findViewById<TextView>(R.id.score)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rankViewHolder {
//            val view = LayoutInflater.from(context). inflate(
//                R.layout.rank_card, parent,
//                false
//            )
//            return rankViewHolder(view)
//        }
//
//        override fun getItemCount(): Int {
//            return leaderboardList.size
//        }
//
//        override fun onBindViewHolder(holder: rankViewHolder, position: Int) {
//            var rankModel =  leaderboardList.get(position)
//            holder.nameTextView.text = rankModel.name
//            holder.rank.text = rankModel.rank.toString()
//            holder.score.text = rankModel.score.toString()
//        }
//    }
//
//
//}
//
//data class Leaderboard(val id: Int, val name: String, val rank: Int, val score: Int)