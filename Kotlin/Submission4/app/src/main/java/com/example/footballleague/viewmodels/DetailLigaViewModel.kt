package com.example.footballleague.viewmodels

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.Utility
import com.example.footballleague.adapter.AdapterListEvent
import com.example.footballleague.adapter.AdapterListLeague
import com.example.footballleague.data.model.RoomDb
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
import com.example.footballleague.data.remotedatasource.pojo.dataleague.League
import com.example.footballleague.data.remotedatasource.pojo.match.Event
import com.example.footballleague.data.repository.RepositoryData
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

    private val repositoryData: RepositoryData by lazy {
        RepositoryData(application)
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
        CompositeDisposable().add(
            repositoryData.getDetailLeagueFromRoom()
                .doOnSubscribe { shimmer.visibility = View.VISIBLE }
                .doFinally {
                    Log.e("ON_COMPLETE", "findLeague")
                }
                .subscribeOn(Schedulers.io())
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
        CompositeDisposable().add(
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

    //TODO Instrument testing
    fun findSchedule(
        idLeague: String,
        eSchedule: String,
        shimmer: ShimmerFrameLayout,
        listMatchRecycler: RecyclerView,
        activity: Activity
    ) {
        CompositeDisposable().add(
            repositoryData.findListEventSchedule(idLeague, eSchedule)
                .doOnSubscribe {
                    shimmer.visibility = View.VISIBLE
                }
                .doFinally {
                    Log.e("ON_COMPLETE", "findSchedule")
                }
                .subscribeOn(Schedulers.io())
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

    //TODO Instrument testing
    fun getListFavEvent(
        idLeague: String,
        shimmer: ShimmerFrameLayout,
        listFavRecycler: RecyclerView,
        activity: Activity
    ) {
        var repo: Flowable<List<ListEventEntity>> =
            repositoryData.getListFavouriteEventByLeague(idLeague)
        if (idLeague == "") {
            repo = repositoryData.getListFavouriteEventAll()
        }
        CompositeDisposable().add(
            repo
                .doOnSubscribe { shimmer.visibility = View.VISIBLE }
                .doFinally {
                    Log.e("ON_COMPLETE", "getListFavEventAll")
                }
                .subscribeOn(Schedulers.io())
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
}