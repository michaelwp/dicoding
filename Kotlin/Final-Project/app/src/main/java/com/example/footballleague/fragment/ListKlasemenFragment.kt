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

/**
 * A simple [Fragment] subclass.
 */
class ListKlasemenFragment : Fragment() {

    private val detailLigaViewModel: DetailLigaViewModel by lazy {
        ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
    }

    private lateinit var klasemenListRecycler: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_klasemen, container, false)
        shimmer = view.findViewById(R.id.shimmer_list_klasemen)
        klasemenListRecycler = view.findViewById(R.id.klasemen_list_recycler)
        return view
    }

    private fun getListKlasemenFromService() {
        val idLeague = arguments!!.getString("data")!!
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val activity: Activity = activity!!

        shimmer.visibility = View.VISIBLE
        detailLigaViewModel.saveListKlasemenToRoom(idLeague, fragmentManager)

        Handler().postDelayed({
            detailLigaViewModel.getListKlasemen(
                idLeague,
                shimmer,
                klasemenListRecycler,
                activity,
                fragmentManager
            )
        }, 1000)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        klasemenListRecycler.layoutManager = LinearLayoutManager(activity)
        getListKlasemenFromService()
    }

}
