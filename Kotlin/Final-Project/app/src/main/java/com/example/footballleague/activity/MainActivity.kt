package com.example.footballleague.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.footballleague.R
import com.example.footballleague.fragment.FavoritesEventFragment
import com.example.footballleague.fragment.LeagueListFragment
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    private fun init() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.leagues -> {
                    supportFragmentManager.commit {
                        replace(R.id.content_frame, LeagueListFragment())
                    }
                }
                R.id.favorites -> {
                    supportFragmentManager.commit {
                        replace(R.id.content_frame, FavoritesEventFragment("mainActivity"))
                    }
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.leagues
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        recordScreenView()

        init()
    }

    private fun recordScreenView() {
        // This string must be <= 36 characters long in order for setCurrentScreen to succeed.
        val screenName = "main_activity"

        // [START set_current_screen]
        mFirebaseAnalytics!!.setCurrentScreen(this, screenName, null /* class override */)
        // [END set_current_screen]
    }

    override fun onResume() {
        super.onResume()

        recordScreenView()
    }
}
