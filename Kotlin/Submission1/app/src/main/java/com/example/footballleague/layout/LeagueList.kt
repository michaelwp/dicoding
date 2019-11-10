package com.example.footballleague.layout

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.footballleague.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LeagueList : AnkoComponent<ViewGroup> {


    companion object {
        val league_name = 1
        val league_image = 2
        val league_list = 3
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            id = league_list
            orientation = LinearLayout.VERTICAL
            lparams(wrapContent, wrapContent)
            padding = dip(16)
            isClickable = true

            linearLayout {
                orientation = LinearLayout.VERTICAL
                lparams(dip(150), wrapContent)

                imageView {
                    id = league_image
                    imageResource = R.drawable.english_premier_league
                }.lparams(dip(50), dip(50)) {
                    gravity = Gravity.CENTER
                }

                textView {
                    id = league_name
                    text = "Coba FC"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams(matchParent, wrapContent) {
                    margin = dip(10)
                }
            }
        }

    }
}