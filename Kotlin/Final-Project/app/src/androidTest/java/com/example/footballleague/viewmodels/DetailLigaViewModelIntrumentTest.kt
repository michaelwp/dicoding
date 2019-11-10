package com.example.footballleague.viewmodels

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.footballleague.data.room.RoomDao
import com.example.footballleague.data.room.RoomDb
import com.example.footballleague.data.room.entity.ListEventEntity
import com.example.footballleague.repository.RepositoryData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailLigaViewModelIntrumentTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var roomDb: RoomDb
    private lateinit var roomDao: RoomDao
    private val strLeague: String = "English Premier League"
    private val strEvent = "Arsenal vs Watford"
    private val idLeague: String = "4328"
    private val strTeam = "Arsenal"

    private val repositoryData: RepositoryData by lazy {
        RepositoryData(context)
    }

    @Before
    fun setUp() {
        roomDb = RoomDb.invoke(context)
        roomDao = roomDb.roomDao()
    }

    @Test
    @Throws(Exception::class)
    fun findLeague() {
        CompositeDisposable().add(
            repositoryData.getDetailLeagueFromRoom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        Log.e("ON_EMPTY", "Data is empty")
                        return@subscribe
                    }
                    val strLeaguefind = it.filter { it.strLeague == strLeague }
                        .first()
                    Log.e("ON_RESULT", strLeaguefind.strLeague)
                    Assert.assertThat(strLeaguefind.strLeague, Matchers.equalTo(strLeague))
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    @Test
    @Throws(Exception::class)
    fun findEvent() {
        CompositeDisposable().add(
            repositoryData.findListEventFromRoom(strEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        Log.e("ON_EMPTY", "Event Not Found")
                        return@subscribe
                    }
                    val strEventFilter = it.filter { it.strEvent == strEvent }
                    Log.e("EVENT_SEARCH", strEventFilter.first().strEvent)
                    Assert.assertThat(strEventFilter.first().strEvent, Matchers.equalTo(strEvent))
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    @Test
    @Throws(Exception::class)
    fun findSchedule() {
        CompositeDisposable().add(
            repositoryData.findListEventSchedule(idLeague, "next")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val strEventFilter = it.filter { it.strEvent == strEvent }
                    Log.e("EVENT_SEARCH", strEventFilter.first().strEvent)
                    Assert.assertThat(strEventFilter.first().strEvent, Matchers.equalTo(strEvent))
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    @Test
    @Throws(Exception::class)
    fun getListFavEvent() {
        lateinit var repo: Flowable<List<ListEventEntity>>

        repo = if (idLeague.isEmpty()) {
            repositoryData.getListFavouriteEventAll()
        } else {
            repositoryData.getListFavouriteEventByLeague(idLeague)
        }

        CompositeDisposable().add(
            repo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val strEventFilter = it.filter { it.strEvent == strEvent }
                    Log.e("EVENT_SEARCH", strEventFilter.first().strEvent)
                    Assert.assertThat(strEventFilter.first().strEvent, Matchers.equalTo(strEvent))
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    @Test
    @Throws(Exception::class)
    fun getListTeam() {
        CompositeDisposable().add(
            repositoryData.getListTeamFromRoom(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val strTeamFilter = it.filter { it.strTeam == strTeam }
                    Log.e("TEAM_SEARCH", strTeamFilter.first().strTeam)
                    Assert.assertThat(strTeamFilter.first().strTeam, Matchers.equalTo(strTeam))
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

    @Test
    @Throws(Exception::class)
    fun getListTeamFavorite() {
        CompositeDisposable().add(
            repositoryData.getListTeamFromRoom(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val strTeamFavoriteFilter = it.filter { it.favorite == true }
                    Log.e("TEAM_SEARCH", strTeamFavoriteFilter.first().strTeam)
                    Assert.assertThat(
                        strTeamFavoriteFilter.first().strTeam,
                        Matchers.equalTo(strTeam)
                    )
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }
}