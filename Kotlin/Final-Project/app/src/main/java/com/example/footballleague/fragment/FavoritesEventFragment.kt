package com.example.footballleague.fragment


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.activity.LeagueDetailActivity
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class FavoritesEventFragment(var activityContext: String) : Fragment() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DetailLigaViewModel::class.java)
    }
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var listFavRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.event_favorite_list_fragment, container, false)

        shimmer = view.findViewById(R.id.shimmerListFav)
        listFavRecycler = view.findViewById(R.id.recycler_favorite_event)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listFavRecycler.layoutManager = LinearLayoutManager(activity!!)
        showFavoriteEvent()
    }

    fun showFavoriteEvent() {
        val activity: Activity = activity!!

        Handler().postDelayed({
            detailLigaViewModel.getListFavEvent(
                if (activityContext == "mainActivity") {
                    ""
                } else {
                    (context as LeagueDetailActivity).leagueId
                },
                shimmer,
                listFavRecycler,
                activity
            )
        }, 1000)
    }
}
