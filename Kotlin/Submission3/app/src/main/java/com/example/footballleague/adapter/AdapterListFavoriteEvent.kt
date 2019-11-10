package com.example.footballleague.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.data.FavoriteEvent
import com.example.footballleague.db.database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_list.view.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class AdapterListFavoriteEvent(
    private val context: Context,
    private val listFavorite: List<FavoriteEvent>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<AdapterListFavoriteEvent.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun onDetails() {
            holder.view.frameMoreDetails.visibility = View.VISIBLE
            holder.view.imageNavigator.image = context.resources.getDrawable(R.drawable.ic_keyboard_arrow_up)
        }

        fun offDetails() {
            holder.view.frameMoreDetails.visibility = View.GONE
            holder.view.imageNavigator.image = context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down)
        }

        holder.view.moreDetailsButton.onFocusChange { _, hasFocus ->
            if (hasFocus) {
                onDetails()
                return@onFocusChange
            }
            offDetails()
        }

        holder.view.moreDetailsButton.setOnClickListener {
            if (holder.view.frameMoreDetails.visibility == View.GONE) {
                onDetails()
                return@setOnClickListener
            }
            offDetails()
        }

        holder.itemView.fav_icon.image = context.resources.getDrawable(R.drawable.ic_favorite_red)
        holder.itemView.fav_icon.setOnClickListener {
            context.database.use {
                val del = delete(
                    FavoriteEvent.TABLE_FAVORITE,
                    "${FavoriteEvent.ID_EVENT} = {idEvent}",
                    "idEvent" to listFavorite[position].idEvent.toString()
                )
                if (del == 1) {
                    Toast.makeText(
                        context,
                        "${listFavorite[position].strEvent} has removed from history",
                        Toast.LENGTH_LONG
                    ).show()
                    listener.onRefreshAdapter()
                }
            }
        }

        holder.bindItems(listFavorite[position], context)
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(items: FavoriteEvent, context: Context) {
            itemView.homeScored.text = items.intHomeScore
            itemView.awayScored.text = items.intAwayScore
            itemView.homeTeamName.text = items.strHomeTeam
            itemView.awayTeamName.text = items.strAwayTeam
            itemView.titleTextView.text = items.strLeague
            itemView.homeGoalScorer.text = items.intHomeScore
            itemView.awayGoalScorer.text = items.intAwayScore
            itemView.seasonEvent.text = items.strSeason
            itemView.homeGoalScorer.text = items.strHomeGoalDetails
            itemView.awayGoalScorer.text = items.strAwayGoalDetails
            itemView.homeRedCards.text = items.strHomeRedCards
            itemView.awayRedCards.text = items.strAwayRedCards
            itemView.homeYellowCards.text = items.strHomeYellowCards
            itemView.awayYellowCards.text = items.strAwayYellowCards
            itemView.homeShoots.text = items.intHomeShots
            itemView.awayShoots.text = items.intAwayShots
            itemView.homeGoalKeeper.text = items.strHomeLineupGoalkeeper
            itemView.awayGoalKeeper.text = items.strAwayLineupGoalkeeper
            itemView.homeBek.text = items.strHomeLineupDefense
            itemView.awayBek.text = items.strAwayLineupDefense
            itemView.homeForward.text = items.strHomeLineupForward
            itemView.awayForward.text = items.strAwayLineupForward
            itemView.homeSubtitutes.text = items.strHomeLineupSubstitutes
            itemView.awaySubtitutes.text = items.strAwayLineupSubstitutes
            itemView.dateTextView.text = "${items.dateEvent} ${items.strTime}"

            try {
                Picasso.get().load(items.strHomeTeamBadge).into(itemView.homeBadge)
                Picasso.get().load(items.strAwayTeamBadge).into(itemView.awayBadge)
            } catch (e: IllegalArgumentException) {
                Log.e("ERROR_KEY", e.toString())
            }
        }
    }

    interface OnClickListener {
        fun onRefreshAdapter()
    }
}