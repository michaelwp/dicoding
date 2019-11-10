package com.example.footballleague.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.footballleague.R
import com.example.footballleague.Utility
import com.example.footballleague.fragment.FavoritesEventFragment
import com.example.footballleague.fragment.LeagueDescriptionFragment
import com.example.footballleague.fragment.LeagueEventScheduleFragment
import com.example.footballleague.fragment.ListKlasemenFragment
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.league_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick


class LeagueDetailActivity : AppCompatActivity() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
    }
    var leagueDesc: String = ""
    lateinit var leagueId: String
    private var disposable = CompositeDisposable()
    private lateinit var listTab: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.league_detail)

        val idLeague = intent.getStringExtra("idLeague")
        disposable.add(
            detailLigaViewModel.findDetailLeagueFromRoom(idLeague!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.forEach {
                        leagueDesc = it.strDescriptionEN
                        leagueId = it.idLeague
                        league_detail_name.text = it.strLeague
                        Picasso.get().load(it.strBadge).into(league_detail_image)
                    }
                    Utility.replaceFragment(
                        this@LeagueDetailActivity,
                        R.id.league_detail_fragment_container,
                        LeagueDescriptionFragment(),
                        leagueDesc
                    )
                }, { error -> Log.e("ERROR_KEY", error.toString()) })
        )

        listTab = arrayOf(
            resources.getString(R.string.deskripsi),
            resources.getString(R.string.pastmatch),
            resources.getString(R.string.nextmatch),
            resources.getString(R.string.favoritesevent),
            resources.getString(R.string.klasemen)
        )

        for (title in listTab) {
            tab_league_detail.addTab(tab_league_detail.newTab().setText(title))
        }

        tab_league_detail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        Utility.replaceFragment(
                            this@LeagueDetailActivity,
                            R.id.league_detail_fragment_container,
                            LeagueDescriptionFragment(),
                            leagueDesc
                        )
                    }
                    1 -> {
                        Utility.replaceFragment(
                            this@LeagueDetailActivity,
                            R.id.league_detail_fragment_container,
                            LeagueEventScheduleFragment("past"),
                            leagueId
                        )
                    }
                    2 -> {
                        Utility.replaceFragment(
                            this@LeagueDetailActivity,
                            R.id.league_detail_fragment_container,
                            LeagueEventScheduleFragment("next"),
                            leagueId
                        )
                    }
                    3 -> {
                        Utility.replaceFragment(
                            this@LeagueDetailActivity,
                            R.id.league_detail_fragment_container,
                            FavoritesEventFragment("leagueDetailActivity")
                        )
                    }
                    4 -> {
                        Utility.replaceFragment(
                            this@LeagueDetailActivity,
                            R.id.league_detail_fragment_container,
                            ListKlasemenFragment(),
                            leagueId
                        )
                    }
                }
            }

        })

        team_navigation.onClick {
            startActivity(intentFor<TeamActivity>("idLeague" to leagueId))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }

}
