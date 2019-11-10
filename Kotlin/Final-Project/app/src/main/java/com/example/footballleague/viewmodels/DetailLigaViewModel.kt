package com.example.footballleague.viewmodels

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.Utility
import com.example.footballleague.adapter.AdapterListEvent
import com.example.footballleague.adapter.AdapterListKlasemen
import com.example.footballleague.adapter.AdapterListLeague
import com.example.footballleague.adapter.AdapterListTeam
import com.example.footballleague.data.api.pojo.dataleague.League
import com.example.footballleague.data.api.pojo.klasemen.Table
import com.example.footballleague.data.api.pojo.match.Event
import com.example.footballleague.data.api.pojo.team.Team
import com.example.footballleague.data.room.RoomDb
import com.example.footballleague.data.room.entity.DetailLeagueEntity
import com.example.footballleague.data.room.entity.ListEventEntity
import com.example.footballleague.repository.RepositoryData
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DetailLigaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = RoomDb.invoke(application)
    private var listEvent: MutableList<Event> = mutableListOf()
    private var listDetailLeague: MutableList<League> = mutableListOf()
    private var listTeam: MutableList<Team> = mutableListOf()
    private var listKlasemen: MutableList<Table> = mutableListOf()

    private var disposable = CompositeDisposable()

    private val repositoryData: RepositoryData by lazy {
        RepositoryData(application)
    }

    private fun clearDisposable() {
        disposable.clear()
    }

    fun saveListLeagueToRoom(fragmentManager: FragmentManager) {
        repositoryData.getListLeagueFromService(fragmentManager)
    }

    fun findDetailLeagueFromRoom(idLeague: String): Single<List<DetailLeagueEntity>> {
        return db.roomDao().findDetailLeague(idLeague)
    }

    fun saveListEventToRoom(
        strEvent: String,
        fragmentManager: FragmentManager,
        bottomSheetDialogFragment: BottomSheetDialogFragment
    ) {
        repositoryData.getListEventFromService(strEvent, fragmentManager, bottomSheetDialogFragment)
    }

    fun findLeague(
        shimmer: ShimmerFrameLayout,
        recyclerView: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.getDetailLeagueFromRoom()
                .doOnSubscribe { shimmer.visibility = View.VISIBLE }
                .doFinally {
                    Log.e("ON_COMPLETE", "findLeague")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        Log.e("ON_EMPTY", "Data is empty")
                        return@subscribe
                    }
                    listDetailLeague.clear()
                    it.forEach {
                        listDetailLeague.add(
                            League(
                                it.idLeague,
                                it.strLeague,
                                it.strDescriptionEN,
                                it.strBadge
                            )
                        )
                    }
                    try {
                        recyclerView.adapter =
                            AdapterListLeague(activity, listDetailLeague)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    fun findEvent(
        event: String,
        shimmer: ShimmerFrameLayout,
        bottomSheetDialogFragment: BottomSheetDialogFragment,
        fragmentManager: FragmentManager,
        recyclerView: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.findListEventFromRoom(event)
                .doOnSubscribe { shimmer.visibility = View.VISIBLE }
                .doFinally {
                    try {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.e("ON_COMPLETE", "findEvent")
                            shimmer.visibility = View.GONE
                        }
                    } catch (e: UndeliverableException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listEvent.clear()
                    if (it.isEmpty()) {
                        try {
                            bottomSheetDialogFragment.dismiss()
                            Utility.errMessage(
                                fragmentManager,
                                "NullPointerException"
                            )
                        } catch (e: Exception) {
                            Log.e("ON_ERROR", e.toString())
                        }
                    }
                    it.forEach {
                        listEvent.add(
                            Event(
                                it.idEvent,
                                it.idLeague,
                                it.schedule,
                                it.strEvent,
                                it.strSport,
                                it.strHomeTeam,
                                it.strAwayTeam,
                                it.idHomeTeam,
                                it.idAwayTeam,
                                it.strLeague,
                                it.strSeason,
                                it.intHomeScore,
                                it.intAwayScore,
                                it.strHomeGoalDetails,
                                it.strAwayGoalDetails,
                                it.strHomeRedCards,
                                it.strAwayRedCards,
                                it.strHomeYellowCards,
                                it.strAwayYellowCards,
                                it.intHomeShots,
                                it.intAwayShots,
                                it.strHomeLineupGoalkeeper,
                                it.strAwayLineupGoalkeeper,
                                it.strHomeLineupDefense,
                                it.strAwayLineupDefense,
                                it.strHomeLineupMidfield,
                                it.strAwayLineupMidfield,
                                it.strHomeLineupForward,
                                it.strAwayLineupForward,
                                it.strHomeLineupSubstitutes,
                                it.strAwayLineupSubstitutes,
                                it.strHomeFormation,
                                it.strAwayFormation,
                                it.strTime,
                                it.dateEvent,
                                it.strHomeTeamBadge,
                                it.strAwayTeamBadge,
                                it.favorite
                            )
                        )
                    }

                    try {
                        recyclerView.adapter = AdapterListEvent(activity, listEvent)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }


    fun saveListEventSchedule(
        idLeague: String,
        schedule: String,
        fragmentManager: FragmentManager
    ) {
        repositoryData.getDataScheduleEventFromService(idLeague, schedule, fragmentManager)
    }

    fun findSchedule(
        idLeague: String,
        eSchedule: String,
        shimmer: ShimmerFrameLayout,
        listMatchRecycler: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.findListEventSchedule(idLeague, eSchedule)
                .doOnSubscribe {
                    shimmer.visibility = View.VISIBLE
                }
                .doFinally {
                    Log.e("ON_COMPLETE", "findSchedule")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listEvent.clear()
                    it.forEach {
                        listEvent.add(
                            Event(
                                it.idEvent,
                                it.idLeague,
                                it.schedule,
                                it.strEvent,
                                it.strSport,
                                it.strHomeTeam,
                                it.strAwayTeam,
                                it.idHomeTeam,
                                it.idAwayTeam,
                                it.strLeague,
                                it.strSeason,
                                it.intHomeScore,
                                it.intAwayScore,
                                it.strHomeGoalDetails,
                                it.strAwayGoalDetails,
                                it.strHomeRedCards,
                                it.strAwayRedCards,
                                it.strHomeYellowCards,
                                it.strAwayYellowCards,
                                it.intHomeShots,
                                it.intAwayShots,
                                it.strHomeLineupGoalkeeper,
                                it.strAwayLineupGoalkeeper,
                                it.strHomeLineupDefense,
                                it.strAwayLineupDefense,
                                it.strHomeLineupMidfield,
                                it.strAwayLineupMidfield,
                                it.strHomeLineupForward,
                                it.strAwayLineupForward,
                                it.strHomeLineupSubstitutes,
                                it.strAwayLineupSubstitutes,
                                it.strHomeFormation,
                                it.strAwayFormation,
                                it.strTime,
                                it.dateEvent,
                                it.strHomeTeamBadge,
                                it.strAwayTeamBadge,
                                it.favorite
                            )
                        )
                    }
                    try {
                        listMatchRecycler.adapter = AdapterListEvent(activity, listEvent)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    fun updateFavoriteStatus(fav: Boolean, idEvent: String) {
        repositoryData.updateFavorite(fav, idEvent)
    }

    fun getListFavEvent(
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        listFavRecycler: RecyclerView,
        activity: Activity
    ) {
        lateinit var repo: Flowable<List<ListEventEntity>>

        repo = if (idLeague.isEmpty()) {
            repositoryData.getListFavouriteEventAll()
        } else {
            repositoryData.getListFavouriteEventByLeague(idLeague)
        }

        clearDisposable()
        disposable.add(
            repo
                .doOnSubscribe { shimmer.visibility = View.VISIBLE }
                .doFinally {
                    Log.e("ON_COMPLETE", "getListFavEvent")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listEvent.clear()
                    it.forEach {
                        listEvent.add(
                            Event(
                                it.idEvent,
                                it.idLeague,
                                it.schedule,
                                it.strEvent,
                                it.strSport,
                                it.strHomeTeam,
                                it.strAwayTeam,
                                it.idHomeTeam,
                                it.idAwayTeam,
                                it.strLeague,
                                it.strSeason,
                                it.intHomeScore,
                                it.intAwayScore,
                                it.strHomeGoalDetails,
                                it.strAwayGoalDetails,
                                it.strHomeRedCards,
                                it.strAwayRedCards,
                                it.strHomeYellowCards,
                                it.strAwayYellowCards,
                                it.intHomeShots,
                                it.intAwayShots,
                                it.strHomeLineupGoalkeeper,
                                it.strAwayLineupGoalkeeper,
                                it.strHomeLineupDefense,
                                it.strAwayLineupDefense,
                                it.strHomeLineupMidfield,
                                it.strAwayLineupMidfield,
                                it.strHomeLineupForward,
                                it.strAwayLineupForward,
                                it.strHomeLineupSubstitutes,
                                it.strAwayLineupSubstitutes,
                                it.strHomeFormation,
                                it.strAwayFormation,
                                it.strTime,
                                it.dateEvent,
                                it.strHomeTeamBadge,
                                it.strAwayTeamBadge,
                                it.favorite
                            )
                        )
                    }

                    try {
                        listFavRecycler.adapter = AdapterListEvent(activity, listEvent)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                })
        )
    }

    fun saveListTeamToRoom(idLeague: String, fragmentManager: FragmentManager) {
        repositoryData.getListTeamFromService(idLeague, fragmentManager)
    }

    fun getListTeam(
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        recyclerView: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.getListTeamFromRoom(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        Log.e("ON_EMPTY", "Data is empty")
                        shimmer.visibility = View.GONE
                        return@subscribe
                    }
                    listTeam.clear()
                    it.forEach {
                        listTeam.add(
                            Team(
                                it.idTeam, it.strTeam,
                                it.strLeague, it.idLeague,
                                it.strManager, it.strTeamBadge,
                                it.strDescriptionEN, it.favorite
                            )
                        )
                    }

                    try {
                        recyclerView.adapter =
                            AdapterListTeam(activity, listTeam)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

    fun updateFavoriteTeamStatus(fav: Boolean, idTeam: String) {
        repositoryData.updateFavoriteTeamInRoom(fav, idTeam)
    }

    fun getListTeamFavorite(
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        recyclerView: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.getListTeamFromRoom(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        Log.e("ON_EMPTY", "Data is empty")
                        shimmer.visibility = View.GONE
                        return@subscribe
                    }
                    listTeam.clear()
                    it.filter { it.favorite }
                        .forEach {
                            listTeam.add(
                                Team(
                                    it.idTeam, it.strTeam,
                                    it.strLeague, it.idLeague,
                                    it.strManager, it.strTeamBadge,
                                    it.strDescriptionEN, it.favorite
                                )
                            )
                        }

                    try {
                        recyclerView.adapter =
                            AdapterListTeam(activity, listTeam)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

    fun searchAndSaveListTeamToRoom(
        idLeague: String,
        fragmentManager: FragmentManager,
        strTeam: String
    ) {
        repositoryData.searchListTeamFromService(idLeague, fragmentManager, strTeam)
    }

    fun searchListTeam(
        strTeam: String,
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        recyclerView: RecyclerView,
        activity: Activity
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.searchListTeamFromRoom(strTeam, idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listTeam.clear()
                    if (it.isEmpty()) {
                        Log.e("ON_EMPTY", "Data is empty")
                        shimmer.visibility = View.GONE
                    } else {
                        it.forEach {
                            listTeam.add(
                                Team(
                                    it.idTeam, it.strTeam,
                                    it.strLeague, it.idLeague,
                                    it.strManager, it.strTeamBadge,
                                    it.strDescriptionEN, it.favorite
                                )
                            )
                        }
                    }

                    try {
                        recyclerView.adapter =
                            AdapterListTeam(activity, listTeam)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

    fun saveListKlasemenToRoom(idLeague: String, fragmentManager: FragmentManager) {
        repositoryData.getListKlasemenFromService(idLeague, fragmentManager)
    }

    fun getListKlasemen(
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        recyclerView: RecyclerView,
        activity: Activity,
        fragmentManager: FragmentManager
    ) {
        clearDisposable()
        disposable.add(
            repositoryData.getListKlasemenFromRoom(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        Utility.errMessage(
                            fragmentManager,
                            "NullPointerException"
                        )
                        Log.e("ON_EMPTY", "Data is empty")
                        shimmer.visibility = View.GONE
                        return@subscribe
                    }
                    listTeam.clear()
                    it.forEach {
                        listKlasemen.add(
                            Table(
                                it.idLeague, it.name,
                                it.teamid, it.played,
                                it.goalsfor, it.goalsagainst,
                                it.goalsdifference, it.win,
                                it.draw, it.loss,
                                it.total
                            )
                        )
                    }

                    try {
                        recyclerView.adapter =
                            AdapterListKlasemen(activity, listKlasemen)
                    } catch (e: IllegalStateException) {
                        Log.e("ON_ERROR", e.toString())
                    }
                    shimmer.visibility = View.GONE
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

    fun saveDetailTeamToRoom(idTeam: String) {
        repositoryData.getDetailTeamFromService(idTeam)
    }

    fun getDetailTeam(
        idTeam: String,
        teamTitle: TextView,
        teamBadge: ImageView
    ) {
        disposable.add(
            repositoryData.getDetailTeamFromRoom(idTeam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ team ->
                    teamTitle.text = team.strTeam.toUpperCase()
                    Picasso.get().load(team.strTeamBadge).into(teamBadge)
                }, { err ->
                    Log.e("ON_ERROR", err.toString())
                })
        )
    }

}