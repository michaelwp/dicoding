package com.example.footballleague.fragment


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.league_event_schedule_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeagueEventScheduleFragment(schedule: String) : Fragment() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DetailLigaViewModel::class.java)
    }
    private val eSchedule = schedule
    lateinit var shimmer: ShimmerFrameLayout
    private lateinit var listMatchRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.league_event_schedule_fragment, container, false)

        shimmer = view.findViewById(R.id.shimmer_list_next_event)
        listMatchRecycler = view.findViewById(R.id.recycler_view_next_match)

        return view
    }

    private fun findListEventSchedule() {
        val idLeague = arguments!!.getString("data")!!
        val fragmentManager = activity!!.supportFragmentManager
        val activity: Activity = activity!!

        shimmer.visibility = View.VISIBLE
        detailLigaViewModel.saveListEventSchedule(
            idLeague,
            eSchedule,
            fragmentManager
        )

        Handler().postDelayed({
            detailLigaViewModel.findSchedule(
                idLeague,
                eSchedule,
                shimmer,
                listMatchRecycler,
                activity
            )
        }, 3000)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_next_match.layoutManager = LinearLayoutManager(activity)
        findListEventSchedule()
    }
}
