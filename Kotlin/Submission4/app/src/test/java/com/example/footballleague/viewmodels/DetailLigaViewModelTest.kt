package com.example.footballleague.viewmodels

import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.activity.MainActivity
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.repository.RepositoryData
import com.example.footballleague.fragment.LeagueListFragment
import com.example.footballleague.fragment.ListEventFragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLigaViewModelTest {

    @Mock
    private lateinit var mainActivity: MainActivity

    @Mock
    private lateinit var detailLigaViewModel: DetailLigaViewModel

    @Mock
    private lateinit var listEventFragment: BottomSheetDialogFragment

    @Mock
    private lateinit var leagueListFragment: LeagueListFragment

    @Mock
    private lateinit var shimmerLeagueList: ShimmerFrameLayout

    @Mock
    private lateinit var recyclerView: RecyclerView

    @Mock
    private lateinit var repositoryData: RepositoryData

    @Mock
    private lateinit var detailLeagueEntity: Single<List<DetailLeagueEntity>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivity = MainActivity()
        listEventFragment = ListEventFragment()
        leagueListFragment = LeagueListFragment()
        repositoryData = RepositoryData(mainActivity)
    }

    @Test
    fun saveListLeagueToRoom() {
        doNothing().`when`(detailLigaViewModel)
            .saveListLeagueToRoom(mainActivity.supportFragmentManager)
        detailLigaViewModel.saveListLeagueToRoom(mainActivity.supportFragmentManager)
        verify(detailLigaViewModel).saveListLeagueToRoom(mainActivity.supportFragmentManager)
    }

    @Test
    fun findDetailLeagueFromRoom() {
        Mockito.`when`(detailLigaViewModel.findDetailLeagueFromRoom(ArgumentMatchers.anyString()))
            .thenReturn(detailLeagueEntity)
        detailLigaViewModel.findDetailLeagueFromRoom(ArgumentMatchers.anyString())
        verify(detailLigaViewModel).findDetailLeagueFromRoom(ArgumentMatchers.anyString())
    }

    @Test
    fun saveListEventToRoom() {
        doNothing().`when`(detailLigaViewModel).saveListEventToRoom(
            "Arsenal",
            mainActivity.supportFragmentManager,
            listEventFragment
        )

        detailLigaViewModel.saveListEventToRoom(
            "Arsenal",
            mainActivity.supportFragmentManager,
            listEventFragment
        )

        verify(detailLigaViewModel).saveListEventToRoom(
            "Arsenal",
            mainActivity.supportFragmentManager,
            listEventFragment
        )
    }

    @Test
    fun saveListEventSchedule() {
        doNothing().`when`(detailLigaViewModel).saveListEventSchedule(
            "4328",
            "next",
            mainActivity.supportFragmentManager
        )

        detailLigaViewModel.saveListEventSchedule(
            "4328",
            "next",
            mainActivity.supportFragmentManager
        )

        verify(detailLigaViewModel).saveListEventSchedule(
            "4328",
            "next",
            mainActivity.supportFragmentManager
        )
    }

    @Test
    fun updateFavoriteStatus() {
        doNothing().`when`(detailLigaViewModel).updateFavoriteStatus(true, "602502")
        detailLigaViewModel.updateFavoriteStatus(true, "602502")
        verify(detailLigaViewModel).updateFavoriteStatus(true, "602502")
    }
}