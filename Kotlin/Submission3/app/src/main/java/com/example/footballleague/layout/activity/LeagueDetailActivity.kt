package com.example.footballleague.layout.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.layout.fragment.FavoritesEventFragment
import com.example.footballleague.layout.fragment.LeagueDescriptionFragment
import com.example.footballleague.layout.fragment.LeagueEventScheduleFragment
import com.example.footballleague.util.ReplaceFragment
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.league_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LeagueDetailActivity : AppCompatActivity() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel
    lateinit var leagueDesc: String
    lateinit var leagueId: String

    private fun setLigaDetail() {
        val detailListLiga = detailLigaViewModel.listDetailLiga[0]
        leagueDesc = detailListLiga.strDescriptionEN.toString()
        leagueId = detailListLiga.idLeague.toString()
        league_detail_name.text = detailListLiga.strLeague
        Picasso.get().load(detailListLiga.strBadge).into(league_detail_image)
        ReplaceFragment().replaceFragment(
                this,
                R.id.league_detail_fragment_container,
                LeagueDescriptionFragment(),
                leagueDesc
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.league_detail)

        detailLigaViewModel = ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            val idLeague = intent.getStringExtra("idLeague")
            detailLigaViewModel.getDetailLiga(idLeague!!)
            setLigaDetail()
        }

        tab_league_detail.addTab(tab_league_detail.newTab().setText("Description"))
        tab_league_detail.addTab(tab_league_detail.newTab().setText("Past Match"))
        tab_league_detail.addTab(tab_league_detail.newTab().setText("Next Match"))
        tab_league_detail.addTab(tab_league_detail.newTab().setText("Favorites Event"))

        tab_league_detail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        ReplaceFragment().replaceFragment(
                                this@LeagueDetailActivity,
                                R.id.league_detail_fragment_container,
                                LeagueDescriptionFragment(),
                                leagueDesc
                        )
                    }
                    1 -> {
                        ReplaceFragment().replaceFragment(
                                this@LeagueDetailActivity,
                                R.id.league_detail_fragment_container,
                                LeagueEventScheduleFragment("past"),
                                leagueId
                        )
                    }
                    2 -> {
                        ReplaceFragment().replaceFragment(
                                this@LeagueDetailActivity,
                                R.id.league_detail_fragment_container,
                                LeagueEventScheduleFragment("next"),
                                leagueId
                        )
                    }
                    3 -> {
                        ReplaceFragment().replaceFragment(
                                this@LeagueDetailActivity,
                                R.id.league_detail_fragment_container,
                                FavoritesEventFragment("leagueDetailActivity")
                        )
                    }
                }
            }

        })
    }

}
