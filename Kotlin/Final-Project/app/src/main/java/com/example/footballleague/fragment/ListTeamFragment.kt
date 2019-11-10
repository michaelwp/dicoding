package com.example.footballleague.fragment


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class ListTeamFragment(val fav: Boolean = false) : Fragment() {
    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
    }

    private lateinit var searchView: SearchView
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var teamListRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.team_list_fragment, container, false)
        searchView = view.findViewById<SearchView>(R.id.searchViewTeam)
        shimmer = view.findViewById(R.id.shimmerListTeam)
        teamListRecycler = view.findViewById(R.id.team_list_recycler)
        if (fav) {
            searchView.visibility = View.GONE
        }

        return view
    }

    private fun getListTeamFromService() {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val activity: Activity = activity!!
        val idLeague = activity.intent.getStringExtra("idLeague")

        shimmer.visibility = View.VISIBLE
        teamListRecycler.visibility = View.GONE
        detailLigaViewModel.saveListTeamToRoom(idLeague!!, fragmentManager)

        Handler().postDelayed({
            if (fav) {
                Log.e("TEAM FAVORITE", fav.toString())
                detailLigaViewModel.getListTeamFavorite(
                    idLeague,
                    shimmer,
                    teamListRecycler,
                    activity
                )
            } else {
                detailLigaViewModel.getListTeam(
                    idLeague,
                    shimmer,
                    teamListRecycler,
                    activity
                )
            }
            teamListRecycler.visibility = View.VISIBLE
        }, 1000)
    }

    private fun searchListTeam(strTeam: String) {
        val activity: Activity = activity!!
        val idLeague = activity.intent.getStringExtra("idLeague")

        teamListRecycler.visibility = View.GONE

        if (strTeam.isEmpty()) {
            getListTeamFromService()
            return
        }

        shimmer.visibility = View.VISIBLE

        Handler().postDelayed({
            detailLigaViewModel.searchListTeam(
                strTeam,
                idLeague,
                shimmer,
                teamListRecycler,
                activity
            )
            teamListRecycler.visibility = View.VISIBLE
        }, 1000)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teamListRecycler.layoutManager = GridLayoutManager(activity, 2)
        getListTeamFromService()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(strTeam: String): Boolean {
                searchListTeam(strTeam)
                return false
            }

            override fun onQueryTextSubmit(strTeam: String): Boolean {
                searchListTeam(strTeam)
                return false
            }
        })
    }
}
