package com.example.footballleague.layout.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.footballleague.R
import com.example.footballleague.ViewModels.DetailLigaViewModel
import com.example.footballleague.adapter.AdapterLeagueList
import com.example.footballleague.data.pojo.dataliga.League
import com.example.footballleague.layout.fragment.ListEventFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var items: MutableList<League> = mutableListOf()
    private lateinit var detailLigaViewModel: DetailLigaViewModel
    private lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment

    private fun initData() {
        GlobalScope.launch(Dispatchers.Main) {
            detailLigaViewModel.listDetailLiga.clear()
            detailLigaViewModel.getListLiga()
            for (i in detailLigaViewModel.listLiga) {
                detailLigaViewModel.getDetailLiga(i.idLeague!!)
                recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
                recyclerView.adapter = AdapterLeagueList(this@MainActivity, detailLigaViewModel.listDetailLiga)
            }
            shimmerListLeague.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                bottomSheetDialogFragment.setArguments(b)
                bottomSheetDialogFragment.show(supportFragmentManager, "")
                return false
            }
        })

    }
}
