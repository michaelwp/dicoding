package com.example.footballleague.layout.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.adapter.AdapterListLeague
import com.example.footballleague.layout.activity.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.league_list_fragment.*
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
class LeagueListFragment : Fragment() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel
    private lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment

    private fun initData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                detailLigaViewModel.listDetailLiga.clear()
                detailLigaViewModel.getListLiga()
                for (i in detailLigaViewModel.listLiga) {

                    detailLigaViewModel.getDetailLiga(i.idLeague!!)
                    recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    recyclerView.adapter = AdapterListLeague((context as MainActivity), detailLigaViewModel.listDetailLiga)
                }
                shimmerListLeague.visibility = View.GONE
            } catch (e: IllegalStateException) {
                Log.e("ERROR_KEY", e.toString())
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.league_list_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottomSheetDialogFragment = ListEventFragment()
        detailLigaViewModel = ViewModelProviders.of(this).get(DetailLigaViewModel::class.java)
        initData()

        searchViewEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(event: String?): Boolean {
                if (bottomSheetDialogFragment.isAdded) {
                    return true
                }
                val b = Bundle()
                b.putString("key", event)
                bottomSheetDialogFragment.arguments = b
                bottomSheetDialogFragment.show(activity!!.supportFragmentManager, "")
                return false
            }
        })

    }


}
