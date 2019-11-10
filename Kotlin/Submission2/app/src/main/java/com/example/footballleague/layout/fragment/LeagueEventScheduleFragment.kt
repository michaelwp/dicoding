package com.example.footballleague.layout.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.adapter.AdapterListEvent
import com.example.footballleague.layout.activity.LeagueDetailActivity
import com.example.footballleague.util.ReplaceFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.event_list_fragment.*
import kotlinx.android.synthetic.main.league_detail.*
import kotlinx.android.synthetic.main.league_event_schedule_fragment.*
import kotlinx.android.synthetic.main.league_event_schedule_fragment.recycler_view_next_match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeagueEventScheduleFragment(schedule: String) : Fragment() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel
    private val eSchedule = schedule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.league_event_schedule_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailLigaViewModel = ViewModelProviders.of(activity!!).get(DetailLigaViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            detailLigaViewModel.getLeagueEventSchedule(arguments!!.getString("data")!!, eSchedule)
            if (detailLigaViewModel.listEvent.size == 0) {
                shimmer_list_next_event.visibility = View.GONE
                ReplaceFragment().replaceFragment(activity!!, R.id.league_detail_fragment_container, DataNotFoundFragment())
                return@launch
            }
            recycler_view_next_match.recycledViewPool.clear()
            recycler_view_next_match.layoutManager = LinearLayoutManager(activity)
            recycler_view_next_match.adapter = AdapterListEvent(activity!!, detailLigaViewModel.listEvent)
            AdapterListEvent(activity!!, detailLigaViewModel.listEvent).notifyDataSetChanged()
            shimmer_list_next_event.visibility = View.GONE
        }
    }
}
