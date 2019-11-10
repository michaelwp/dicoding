package com.example.footballleague.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.data.pojo.dataliga.League
import com.example.footballleague.layout.activity.LeagueDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.league_list.view.*
import org.jetbrains.anko.startActivity

class AdapterListLeague(
        private val context: Context,
        private val items: List<League>
) : RecyclerView.Adapter<AdapterListLeague.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(items: League) {
            itemView.league_name.text = items.strLeague
            Picasso.get().load(items.strBadge).into(itemView.league_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.league_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])

        holder.view.setOnClickListener {
            context.startActivity<LeagueDetailActivity>(
                    "idLeague" to items[position].idLeague
            )
        }
    }

    override fun getItemCount(): Int = items.size

}