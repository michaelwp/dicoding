package com.example.footballleague.fragment


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.viewmodels.DetailLigaViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ListEventFragment : BottomSheetDialogFragment() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DetailLigaViewModel::class.java)
    }
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var listEventRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.event_list_fragment,
            container,
            false
        )

        shimmer = view.findViewById(R.id.shimmer_list_event)
        listEventRecycler = view.findViewById(R.id.list_event_recycler)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listEventRecycler.layoutManager = LinearLayoutManager(activity!!)
        getListEvenFromService()
    }

    private fun getListEvenFromService() {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val activity: Activity = activity!!

        shimmer.visibility = View.VISIBLE
        detailLigaViewModel.saveListEventToRoom(
            arguments!!.getString("key", ""),
            fragmentManager,
            this
        )

        Handler().postDelayed({
            detailLigaViewModel.findEvent(
                arguments!!.getString("key", ""),
                shimmer,
                this,
                fragmentManager,
                listEventRecycler,
                activity
            )
        }, 5000)
    }
}
