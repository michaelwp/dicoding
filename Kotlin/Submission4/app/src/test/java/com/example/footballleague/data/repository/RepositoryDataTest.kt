package com.example.footballleague.data.repository

import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
import com.example.footballleague.data.remotedatasource.DetailLeagueService
import com.example.footballleague.data.remotedatasource.pojo.dataleague.DataListLeague
import com.example.footballleague.data.remotedatasource.pojo.match.Match
import com.example.footballleague.data.remotedatasource.pojo.team.DataTeam
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepositoryDataTest {

    private val strLeague: String = "English Premier League"
    private val idLeague: String = "4328"
    private val strEvent = "Arsenal vs Watford"

    @Mock
    private lateinit var detailLeagueService: DetailLeagueService

    @Mock
    private lateinit var dataListLeague: Observable<DataListLeague>

    @Mock
    private lateinit var match: Observable<Match>

    @Mock
    private lateinit var dataTeam: DataTeam

    @Mock
    private lateinit var repositoryData: RepositoryData

    @Mock
    private lateinit var detailLeagueEntity: Flowable<List<DetailLeagueEntity>>

    @Mock
    private lateinit var listEventEntity: Maybe<List<ListEventEntity>>

    @Mock
    private lateinit var listEventEntityFlow: Flowable<List<ListEventEntity>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailLeagueService = Mockito.spy(DetailLeagueService.dataRepositoryServices())
    }


    @Test
    fun getListLeagueFromService() {
        Mockito.`when`(detailLeagueService.getListLeague()).thenReturn(dataListLeague)
        detailLeagueService.getListLeague()
        verify(detailLeagueService).getListLeague()
    }

    @Test
    fun getListEventFromService() {
        Mockito.`when`(detailLeagueService.getSearchEvent(strEvent))
            .thenReturn(match)
        detailLeagueService.getSearchEvent(strEvent)
        verify(detailLeagueService).getSearchEvent(strEvent)
    }

    @Test
    fun getDataTeamFromService() {
        GlobalScope.launch {
            Mockito.`when`(detailLeagueService.getDataTeam(strLeague))
                .thenReturn(dataTeam)
            detailLeagueService.getDataTeam(strLeague)
            verify(detailLeagueService).getDataTeam(strLeague)
        }
    }

    @Test
    fun getDataScheduleEventFromService() {
        Mockito.`when`(detailLeagueService.getPastEvent(idLeague))
            .thenReturn(match)
        detailLeagueService.getPastEvent(idLeague)
        verify(detailLeagueService).getPastEvent(idLeague)

        Mockito.`when`(detailLeagueService.getNextEvent(idLeague))
            .thenReturn(match)
        detailLeagueService.getNextEvent(idLeague)
        verify(detailLeagueService).getNextEvent(idLeague)
    }

    @Test
    fun getDetailLeagueFromRoom() {
        Mockito.`when`(repositoryData.getDetailLeagueFromRoom()).thenReturn(detailLeagueEntity)
        repositoryData.getDetailLeagueFromRoom()
        verify(repositoryData).getDetailLeagueFromRoom()
    }

    @Test
    fun findListEventFromRoom() {
        Mockito.`when`(repositoryData.findListEventFromRoom(strEvent))
            .thenReturn(listEventEntity)
        repositoryData.findListEventFromRoom(strEvent)
        verify(repositoryData).findListEventFromRoom(strEvent)
    }

    @Test
    fun findListEventSchedule() {
        Mockito.`when`(
            repositoryData.findListEventSchedule(
                idLeague,
                "next"
            )
        ).thenReturn(listEventEntityFlow)
        repositoryData.findListEventSchedule(
            idLeague,
            "past"
        )
        verify(repositoryData).findListEventSchedule(
            idLeague,
            "past"
        )
    }

    @Test
    fun getListFavouriteEventAll() {
        Mockito.`when`(repositoryData.getListFavouriteEventAll()).thenReturn(listEventEntityFlow)
        repositoryData.getListFavouriteEventAll()
        verify(repositoryData).getListFavouriteEventAll()
    }

    @Test
    fun getListFavouriteEventByLeague() {
        Mockito.`when`(repositoryData.getListFavouriteEventByLeague(ArgumentMatchers.anyString()))
            .thenReturn(listEventEntityFlow)
        repositoryData.getListFavouriteEventByLeague(idLeague)
        verify(repositoryData).getListFavouriteEventByLeague(idLeague)
    }
}