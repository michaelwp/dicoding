package com.example.footballleague.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.data.api.pojo.match.Event
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_list.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class AdapterListEvent(
    private val context: Context,
    private val listEvent: List<Event>
) : RecyclerView.Adapter<AdapterListEvent.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun onDetails() {
            holder.view.frameMoreDetails.visibility = View.VISIBLE
            holder.view.imageNavigator.image =
                ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_up)
        }

        fun offDetails() {
            holder.view.frameMoreDetails.visibility = View.GONE
            holder.view.imageNavigator.image =
                ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_down)
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
        try {
            holder.bindItems(listEvent[position], context)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("ERR_LIST_EVENT_ADAPT", e.toString())
        }

    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(items: Event, context: Context) {

            val detailLigaViewModel: DetailLigaViewModel by lazy {
                ViewModelProviders.of(context as FragmentActivity)
                    .get(DetailLigaViewModel::class.java)
            }

            itemView.homeTeamName.text = items.strHomeTeam
            itemView.awayTeamName.text = items.strAwayTeam
            itemView.titleTextView.text = items.strLeague
            itemView.seasonEvent.text = items.strSeason
            itemView.homeScored.text = items.intHomeScore.toString()
            itemView.awayScored.text = items.intAwayScore.toString()
            itemView.homeGoalScorer.text = items.strHomeGoalDetails.toString()
            itemView.awayGoalScorer.text = items.strAwayGoalDetails.toString()
            itemView.homeRedCards.text = items.strHomeRedCards.toString()
            itemView.awayRedCards.text = items.strAwayRedCards.toString()
            itemView.homeYellowCards.text = items.strHomeYellowCards.toString()
            itemView.awayYellowCards.text = items.strAwayYellowCards.toString()
            itemView.homeShoots.text = items.intHomeShots.toString()
            itemView.awayShoots.text = items.intAwayShots.toString()
            itemView.homeGoalKeeper.text = items.strHomeLineupGoalkeeper.toString()
            itemView.awayGoalKeeper.text = items.strAwayLineupGoalkeeper.toString()
            itemView.homeBek.text = items.strHomeLineupDefense.toString()
            itemView.awayBek.text = items.strAwayLineupDefense.toString()
            itemView.homeMiddle.text = items.strHomeLineupMidfield.toString()
            itemView.awayMiddle.text = items.strAwayLineupMidfield.toString()
            itemView.homeForward.text = items.strHomeLineupForward.toString()
            itemView.awayForward.text = items.strAwayLineupForward.toString()
            itemView.homeSubtitutes.text = items.strHomeLineupSubstitutes.toString()
            itemView.awaySubtitutes.text = items.strAwayLineupSubstitutes.toString()
            itemView.homeFormation.text = items.strHomeFormation.toString()
            itemView.awayFormation.text = items.strAwayFormation.toString()
            if (items.strTime != "-") {
                itemView.dateTextView.text = "${items.dateEvent} ${items.strTime} WIB"
            } else {
                itemView.dateTextView.text = items.dateEvent
            }
            Picasso.get().load(items.strHomeTeamBadge.toString()).into(itemView.homeBadge)
            Picasso.get().load(items.strAwayTeamBadge.toString()).into(itemView.awayBadge)

            var fav: Boolean = items.favorite!!

            when (items.favorite) {
                true -> {
                    itemView.fav_icon.image =
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                }
                else -> {
                    itemView.fav_icon.image =
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_favorite_border_red
                        )
                }
            }

            itemView.fav_icon.setOnClickListener {
                when (fav) {
                    false -> {
                        itemView.fav_icon.image =
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                        fav = true
                    }
                    else -> {
                        itemView.fav_icon.image =
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_favorite_border_red
                            )

                        fav = false
                    }
                }
                detailLigaViewModel.updateFavoriteStatus(fav, items.idEvent.toString())
            }
        }
    }
}