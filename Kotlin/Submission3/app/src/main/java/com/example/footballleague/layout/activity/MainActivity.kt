package com.example.footballleague.layout.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.footballleague.R
import com.example.footballleague.layout.fragment.FavoritesEventFragment
import com.example.footballleague.layout.fragment.LeagueListFragment
import com.example.footballleague.util.ReplaceFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.leagues -> {
                    ReplaceFragment().replaceFragment(this, R.id.content_frame, LeagueListFragment())
                }
                R.id.favorites -> {
                    ReplaceFragment().replaceFragment(this, R.id.content_frame, FavoritesEventFragment("mainActivity"))
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.leagues
    }
}
