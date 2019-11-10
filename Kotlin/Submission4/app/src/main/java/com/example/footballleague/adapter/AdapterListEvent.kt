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
import com.example.footballleague.data.remotedatasource.pojo.match.Event
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

            /*
            var fav: Boolean = false
            itemView.context.database.use {
                val itemSize = query(
                    FavoriteEvent.TABLE_FAVORITE,
                    arrayOf("*"),
                    "${FavoriteEvent.ID_EVENT} = ${items.idEvent}", null,
                    null, null,
                    null, null
                ).count
                if (itemSize > 0) {
                    itemView.fav_icon.image =
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                    fav = true
                }
            }


            itemView.fav_icon.setOnClickListener {
                if (fav == true) {
                    val status = RemovedFromFavorite(
                        context = itemView.context,
                        idEvent = items.idEvent!!,
                        strEvent = items.strEvent!!
                    ).removeFromFavorite()

                    if (status == true) {
                        it.fav_icon.image =
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_favorite_border_red
                            )
                        fav = false
                    }
                    return@setOnClickListener
                }

                it.fav_icon.image =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red)
                fav = true
                AddToFavorites(
                    context = itemView.context,
                    idEvent = items.idEvent.toString(),
                    idLeague = items.idLeague.toString(),
                    strEvent = items.strEvent.toString(),
                    strLeague = items.strLeague.toString(),
                    strHomeTeam = items.strHomeTeam.toString(),
                    strAwayTeam = items.strAwayTeam.toString(),
                    dateEvent = items.dateEvent.toString(),
                    strTime = items.strTime.toString(),
                    intHomeScore = items.intHomeScore.toString(),
                    intAwayScore = items.intAwayScore.toString(),
                    strSeason = items.strSeason.toString(),
                    strHomeGoalDetails = items.strHomeGoalDetails.toString(),
                    strAwayGoalDetails = items.strAwayGoalDetails.toString(),
                    strHomeRedCards = items.strHomeRedCards.toString(),
                    strAwayRedCards = items.strAwayRedCards.toString(),
                    strHomeYellowCards = items.strHomeYellowCards.toString(),
                    strAwayYellowCards = items.strAwayYellowCards.toString(),
                    intHomeShots = items.intHomeShots.toString(),
                    intAwayShots = items.intAwayShots.toString(),
                    strHomeLineupGoalkeeper = items.strHomeLineupGoalkeeper.toString(),
                    strAwayLineupGoalkeeper = items.strAwayLineupGoalkeeper.toString(),
                    strHomeLineupDefense = items.strHomeLineupDefense.toString(),
                    strAwayLineupDefense = items.strAwayLineupDefense.toString(),
                    strHomeLineupForward = items.strHomeLineupForward.toString(),
                    strAwayLineupForward = items.strAwayLineupForward.toString(),
                    strHomeLineupSubstitutes = items.strHomeLineupSubstitutes.toString(),
                    strAwayLineupSubstitutes = items.strAwayLineupSubstitutes.toString(),
                    strHomeBadge = items.strHomeTeamBadge.toString(),
                    strAwayBadge = items.strAwayTeamBadge.toString()
                ).saveToFavorites()
            }*/

        }
    }

    /*
    class AddToFavorites(
        var context: Context,
        var idEvent: String = "-",
        var idLeague: String = "-",
        var strEvent: String = "-",
        var strLeague: String = "-",
        var strHomeTeam: String = "-",
        var strAwayTeam: String = "-",
        var dateEvent: String = "-",
        var strTime: String = "-",
        var intHomeScore: String = "-",
        var intAwayScore: String = "-",
        val strSeason: String = "-",
        var strHomeGoalDetails: String = "_",
        var strAwayGoalDetails: String = "_",
        val strHomeRedCards: String? = "-",
        val strAwayRedCards: String? = "-",
        val strHomeYellowCards: String? = "-",
        val strAwayYellowCards: String? = "-",
        val intHomeShots: String? = "-",
        val intAwayShots: String? = "-",
        val strHomeLineupGoalkeeper: String? = "-",
        val strAwayLineupGoalkeeper: String? = "-",
        val strHomeLineupDefense: String? = "-",
        val strAwayLineupDefense: String? = "-",
        val strHomeLineupForward: String? = "-",
        val strAwayLineupForward: String? = "-",
        val strHomeLineupSubstitutes: String? = "-",
        val strAwayLineupSubstitutes: String? = "-",
        var strHomeBadge: String = "-",
        var strAwayBadge: String = "-"
    ) {
        fun saveToFavorites() {
            try {
                context.database.use {
                    val status = insert(
                        FavoriteEvent.TABLE_FAVORITE,
                        FavoriteEvent.ID_EVENT to idEvent,
                        FavoriteEvent.ID_LEAGUE to idLeague,
                        FavoriteEvent.STR_EVENT to strEvent,
                        FavoriteEvent.STR_LEAGUE to strLeague,
                        FavoriteEvent.STR_HOME_TEAM to strHomeTeam,
                        FavoriteEvent.STR_AWAY_TEAM to strAwayTeam,
                        FavoriteEvent.DATE_EVENT to dateEvent,
                        FavoriteEvent.STR_TIME to strTime,
                        FavoriteEvent.INT_HOME_SCORE to intHomeScore,
                        FavoriteEvent.INT_AWAY_SCORE to intAwayScore,
                        FavoriteEvent.STR_SEASON to strSeason,
                        FavoriteEvent.STR_HOME_GOAL_DETAILS to strHomeGoalDetails,
                        FavoriteEvent.STR_AWAY_GOAL_DETAILS to strAwayGoalDetails,
                        FavoriteEvent.STR_HOME_RED_CARDS to strHomeRedCards,
                        FavoriteEvent.STR_AWAY_RED_CARDS to strAwayRedCards,
                        FavoriteEvent.STR_HOME_YELLOW_CARDS to strHomeYellowCards,
                        FavoriteEvent.STR_AWAY_YELLOW_CARDS to strAwayYellowCards,
                        FavoriteEvent.INT_HOME_SHOTS to intHomeShots,
                        FavoriteEvent.INT_AWAY_SHOTS to intAwayShots,
                        FavoriteEvent.STR_HOME_LINEUP_GOAL_KEEPER to strHomeLineupGoalkeeper,
                        FavoriteEvent.STR_AWAY_LINEUP_GOAL_KEEPER to strAwayLineupGoalkeeper,
                        FavoriteEvent.STR_HOME_LINEUP_DEFENSE to strHomeLineupDefense,
                        FavoriteEvent.STR_AWAY_LINEUP_DEFENSE to strAwayLineupDefense,
                        FavoriteEvent.STR_HOME_LINEUP_FORWARD to strHomeLineupForward,
                        FavoriteEvent.STR_AWAY_LINEUP_FORWARD to strAwayLineupForward,
                        FavoriteEvent.STR_HOME_LINEUP_SUBTITUTES to strHomeLineupSubstitutes,
                        FavoriteEvent.STR_AWAY_LINEUP_SUBTITUTES to strAwayLineupSubstitutes,
                        FavoriteEvent.STR_HOME_TEAM_BADGE to strHomeBadge,
                        FavoriteEvent.STR_AWAY_TEAM_BADGE to strAwayBadge
                    )
                    if (status > 0) {
                        Toast.makeText(
                            context,
                            "${strEvent} successfully added into favorite list",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        return@use
                    }
                    Toast.makeText(
                        context,
                        "${strEvent} failed to add into favorite list",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: SQLiteConstraintException) {
                Log.e("ERROR_KEY", e.toString())
            }
        }

    }

    class RemovedFromFavorite(
        var context: Context,
        var idEvent: String,
        var strEvent: String
    ) {
        fun removeFromFavorite(): Boolean {
            var status: Boolean = false
            context.database.use {
                val del = delete(
                    FavoriteEvent.TABLE_FAVORITE,
                    "${FavoriteEvent.ID_EVENT} = {idEvent}",
                    "idEvent" to idEvent
                )

                if (del > 0) {
                    Toast.makeText(
                        context,
                        "${strEvent} successfully removed from favorite list",
                        Toast.LENGTH_LONG
                    ).show()
                    status = true
                } else {
                    Toast.makeText(
                        context,
                        "${strEvent} failed to remove from favorite list",
                        Toast.LENGTH_LONG
                    ).show()
                    status = false
                }
            }
            return status
        }
    }*/
}