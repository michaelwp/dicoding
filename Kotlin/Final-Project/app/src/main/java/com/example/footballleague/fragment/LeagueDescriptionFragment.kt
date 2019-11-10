package com.example.footballleague.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import kotlinx.android.synthetic.main.league_description_fragment.*

class LeagueDescriptionFragment : Fragment() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.league_description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        league_description_text.text = arguments?.getString("data")
    }

}
