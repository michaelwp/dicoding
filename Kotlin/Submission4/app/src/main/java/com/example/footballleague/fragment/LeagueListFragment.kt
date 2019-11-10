package com.example.footballleague.fragment


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.league_list_fragment.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeagueListFragment : Fragment() {
    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
    }

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    private val bottomSheetDialogFragment: BottomSheetDialogFragment = ListEventFragment()
    lateinit var shimmer: ShimmerFrameLayout
    private lateinit var leagueListRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.league_list_fragment, container, false)
        shimmer = view.findViewById(R.id.shimmerListLeague)
        leagueListRecycler = view.findViewById(R.id.league_list_recycler)
        return view
    }

    private fun getListLeagueFromService() {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val activity: Activity = activity!!

        shimmer.visibility = View.VISIBLE
        detailLigaViewModel.saveListLeagueToRoom(fragmentManager)

        Handler().postDelayed({
            detailLigaViewModel.findLeague(
                shimmer,
                leagueListRecycler,
                activity
            )
        }, 1000)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity!!)
        recordScreenView()

        leagueListRecycler.layoutManager = GridLayoutManager(activity, 2)
        getListLeagueFromService()

        searchViewEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(event: String?): Boolean {
                if (!bottomSheetDialogFragment.isAdded) {
                    val b = Bundle()
                    b.putString("key", event)
                    bottomSheetDialogFragment.arguments = b
                    bottomSheetDialogFragment.show(activity!!.supportFragmentManager, "")
                }
                return false
            }
        })
    }

    private fun recordScreenView() {
        // This string must be <= 36 characters long in order for setCurrentScreen to succeed.
        val screenName = "main_activity"

        // [START set_current_screen]
        mFirebaseAnalytics!!.setCurrentScreen(activity!!, screenName, null /* class override */)
        // [END set_current_screen]
    }
}
