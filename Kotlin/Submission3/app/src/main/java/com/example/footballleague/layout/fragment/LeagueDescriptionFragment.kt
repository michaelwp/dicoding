package com.example.footballleague.layout.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.layout.activity.LeagueDetailActivity
import kotlinx.android.synthetic.main.league_description_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeagueDescriptionFragment : Fragment() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.league_description_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        league_description_text.text = arguments!!.getString("data")//(activity as LeagueDetailActivity).leagueDesc
    }

}
