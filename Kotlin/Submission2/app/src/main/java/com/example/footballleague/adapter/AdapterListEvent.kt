package com.example.footballleague.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.data.pojo.match.Event
import com.example.footballleague.layout.activity.MainActivity
import com.example.footballleague.util.DtFormat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

        holder.bindItems(listEvent[position], detailLigaViewModel)

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
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(items: Event, detailLigaViewModel: DetailLigaViewModel) {

            if (items.intHomeScore != null) {
                itemView.homeScored.text = items.intHomeScore.toString()
            }
            if (items.intAwayScore != null) {
                itemView.awayScored.text = items.intAwayScore.toString()
            }

            itemView.homeTeamName.text = items.strHomeTeam
            itemView.awayTeamName.text = items.strAwayTeam
            itemView.titleTextView.text = items.strLeague
            itemView.seasonEvent.text = "Season ${items.strSeason.toString()}"
            if (items.strHomeGoalDetails != null) {
                val homeGoalScorer = items.strHomeGoalDetails.toString().replace(";".toRegex(), ";\n")
                itemView.homeGoalScorer.text = homeGoalScorer
            }
            if (items.strAwayGoalDetails != null) {
                val awayGoalScorer = items.strAwayGoalDetails.toString().replace(";".toRegex(), ";\n")
                itemView.awayGoalScorer.text = awayGoalScorer
            }
            if (items.strHomeRedCards != null) {
                val homeRedCards = items.strHomeRedCards.toString().replace(";".toRegex(), ";\n")
                itemView.homeRedCards.text = homeRedCards
            }
            if (items.strAwayRedCards != null) {
                val awayRedCards = items.strAwayRedCards.toString().replace(";".toRegex(), ";\n")
                itemView.awayRedCards.text = awayRedCards
            }
            if (items.strHomeYellowCards != null) {
                val homeYellowCards = items.strHomeYellowCards.toString().replace(";".toRegex(), ";\n")
                itemView.homeYellowCards.text = homeYellowCards
            }
            if (items.strAwayYellowCards != null) {
                val awayYellowCards = items.strAwayYellowCards.toString().replace(";".toRegex(), ";\n")
                itemView.awayYellowCards.text = awayYellowCards
            }
            if (items.intHomeShots != null) {
                itemView.homeShoots.text = items.intHomeShots.toString()
            }
            if (items.intAwayShots != null) {
                itemView.awayShoots.text = items.intAwayShots.toString()
            }
            if (items.strHomeLineupGoalkeeper != null) {
                val homeGoalKeeper = items.strHomeLineupGoalkeeper.toString().replace(";".toRegex(), ";\n")
                itemView.homeGoalKeeper.text = homeGoalKeeper
            }
            if (items.strAwayLineupGoalkeeper != null) {
                val awayGoalKeeper = items.strAwayLineupGoalkeeper.toString().replace(";".toRegex(), ";\n")
                itemView.awayGoalKeeper.text = awayGoalKeeper
            }
            if (items.strHomeLineupDefense != null) {
                val homeBek = items.strHomeLineupDefense.toString().replace(";".toRegex(), ";\n")
                itemView.homeBek.text = homeBek
            }
            if (items.strAwayLineupDefense != null) {
                val awayBek = items.strAwayLineupDefense.toString().replace(";".toRegex(), ";\n")
                itemView.awayBek.text = awayBek
            }
            if (items.strHomeLineupForward != null) {
                val homeForward = items.strHomeLineupForward.toString().replace(";".toRegex(), ";\n")
                itemView.homeForward.text = homeForward
            }
            if (items.strAwayLineupForward != null) {
                val awayForward = items.strAwayLineupForward.toString().replace(";".toRegex(), ";\n")
                itemView.awayForward.text = awayForward
            }
            if (items.strHomeLineupSubstitutes != null) {
                val homeSubtitutes = items.strHomeLineupSubstitutes.toString().replace(";".toRegex(), ";\n")
                itemView.homeSubtitutes.text = homeSubtitutes
            }
            if (items.strAwayLineupSubstitutes != null) {
                val awaySubtitutes = items.strAwayLineupSubstitutes.toString().replace(";".toRegex(), ";\n")
                itemView.awaySubtitutes.text = awaySubtitutes
            }

            if (items.strTime != null) {
                val strTime = items.strTime.replace(" GMT".toRegex(), "")
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
                try {
                    detailLigaViewModel.getDataTeam(items.idHomeTeam!!)
                    Picasso.get().load(detailLigaViewModel.listDataTeam[0].strTeamBadge!!).into(itemView.homeBadge)
                } catch (e: KotlinNullPointerException) {
                    Log.e("ERROR_KEY", e.toString())
                }
            }

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    detailLigaViewModel.getDataTeam(items.idAwayTeam!!)
                    Picasso.get().load(detailLigaViewModel.listDataTeam[0].strTeamBadge!!).into(itemView.awayBadge)
                } catch (e: KotlinNullPointerException) {
                    Log.e("ERROR_KEY", e.toString())
                }

            }
        }
    }

}