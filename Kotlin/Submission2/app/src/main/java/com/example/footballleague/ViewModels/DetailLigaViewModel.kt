package com.example.footballleague.ViewModels

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import com.example.footballleague.data.DetailLigaService
import com.example.footballleague.data.pojo.dataliga.League
import com.example.footballleague.data.pojo.dataliga.ListLiga
import com.example.footballleague.data.pojo.match.Event
import com.example.footballleague.data.pojo.team.Team
import java.lang.NullPointerException


class DetailLigaViewModel(application: Application) : AndroidViewModel(application) {

    private val detailLigaService = DetailLigaService()
    var listLiga: MutableList<ListLiga> = mutableListOf()
    var listDetailLiga: MutableList<League> = mutableListOf()
    var listEvent: MutableList<Event> = mutableListOf()
    var listDataTeam: MutableList<Team> = mutableListOf()

    @WorkerThread
    suspend fun getListLiga() {
        val getListLigaResponse = detailLigaService.getListLiga().await()
        listLiga.clear()
        for (result in getListLigaResponse.leagues) {
            if (result.strSport == "Soccer") {
                listLiga.add(
                    ListLiga(
                        result.idLeague,
                        result.strLeague,
                        result.strSport,
                        result.strLeagueAlternate
                    )
                )
            }
        }
    }

    @WorkerThread
    suspend fun getDetailLiga(idLeague: String) {
        val getDetailLigaResponse = detailLigaService.getDataLiga(idLeague).await()
        for (result in getDetailLigaResponse.leagues) {
            listDetailLiga.add(
                League(
                    result.idLeague,
                    result.idSoccerXML,
                    result.strSport,
                    result.strLeague,
                    result.strLeagueAlternate,
                    result.strDivision,
                    result.idCup,
                    result.intFormedYear,
                    result.dateFirstEvent,
                    result.strGender,
                    result.strCountry,
                    result.strWebsite,
                    result.strFacebook,
                    result.strTwitter,
                    result.strYoutube,
                    result.strRSS,
                    result.strDescriptionEN,
                    result.strDescriptionDE,
                    result.strDescriptionFR,
                    result.strDescriptionIT,
                    result.strDescriptionCN,
                    result.strDescriptionJP,
                    result.strDescriptionRU,
                    result.strDescriptionES,
                    result.strDescriptionPT,
                    result.strDescriptionSE,
                    result.strDescriptionNL,
                    result.strDescriptionHU,
                    result.strDescriptionNO,
                    result.strDescriptionPL,
                    result.strDescriptionIL,
                    result.strFanart1,
                    result.strFanart2,
                    result.strFanart3,
                    result.strFanart4,
                    result.strBanner,
                    result.strBadge,
                    result.strLogo,
                    result.strPoster,
                    result.strTrophy,
                    result.strNaming,
                    result.strComplete,
                    result.strLocked
                )
            )
        }
    }


    @WorkerThread
    suspend fun getLeagueEventSchedule(idLeague: String, schedule: String) {
        if (!idLeague.isNullOrEmpty()) {
            try {
                var getResponse: Any
                when (schedule) {
                    "next" -> getResponse = detailLigaService.getNextEvent(idLeague).await().events
                    "past" -> getResponse = detailLigaService.getPastEvent(idLeague).await().events
                    else -> getResponse = detailLigaService.getSearchEvent(idLeague).await().event
                }

                listEvent.clear()
                for (result in getResponse) {
                    listEvent.add(
                        Event(
                            result.idEvent,
                            result.idSoccerXML,
                            result.strEvent,
                            result.strFilename,
                            result.strSport,
                            result.idLeague,
                            result.strLeague,
                            result.strSeason,
                            result.strHomeTeam,
                            result.strAwayTeam,
                            result.intRound,
                            result.dateEvent,
                            result.strDate,
                            result.strTime,
                            result.idHomeTeam,
                            result.idAwayTeam,
                            result.strLocked,
                            result.strDescriptionEN,
                            result.intHomeScore,
                            result.intAwayScore,
                            result.intSpectators,
                            result.strHomeGoalDetails,
                            result.strHomeRedCards,
                            result.strHomeYellowCards,
                            result.strHomeLineupGoalkeeper,
                            result.strHomeLineupDefense,
                            result.strHomeLineupMidfield,
                            result.strHomeLineupForward,
                            result.strHomeLineupSubstitutes,
                            result.strHomeFormation,
                            result.strAwayRedCards,
                            result.strAwayYellowCards,
                            result.strAwayGoalDetails,
                            result.strAwayLineupGoalkeeper,
                            result.strAwayLineupDefense,
                            result.strAwayLineupMidfield,
                            result.strAwayLineupForward,
                            result.strAwayLineupSubstitutes,
                            result.strAwayFormation,
                            result.intHomeShots,
                            result.intAwayShots,
                            result.datetimeEventUTC,
                            result.strTVStation,
                            result.strResult,
                            result.strCircuit,
                            result.strCountry,
                            result.strCity,
                            result.strPoster,
                            result.strFanart,
                            result.strThumb,
                            result.strBanner,
                            result.strMap,
                            result.strTweet1,
                            result.strTweet2,
                            result.strTweet3,
                            result.strVideo
                        )
                    )
                }
            } catch (e: NullPointerException) {
                Log.e("ERROR_KEY", e.toString())
            }
        }
    }

    @WorkerThread
    suspend fun getDataTeam(idTeam: String) {
        val getDataTeamResponse = detailLigaService.getDataTeam(idTeam).await()
        listDataTeam.clear()
        for (result in getDataTeamResponse.teams) {
            listDataTeam.add(
                Team(
                    result.idTeam,
                    result.idSoccerXML,
                    result.intLoved,
                    result.strTeam,
                    result.strTeamShort,
                    result.strAlternate,
                    result.intFormedYear,
                    result.strSport,
                    result.strLeague,
                    result.idLeague,
                    result.strManager,
                    result.strStadium,
                    result.strKeywords,
                    result.strRSS,
                    result.strStadiumThumb,
                    result.strStadiumDescription,
                    result.strStadiumLocation,
                    result.intStadiumCapacity,
                    result.strWebsite,
                    result.strFacebook,
                    result.strTwitter,
                    result.strInstagram,
                    result.strDescriptionEN,
                    result.strDescriptionDE,
                    result.strDescriptionIT,
                    result.strGender,
                    result.strCountry,
                    result.strTeamBadge,
                    result.strTeamJersey,
                    result.strTeamLogo,
                    result.strTeamFanart1,
                    result.strTeamFanart2,
                    result.strTeamFanart3,
                    result.strTeamFanart4,
                    result.strTeamBanner,
                    result.strYoutube,
                    result.strLocked,
                    result.strDivision,
                    result.strDescriptionFR,
                    result.strDescriptionCN,
                    result.strDescriptionJP,
                    result.strDescriptionRU,
                    result.strDescriptionES,
                    result.strDescriptionPT,
                    result.strDescriptionSE,
                    result.strDescriptionNL,
                    result.strDescriptionHU,
                    result.strDescriptionNO,
                    result.strDescriptionIL,
                    result.strDescriptionPL
                )
            )

        }

    }
}