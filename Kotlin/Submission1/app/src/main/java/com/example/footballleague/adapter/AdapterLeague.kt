package com.example.footballleague.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.data.DataLeague
import com.example.footballleague.layout.LeagueDetail
import com.example.footballleague.layout.LeagueList
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity

class AdapterLeague(
    private val context: Context,
    private val items: List<DataLeague>
) : RecyclerView.Adapter<AdapterLeague.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById(LeagueList.league_image)
            textView = itemView.findViewById(LeagueList.league_name)
        }

        fun bindItems(items: DataLeague) {
            textView.text = items.name
            items.image.let { Picasso.get().load(it).into(imageView) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LeagueList().createView(AnkoContext.create(parent.context, parent)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])

        holder.view.setOnClickListener {
            context.startActivity<LeagueDetail>(
                "dataLeague" to items[position]
            )
        }
    }

    override fun getItemCount(): Int = items.size

}