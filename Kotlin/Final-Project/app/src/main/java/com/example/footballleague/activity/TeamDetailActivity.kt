package com.example.footballleague.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
    }

    val idTeam: String by lazy {
        intent.getStringExtra("idTeam")
    }

    private lateinit var listTab: Array<String>

    private fun getDataTeam() {
        detailLigaViewModel.saveDetailTeamToRoom(idTeam)
        detailLigaViewModel.getDetailTeam(
            idTeam,
            teamTitle,
            teamBadge
        )
    }

    private fun setTabItem() {
        listTab = arrayOf(
            resources.getString(R.string.deskripsi),
            "Pemain"
        )

        listTab.forEach { title ->
            tab_team_detail.addTab(tab_team_detail.newTab().setText(title))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        getDataTeam()
        setTabItem()

    }

}
