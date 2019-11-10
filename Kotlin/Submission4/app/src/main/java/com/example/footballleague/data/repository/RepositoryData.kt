package com.example.footballleague.data.repository

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.footballleague.Utility
import com.example.footballleague.data.model.RoomDb
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
import com.example.footballleague.data.remotedatasource.DetailLeagueService
import com.example.footballleague.data.remotedatasource.pojo.match.Match
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class RepositoryData(context: Context) {
    private val detailLigaService = DetailLeagueService.dataRepositoryServices()
    private val db = RoomDb.invoke(context)
    val disposable = CompositeDisposable()

    /**
     * ListLiga
     */
    fun getListLeagueFromService(fragmentManager: FragmentManager) {
        disposable.add(
            detailLigaService.getListLeague()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.leagues.isEmpty()) {
                        try {
                            Utility.errMessage(
                                fragmentManager,
                                "NullPointerException"
                            )
                        } catch (e: Exception) {
                            Log.e("ON_ERROR", e.toString())
                        }
                        return@subscribe
                    }

                    it.leagues.forEach {
                        if (it.strSport == "Soccer") {
                            getDetailLeagueFromService(it.idLeague.toString())
                        }
                    }
                }, { Log.e("ON_ERROR", it.toString()) }, {
                    Log.e("ON_COMPLETE", "getListLeagueFromService")
                })
        )
    }


    private fun getDetailLeagueFromService(idLeague: String) {
        db.roomDao().findDetailLeague(idLeague)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.isEmpty()) {
                    getDataLeagueFromService(idLeague)
                }
            }
            .doOnError { Log.e("ON_ERROR", it.toString()) }
            .subscribe()
    }

    private fun getDataLeagueFromService(idLeague: String) {
        disposable.add(
            detailLigaService.getDataLeague(idLeague)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.leagues.forEach {
                        insertDetailLeagueToRoom(
                            it.idLeague.toString(),
                            it.strLeague.toString(),
                            it.strDescriptionEN.toString(),
                            it.strBadge.toString()
                        )
                    }
                }, { Log.e("ON_ERROR", it.toString()) }, {
                    Log.e("ON_COMPLETE", "getDataLeagueFromService")
                })
        )
    }

    private fun insertDetailLeagueToRoom(
        idLeague: String,
        strLeague: String,
        strDescriptionEN: String,
        strBadge: String
    ) {
        db.roomDao().insertDetailLeague(
            DetailLeagueEntity(
                idLeague,
                strLeague,
                strDescriptionEN,
                strBadge
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("ON_COMPLETE", "insertDetailLeagueToRoom")
            }
            .doOnError { Log.e("ON_ERROR", it.toString()) }
            .doOnComplete { Log.e("ON_COMPLETE", "insertDetailLeagueToRoom") }
            .subscribe()
    }

    fun getDetailLeagueFromRoom(): Flowable<List<DetailLeagueEntity>> {
        return db.roomDao().getListDetailLeague()
    }

    /**
     * List Event
     */

    fun getListEventFromService(
        strEvent: String,
        fragmentManager: FragmentManager,
        bSheetDialogFragment: BottomSheetDialogFragment
    ) {
        disposable.add(
            detailLigaService.getSearchEvent(strEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    try {
                        if (it.event.isEmpty()) {
                            try {
                                Utility.errMessage(
                                    fragmentManager,
                                    "NullPointerException"
                                )
                            } catch (e: Exception) {
                                Log.e("ON_ERROR", e.toString())
                            }
                            return@subscribe
                        }

                        it.event.forEach {
                            if (it.strSport == "Soccer") {
                                println(it.strEvent)
                                findListEventById(
                                    it.idEvent.toString(),
                                    it.idLeague.toString(),
                                    it.schedule.toString(),
                                    it.strEvent.toString(),
                                    it.strSport.toString(),
                                    it.strHomeTeam.toString(),
                                    it.strAwayTeam.toString(),
                                    it.idHomeTeam.toString(),
                                    it.idAwayTeam.toString(),
                                    it.strLeague.toString(),
                                    it.strSeason.toString(),
                                    it.intHomeScore.toString(),
                                    it.intAwayScore.toString(),
                                    it.strHomeGoalDetails.toString(),
                                    it.strAwayGoalDetails.toString(),
                                    it.strHomeRedCards.toString(),
                                    it.strAwayRedCards.toString(),
                                    it.strHomeYellowCards.toString(),
                                    it.strAwayYellowCards.toString(),
                                    it.intHomeShots.toString(),
                                    it.intAwayShots.toString(),
                                    it.strHomeLineupGoalkeeper.toString(),
                                    it.strAwayLineupGoalkeeper.toString(),
                                    it.strHomeLineupDefense.toString(),
                                    it.strAwayLineupDefense.toString(),
                                    it.strHomeLineupMidfield.toString(),
                                    it.strAwayLineupMidfield.toString(),
                                    it.strHomeLineupForward.toString(),
                                    it.strAwayLineupForward.toString(),
                                    it.strHomeLineupSubstitutes.toString(),
                                    it.strAwayLineupSubstitutes.toString(),
                                    it.strHomeFormation.toString(),
                                    it.strAwayFormation.toString(),
                                    it.strTime.toString(),
                                    it.dateEvent.toString()
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("ON_ERROR", e.toString())
                        try {
                            bSheetDialogFragment.dismiss()
                            when (e) {
                                is UnknownHostException -> {
                                    Utility.errMessage(
                                        fragmentManager, "UnknownHostException"
                                    )
                                }
                                else -> Utility.errMessage(
                                    fragmentManager, "NullPointerException"
                                )
                            }
                        } catch (e: java.lang.Exception) {
                            Log.e("ON_ERROR", e.toString())
                        }
                    }
                }, { error ->
                    Log.e("ON_ERROR", error.toString())
                    bSheetDialogFragment.dismiss()
                    Utility.errMessage(fragmentManager, "UnknownHostException")
                }, { Log.e("ON_COMPLETE", "getListEventFromService") })
        )
    }

    private fun findListEventById(
        idEvent: String, idLeague: String,
        schedule: String, strEvent: String,
        strSport: String, strHomeTeam: String,
        strAwayTeam: String, idHomeTeam: String,
        idAwayTeam: String, strLeague: String,
        strSeason: String, intHomeScore: String,
        intAwayScore: String, strHomeGoalDetails: String,
        strAwayGoalDetails: String, strHomeRedCards: String,
        strAwayRedCards: String, strHomeYellowCards: String,
        strAwayYellowCards: String, intHomeShots: String,
        intAwayShots: String, strHomeLineupGoalkeeper: String,
        strAwayLineupGoalkeeper: String, strHomeLineupDefense: String,
        strAwayLineupDefense: String, strHomeLineupMidfield: String,
        strAwayLineupMidfield: String, strHomeLineupForward: String,
        strAwayLineupForward: String, strHomeLineupSubstitutes: String,
        strAwayLineupSubstitutes: String, strHomeFormation: String,
        strAwayFormation: String, strTime: String,
        dateEvent: String
    ) {
        if (idEvent.isNotEmpty()) {
            disposable.add(
                db.roomDao().findListEventId(idEvent)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.isEmpty()) {
                            saveListEventToRoom(
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
                                dateEvent
                            )
                        }

                    }, { err -> Log.e("ON_ERROR", err.toString()) })
            )
        }
    }

    private fun saveListEventToRoom(
        idEvent: String, idLeague: String,
        schedule: String, strEvent: String,
        strSport: String, strHomeTeam: String,
        strAwayTeam: String, idHomeTeam: String,
        idAwayTeam: String, strLeague: String,
        strSeason: String, intHomeScore: String,
        intAwayScore: String, strHomeGoalDetails: String,
        strAwayGoalDetails: String, strHomeRedCards: String,
        strAwayRedCards: String, strHomeYellowCards: String,
        strAwayYellowCards: String, intHomeShots: String,
        intAwayShots: String, strHomeLineupGoalkeeper: String,
        strAwayLineupGoalkeeper: String, strHomeLineupDefense: String,
        strAwayLineupDefense: String, strHomeLineupMidfield: String,
        strAwayLineupMidfield: String, strHomeLineupForward: String,
        strAwayLineupForward: String, strHomeLineupSubstitutes: String,
        strAwayLineupSubstitutes: String, strHomeFormation: String,
        strAwayFormation: String, strTime: String,
        dateEvent: String
    ) {
        GlobalScope.launch {
            db.roomDao().insertListEvent(
                ListEventEntity(
                    idEvent, idLeague,
                    schedule, strEvent,
                    strSport, strHomeTeam,
                    strAwayTeam, idHomeTeam,
                    idAwayTeam, strLeague,
                    "Season ${strSeason}",
                    when (intHomeScore) {
                        "null" -> "-"
                        else -> intHomeScore
                    },
                    when (intAwayScore) {
                        "null" -> "-"
                        else -> intAwayScore
                    },
                    when (strHomeGoalDetails) {
                        "null" -> "-"
                        else -> strHomeGoalDetails.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayGoalDetails) {
                        "null" -> "-"
                        else -> strAwayGoalDetails.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeRedCards) {
                        "null" -> "-"
                        else -> strHomeRedCards.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayRedCards) {
                        "null" -> "-"
                        else -> strAwayRedCards.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeYellowCards) {
                        "null" -> "-"
                        else -> strHomeYellowCards.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayYellowCards) {
                        "null" -> "-"
                        else -> strAwayYellowCards.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (intHomeShots) {
                        "null" -> "-"
                        else -> intHomeShots
                    },
                    when (intAwayShots) {
                        "null" -> "-"
                        else -> intAwayShots
                    },
                    when (strHomeLineupGoalkeeper) {
                        "null" -> "-"
                        else -> strHomeLineupGoalkeeper.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayLineupGoalkeeper) {
                        "null" -> "-"
                        else -> strAwayLineupGoalkeeper.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeLineupDefense) {
                        "null" -> "-"
                        else -> strHomeLineupDefense.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayLineupDefense) {
                        "null" -> "-"
                        else -> strAwayLineupDefense.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeLineupMidfield) {
                        "null" -> "-"
                        else -> strHomeLineupMidfield.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayLineupMidfield) {
                        "null" -> "-"
                        else -> strAwayLineupMidfield.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeLineupForward) {
                        "null" -> "-"
                        else -> strHomeLineupForward.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayLineupForward) {
                        "null" -> "-"
                        else -> strAwayLineupForward.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeLineupSubstitutes) {
                        "null" -> "-"
                        else -> strHomeLineupSubstitutes.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strAwayLineupSubstitutes) {
                        "null" -> "-"
                        else -> strAwayLineupSubstitutes.replace(
                            ";".toRegex(),
                            ";\n"
                        )
                    },
                    when (strHomeFormation) {
                        "null" -> "-"
                        else -> strHomeFormation
                    },
                    when (strAwayFormation) {
                        "null" -> "-"
                        else -> strAwayFormation
                    },
                    when (strTime) {
                        "null" -> "-"
                        else -> strTime.replace(
                            " GMT".toRegex(),
                            ""
                        )
                    },
                    Utility.formatTo(
                        Utility.toDate(
                            inputDtTime = dateEvent,
                            dateTimeStr = "yyyy-MM-dd"
                        ),
                        dateTimeFormat = "dd-MMM-yyyy"
                    ),
                    getDataTeamFromService(idHomeTeam),
                    getDataTeamFromService(idAwayTeam)
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError { error -> Log.e("ON_ERROR", error.toString()) }
                .doOnComplete { Log.e("ON_COMPLETE", "saveListEventToRoom") }
                .subscribe()
        }

    }


    suspend fun getDataTeamFromService(idTeam: String): String {
        return detailLigaService.getDataTeam(idTeam).teams[0].strTeamBadge.toString()
    }

    fun getDataScheduleEventFromService(
        idLeague: String,
        schedule: String,
        fragmentManager: FragmentManager
    ) {
        var event: Observable<Match>
        when (schedule) {
            "past" -> {
                event = detailLigaService.getPastEvent(idLeague)
            }
            else -> {
                event = detailLigaService.getNextEvent(idLeague)
            }
        }
        disposable.add(
            event
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    try {
                        if (it.events.isEmpty()) {
                            try {
                                Utility.errMessage(
                                    fragmentManager,
                                    "NullPointerException"
                                )
                            } catch (e: Exception) {
                                Log.e("ON_ERROR", e.toString())
                            }
                            return@subscribe
                        }

                        it.events.forEach {
                            findListEventById(
                                it.idEvent.toString(),
                                it.idLeague.toString(),
                                schedule,
                                it.strEvent.toString(),
                                it.strSport.toString(),
                                it.strHomeTeam.toString(),
                                it.strAwayTeam.toString(),
                                it.idHomeTeam.toString(),
                                it.idAwayTeam.toString(),
                                it.strLeague.toString(),
                                it.strSeason.toString(),
                                it.intHomeScore.toString(),
                                it.intAwayScore.toString(),
                                it.strHomeGoalDetails.toString(),
                                it.strAwayGoalDetails.toString(),
                                it.strHomeRedCards.toString(),
                                it.strAwayRedCards.toString(),
                                it.strHomeYellowCards.toString(),
                                it.strAwayYellowCards.toString(),
                                it.intHomeShots.toString(),
                                it.intAwayShots.toString(),
                                it.strHomeLineupGoalkeeper.toString(),
                                it.strAwayLineupGoalkeeper.toString(),
                                it.strHomeLineupDefense.toString(),
                                it.strAwayLineupDefense.toString(),
                                it.strHomeLineupMidfield.toString(),
                                it.strAwayLineupMidfield.toString(),
                                it.strHomeLineupForward.toString(),
                                it.strAwayLineupForward.toString(),
                                it.strHomeLineupSubstitutes.toString(),
                                it.strAwayLineupSubstitutes.toString(),
                                it.strHomeFormation.toString(),
                                it.strAwayFormation.toString(),
                                it.strTime.toString(),
                                it.dateEvent.toString()
                            )
                        }
                    } catch (e: NullPointerException) {
                        Log.e("ERR_REPO_1", e.toString())
                        Utility.errMessage(fragmentManager, "NullPointerException")
                    }
                }, { error -> Log.e("ON_ERROR", error.toString()) }, {
                    Log.e("ON_COMPLETE", "getDataScheduleEventFromService")
                })
        )
    }

    fun findListEventFromRoom(strEvent: String): Maybe<List<ListEventEntity>> {
        return db.roomDao().findListEventStr(
            "${strEvent}%",
            "%${strEvent}%",
            "%${strEvent}"
        )
    }

    fun findListEventSchedule(idLeague: String, schedule: String): Flowable<List<ListEventEntity>> {
        return db.roomDao().findListEventSchedule(idLeague, schedule)
    }

    fun updateFavorite(fav: Boolean, idEvent: String) {
        db.roomDao().updateFavorite(fav, idEvent)
            .doFinally {
                println("${idEvent} : Add to favorite successfully")
            }
            .doOnError { err -> Log.e("ON_ERROR", err.toString()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getListFavouriteEventAll(): Flowable<List<ListEventEntity>> {
        return db.roomDao().getListFavouriteEvent()
    }

    fun getListFavouriteEventByLeague(idLeague: String): Flowable<List<ListEventEntity>> {
        return db.roomDao().getListFavouriteEventbyIdLeague(idLeague)
    }
}