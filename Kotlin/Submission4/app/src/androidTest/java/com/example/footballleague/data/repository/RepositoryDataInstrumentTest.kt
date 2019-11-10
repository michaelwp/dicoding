package com.example.footballleague.data.repository

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.footballleague.data.model.RoomDao
import com.example.footballleague.data.model.RoomDb
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryDataInstrumentTest {

    private lateinit var roomDb: RoomDb
    private lateinit var roomDao: RoomDao

    private val idLeague: String = "4328"
    private val strLeague: String = "English Premier League"
    private val strDescriptionEN: String =
        "The Premier League (often referred to as the English Premier League (EPL) outside England)"
    private val strBadge: String =
        "https://www.thesportsdb.com/images/media/league/badge/i6o0kh1549879062.png"

    private val detailLeagueEntity = DetailLeagueEntity(
        idLeague,
        strLeague,
        strDescriptionEN,
        strBadge
    )

    private val idEvent = "602502"
    private val schedule = ""
    private val strEvent = "Arsenal vs Watford"
    private val strSport = "Soccer"
    private val strSeason = "1920"
    private val strHomeTeam = "Arsenal"
    private val strAwayTeam = "Watford"
    private val intHomeScore = "0"
    private val intAwayScore = "0"
    private val strHomeGoalDetails = ""
    private val strHomeRedCards = ""
    private val strHomeYellowCards = ""
    private val strHomeLineupGoalkeeper = ""
    private val strHomeLineupDefense = ""
    private val strHomeLineupMidfield = ""
    private val strHomeLineupForward = ""
    private val strHomeLineupSubstitutes = ""
    private val strHomeFormation = ""
    private val strAwayGoalDetails = ""
    private val strAwayRedCards = ""
    private val strAwayYellowCards = ""
    private val strAwayLineupGoalkeeper = ""
    private val strAwayLineupDefense = ""
    private val strAwayLineupMidfield = ""
    private val strAwayLineupForward = ""
    private val strAwayLineupSubstitutes = ""
    private val strAwayFormation = ""
    private val intHomeShots = ""
    private val intAwayShots = ""
    private val dateEvent = "17-May-2020"
    private val strTime = "14:00:00"
    private val idHomeTeam = "133604"
    private val idAwayTeam = "133624"
    private val strHomeTeamBadge =
        "https://www.thesportsdb.com/images/media/team/badge/a1af2i1557005128.png"
    private val strAwayTeamBadge =
        "https://www.thesportsdb.com/images/media/team/badge/rsuswy1448813519.png"
    private val favorite = false

    private val listEventEntity = ListEventEntity(
        idEvent, idLeague,
        schedule, strEvent,
        strSport, strHomeTeam,
        strAwayTeam, idHomeTeam,
        idAwayTeam, strLeague,
        strSeason, intHomeScore,
        intAwayScore, strHomeGoalDetails,
        strAwayGoalDetails, strHomeRedCards,
        strAwayRedCards, strHomeYellowCards,
        strAwayYellowCards, intHomeShots,
        intAwayShots, strHomeLineupGoalkeeper,
        strAwayLineupGoalkeeper, strHomeLineupDefense,
        strAwayLineupDefense, strHomeLineupMidfield,
        strAwayLineupMidfield, strHomeLineupForward,
        strAwayLineupForward, strHomeLineupSubstitutes,
        strAwayLineupSubstitutes, strHomeFormation,
        strAwayFormation, strTime,
        dateEvent, strHomeTeamBadge,
        strAwayTeamBadge, favorite
    )

    private val strEventKeyword = "Arsenal"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        roomDb = RoomDb.invoke(context)
        roomDao = roomDb.roomDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertDetailLeagueToRoom() {
        roomDao.insertDetailLeague(detailLeagueEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("ON_COMPLETE", "insertDetailLeagueToRoom")
            }
            .doOnError { Log.e("ON_ERROR", it.toString()) }
            .doOnComplete { Log.e("ON_COMPLETE", "insertDetailLeagueToRoom") }
            .subscribe()
    }

    @Test
    @Throws(Exception::class)
    fun findListEventById() {
        CompositeDisposable(
            roomDao.findListEventId(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        saveListEventToRoom()
                        return@subscribe
                    }
                    Log.e("EVENT_SEARCH", it.first().strEvent)
                    assertThat(it.first().strEvent, equalTo(strEvent))
                }, { err -> Log.e("ON_ERROR", err.toString()) })
        )
    }

    @Test
    @Throws(Exception::class)
    fun saveListEventToRoom() {
        roomDao.insertListEvent(listEventEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError { error -> Log.e("ON_ERROR", error.toString()) }
            .doOnComplete { Log.e("ON_COMPLETE", "saveListEventToRoom") }
            .subscribe()
        Thread.sleep(5000)
        findListEventById()
    }

    @Test
    @Throws(Exception::class)
    fun updateFavorite() {
        roomDao.updateFavorite(true, idEvent)
            .doFinally {
                println("${idEvent} : Add to favorite successfully")
            }
            .doOnError { err -> Log.e("ON_ERROR", err.toString()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}