package com.example.footballleague.layout.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.adapter.AdapterListEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.event_list_fragment.*
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
class ListEventFragment : BottomSheetDialogFragment() {

    private lateinit var detailLigaViewModel: DetailLigaViewModel
    private lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.footballleague.R.layout.event_list_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailLigaViewModel = ViewModelProviders.of(activity!!).get(DetailLigaViewModel::class.java)
        bottomSheetDialogFragment = DataNotFoundFragment()

        GlobalScope.launch(Dispatchers.Main) {
            detailLigaViewModel.getLeagueEventSchedule(arguments!!.getString("key", ""), "search")
            if (detailLigaViewModel.listEvent.size == 0) {
                shimmer_list_event.visibility = View.GONE
                bottomSheetDialogFragment.show(activity!!.supportFragmentManager, "")
                this@ListEventFragment.dismiss()
                return@launch
            }
            list_event_recycler.recycledViewPool.clear()
            list_event_recycler.layoutManager = LinearLayoutManager(activity!!)
            list_event_recycler.adapter = AdapterListEvent(activity!!, detailLigaViewModel.listEvent)
            AdapterListEvent(activity!!, detailLigaViewModel.listEvent).notifyDataSetChanged()
            shimmer_list_event.visibility = View.GONE
        }
    }

}
