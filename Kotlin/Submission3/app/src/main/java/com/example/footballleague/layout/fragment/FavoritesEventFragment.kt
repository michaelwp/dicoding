package com.example.footballleague.layout.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.R
import com.example.footballleague.adapter.AdapterListFavoriteEvent
import com.example.footballleague.data.FavoriteEvent
import com.example.footballleague.db.database
import com.example.footballleague.layout.activity.LeagueDetailActivity
import com.example.footballleague.layout.activity.MainActivity
import kotlinx.android.synthetic.main.event_favorite_list_fragment.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesEventFragment(var activityContext: String) : Fragment(), AdapterListFavoriteEvent.OnClickListener {

    private var favoriteEvents: MutableList<FavoriteEvent> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.event_favorite_list_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showFavoriteEvent()
    }

    fun showFavoriteEvent() {
        favoriteEvents.clear()
        activity!!.database.use {
            val result =
                    if (activityContext == "mainActivity") {
                        select(FavoriteEvent.TABLE_FAVORITE)
                    } else {
                        select(FavoriteEvent.TABLE_FAVORITE)
                                .whereSimple("${FavoriteEvent.ID_LEAGUE} = ?", (context as LeagueDetailActivity).leagueId)
                    }

            val favorite = result.parseList(classParser<FavoriteEvent>())
            favoriteEvents.addAll(favorite)
        }
        recycler_favorite_event.layoutManager = LinearLayoutManager(activity)
        recycler_favorite_event.adapter =
                AdapterListFavoriteEvent(
                        if (activityContext == "mainActivity") {
                            (context as MainActivity)
                        } else {
                            (context as LeagueDetailActivity)
                        },
                        favoriteEvents,
                        this)
        recycler_favorite_event.adapter!!.notifyDataSetChanged()
    }

    override fun onRefreshAdapter() {
        showFavoriteEvent()
    }
}
