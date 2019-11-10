package com.example.footballleague.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.adapter.AdapterListKlasemen.ViewHolder
import com.example.footballleague.data.api.pojo.klasemen.Table
import kotlinx.android.synthetic.main.klasemen_list.view.*

class AdapterListKlasemen(
    private val context: Context,
    private val listKlasemen: List<Table>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.klasemen_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listKlasemen.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listKlasemen[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        lateinit var res: String
        fun twoDigit(input: Int): String {
            if ((input < 10) and (input >= 0)) {
                res = "0$input"
            } else if ((input < 0) and (input > -10)) {
                val x = input.toString().substring(1)
                res = "-0$x"
            } else {
                res = "$input"
            }
            return res
        }

        fun bindItems(items: Table) {
            itemView.teamName.text = items.name
            itemView.teamPlayed.text = twoDigit(items.played!!.toInt())
            itemView.teamGoalsFor.text = twoDigit(items.goalsfor!!.toInt())
            itemView.teamGoalsAgainst.text = twoDigit(items.goalsagainst!!.toInt())
            itemView.teamGoalsDifference.text = twoDigit(items.goalsdifference!!.toInt())
            itemView.teamWin.text = twoDigit(items.win!!.toInt())
            itemView.teamDraw.text = twoDigit(items.draw!!.toInt())
            itemView.teamLoss.text = twoDigit(items.loss!!.toInt())
            itemView.teamTotal.text = twoDigit(items.total!!.toInt())
        }
    }

}