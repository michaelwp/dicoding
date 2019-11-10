package com.example.footballleague.adapter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.data.FavoriteEvent
import com.example.footballleague.data.pojo.match.Event
import com.example.footballleague.db.database
import com.example.footballleague.util.DtFormat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class AdapterListEvent(
        private val context: Context,
        private val listEvent: List<Event>
) : RecyclerView.Adapter<AdapterListEvent.ViewHolder>() {

    private val detailLigaViewModel =
            ViewModelProviders.of(context as FragmentActivity).get(DetailLigaViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listEvent.size
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

        holder.bindItems(listEvent[position], detailLigaViewModel, context)
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(
                items: Event,
                detailLigaViewModel: DetailLigaViewModel,
                context: Context
        ) {
            itemView.homeTeamName.text = items.strHomeTeam
            itemView.awayTeamName.text = items.strAwayTeam
            itemView.titleTextView.text = items.strLeague
            itemView.seasonEvent.text = "Season ${items.strSeason.toString()}"

            //Scored
            var intHomeScored: String = "-"
            var intAwayScored: String = "-"
            if (items.intHomeScore != null) {
                intHomeScored = items.intHomeScore.toString()
                itemView.homeScored.text = intHomeScored
            }
            if (items.intAwayScore != null) {
                intAwayScored = items.intAwayScore.toString()
                itemView.awayScored.text = intAwayScored
            }

            //Goal Scorer
            var strHomeGoalDetails: String = "-"
            var strAwayGoalDetails: String = "-"
            if (items.strHomeGoalDetails != null) {
                strHomeGoalDetails = items.strHomeGoalDetails.toString().replace(";".toRegex(), ";\n")
                itemView.homeGoalScorer.text = strHomeGoalDetails
            }
            if (items.strAwayGoalDetails != null) {
                strAwayGoalDetails = items.strAwayGoalDetails.toString().replace(";".toRegex(), ";\n")
                itemView.awayGoalScorer.text = strAwayGoalDetails
            }

            //RedCards
            var strHomeRedCards: String = "-"
            var strAwayRedCards: String = "-"
            if (items.strHomeRedCards != null) {
                strHomeRedCards = items.strHomeRedCards.toString().replace(";".toRegex(), ";\n")
                itemView.homeRedCards.text = strHomeRedCards
            }
            if (items.strAwayRedCards != null) {
                strAwayRedCards = items.strAwayRedCards.toString().replace(";".toRegex(), ";\n")
                itemView.awayRedCards.text = strAwayRedCards
            }

            //YellowCards
            var strHomeYellowCards: String = "-"
            var strAwayYellowCards: String = "-"
            if (items.strHomeYellowCards != null) {
                strHomeYellowCards = items.strHomeYellowCards.toString().replace(";".toRegex(), ";\n")
                itemView.homeYellowCards.text = strHomeYellowCards
            }
            if (items.strAwayYellowCards != null) {
                strAwayYellowCards = items.strAwayYellowCards.toString().replace(";".toRegex(), ";\n")
                itemView.awayYellowCards.text = strAwayYellowCards
            }

            //Shoots
            var intHomeShots: String = "-"
            var intAwayShots: String = "-"
            if (items.intHomeShots != null) {
                intHomeShots = items.intHomeShots.toString()
                itemView.homeShoots.text = intHomeShots
            }
            if (items.intAwayShots != null) {
                intAwayShots = items.intAwayShots.toString()
                itemView.awayShoots.text = intAwayShots
            }

            //GoalKeeper
            var strHomeLineupGoalkeeper: String = "-"
            var strAwayLineupGoalkeeper: String = "-"
            if (items.strHomeLineupGoalkeeper != null) {
                strHomeLineupGoalkeeper = items.strHomeLineupGoalkeeper.toString().replace(";".toRegex(), ";\n")
                itemView.homeGoalKeeper.text = strHomeLineupGoalkeeper
            }
            if (items.strAwayLineupGoalkeeper != null) {
                strAwayLineupGoalkeeper = items.strAwayLineupGoalkeeper.toString().replace(";".toRegex(), ";\n")
                itemView.awayGoalKeeper.text = strAwayLineupGoalkeeper
            }

            //Defense/Bek
            var strHomeLineupDefense: String = "-"
            var strAwayLineupDefense: String = "-"
            if (items.strHomeLineupDefense != null) {
                strHomeLineupDefense = items.strHomeLineupDefense.toString().replace(";".toRegex(), ";\n")
                itemView.homeBek.text = strHomeLineupDefense
            }
            if (items.strAwayLineupDefense != null) {
                strAwayLineupDefense = items.strAwayLineupDefense.toString().replace(";".toRegex(), ";\n")
                itemView.awayBek.text = strAwayLineupDefense
            }

            //Forward/Striker
            var strHomeLineupForward: String = "-"
            var strAwayLineupForward: String = "-"
            if (items.strHomeLineupForward != null) {
                strHomeLineupForward = items.strHomeLineupForward.toString().replace(";".toRegex(), ";\n")
                itemView.homeForward.text = strHomeLineupForward
            }
            if (items.strAwayLineupForward != null) {
                strAwayLineupForward = items.strAwayLineupForward.toString().replace(";".toRegex(), ";\n")
                itemView.awayForward.text = strAwayLineupForward
            }

            //Subtitutes
            var strHomeLineupSubstitutes: String = "-"
            var strAwayLineupSubstitutes: String = "-"
            if (items.strHomeLineupSubstitutes != null) {
                strHomeLineupSubstitutes = items.strHomeLineupSubstitutes.toString().replace(";".toRegex(), ";\n")
                itemView.homeSubtitutes.text = strHomeLineupSubstitutes
            }
            if (items.strAwayLineupSubstitutes != null) {
                strAwayLineupSubstitutes = items.strAwayLineupSubstitutes.toString().replace(";".toRegex(), ";\n")
                itemView.awaySubtitutes.text = strAwayLineupSubstitutes
            }

            //time
            var strTime: String = "-"
            if (items.strTime != null) {
                strTime = items.strTime.replace(" GMT".toRegex(), "")
                val strDateTime = "${items.dateEvent} ${strTime}"
                itemView.dateTextView.text = "${DtFormat().formatTo(DtFormat().toDate(strDateTime))} WIB"
            } else {
                val strDateTime = "${items.dateEvent}"
                itemView.dateTextView.text =
                        DtFormat().formatTo(
                                DtFormat().toDate(
                                        inputDtTime = strDateTime,
                                        dateTimeStr = "yyyy-MM-dd"
                                ),
                                dateTimeFormat = "dd-MMM-yyyy"
                        )
            }

            GlobalScope.launch(Dispatchers.Main) {
                lateinit var homeBadge: String
                lateinit var awayBadge: String

                try {
                    detailLigaViewModel.getDataTeam(items.idHomeTeam!!)
                    homeBadge = detailLigaViewModel.listDataTeam[0].strTeamBadge!!
                    Picasso.get().load(homeBadge).into(itemView.homeBadge)
                } catch (e: KotlinNullPointerException) {
                    Log.e("ERROR_KEY", e.toString())
                }

                try {
                    detailLigaViewModel.getDataTeam(items.idAwayTeam!!)
                    awayBadge = detailLigaViewModel.listDataTeam[0].strTeamBadge!!
                    Picasso.get().load(awayBadge).into(itemView.awayBadge)
                } catch (e: KotlinNullPointerException) {
                    Log.e("ERROR_KEY", e.toString())
                }

                var fav: Boolean = false
                context.database.use {
                    val itemSize = query(
                            FavoriteEvent.TABLE_FAVORITE,
                            arrayOf("*"),
                            "${FavoriteEvent.ID_EVENT} = ${items.idEvent}", null,
                            null, null,
                            null, null
                    ).count
                    if (itemSize > 0) {
                        itemView.fav_icon.image = context.resources.getDrawable(R.drawable.ic_favorite_red)
                        fav = true
                    }
                }


                itemView.fav_icon.setOnClickListener {
                    if (fav == true) {
                        val status = RemovedFromFavorite(
                                context = context,
                                idEvent = items.idEvent!!,
                                strEvent = items.strEvent!!
                        ).removeFromFavorite()

                        if (status == true) {
                            it.fav_icon.image = context.resources.getDrawable(R.drawable.ic_favorite_border_red)
                            fav = false
                        }
                        return@setOnClickListener
                    }

                    it.fav_icon.image = context.resources.getDrawable(R.drawable.ic_favorite_red)
                    fav = true
                    AddToFavorites(
                            context = context,
                            idEvent = items.idEvent!!,
                            idLeague = items.idLeague!!,
                            strEvent = items.strEvent!!,
                            strLeague = items.strLeague!!,
                            strHomeTeam = items.strHomeTeam!!,
                            strAwayTeam = items.strAwayTeam!!,
                            dateEvent = items.dateEvent!!,
                            strTime = strTime,
                            intHomeScore = intHomeScored,
                            intAwayScore = intAwayScored,
                            strSeason = "Season ${items.strSeason}",
                            strHomeGoalDetails = strHomeGoalDetails,
                            strAwayGoalDetails = strAwayGoalDetails,
                            strHomeRedCards = strHomeRedCards,
                            strAwayRedCards = strAwayRedCards,
                            strHomeYellowCards = strHomeYellowCards,
                            strAwayYellowCards = strAwayYellowCards,
                            intHomeShots = intHomeShots,
                            intAwayShots = intAwayShots,
                            strHomeLineupGoalkeeper = strHomeLineupGoalkeeper,
                            strAwayLineupGoalkeeper = strAwayLineupGoalkeeper,
                            strHomeLineupDefense = strHomeLineupDefense,
                            strAwayLineupDefense = strAwayLineupDefense,
                            strHomeLineupForward = strHomeLineupForward,
                            strAwayLineupForward = strAwayLineupForward,
                            strHomeLineupSubstitutes = strHomeLineupSubstitutes,
                            strAwayLineupSubstitutes = strAwayLineupSubstitutes,
                            strHomeBadge = homeBadge,
                            strAwayBadge = awayBadge
                    ).saveToFavorites()
                }
            }

        }
    }

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
                        Toast.makeText(context, "${strEvent} successfully added into favorite list", Toast.LENGTH_LONG).show()
                        return@use
                    }
                    Toast.makeText(context, "${strEvent} failed to add into favorite list", Toast.LENGTH_LONG).show()
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
    }
}