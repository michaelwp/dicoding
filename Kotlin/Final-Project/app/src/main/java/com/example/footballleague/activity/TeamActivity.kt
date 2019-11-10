package com.example.footballleague.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.footballleague.R
import com.example.footballleague.fragment.ListTeamFragment
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : AppCompatActivity() {

    private fun init() {
        bottom_navigation_teams.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.teams -> {
                    supportFragmentManager.commit {
                        replace(R.id.content_frame_teams, ListTeamFragment())
                    }
                }
                R.id.teamfavorites -> {
                    supportFragmentManager.commit {
                        replace(R.id.content_frame_teams, ListTeamFragment(true))
                    }
                }
            }
            true
        }
        bottom_navigation_teams.selectedItemId = R.id.teams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        init()
    }
}
