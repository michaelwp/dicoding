package com.example.footballleague.layout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.example.footballleague.data.DataLeague
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*


class LeagueDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataLeague = intent.extras?.getParcelable("dataLeague") as DataLeague?

        verticalLayout {
            lparams(matchParent, matchParent)

            linearLayout {
                backgroundColor = Color.rgb(0, 166, 137)
            }.lparams(matchParent, dip(150)) {}

            verticalLayout {

                frameLayout {
                    lparams(matchParent, wrapContent)

                    val league_detail_image = imageView {
                        padding = dip(10)
                    }.lparams(dip(150), dip(150)) {}
                    dataLeague!!.image.let { Picasso.get().load(it).into(league_detail_image) }

                    val league_detail_name = textView {
                        textColor = Color.WHITE
                        textSize = 20f
                    }.lparams(matchParent, wrapContent) {
                        gravity = Gravity.CENTER_VERTICAL
                        leftMargin = dip(150)
                        padding = dip(10)
                    }
                    league_detail_name.text = dataLeague!!.name

                }

                scrollView {

                    val league_detail_desc = textView {
                    }.lparams(matchParent, wrapContent) {
                        padding = dip(16)
                    }
                    league_detail_desc.text = dataLeague!!.desc

                }.lparams(matchParent, matchParent) {}

            }.lparams(matchParent, wrapContent) {
                topMargin = dip(-100)
            }
        }

    }
}
