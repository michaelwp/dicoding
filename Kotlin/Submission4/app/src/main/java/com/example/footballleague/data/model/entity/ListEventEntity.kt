package com.example.footballleague.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_event_entity_table")
data class ListEventEntity(
    @PrimaryKey var idEvent: String,
    @ColumnInfo(name = "idLeague") var idLeague: String,
    @ColumnInfo(name = "schedule") var schedule: String,
    @ColumnInfo(name = "strEvent") var strEvent: String,
    @ColumnInfo(name = "strSport") var strSport: String,
    @ColumnInfo(name = "strHomeTeam") var strHomeTeam: String,
    @ColumnInfo(name = "strAwayTeam") var strAwayTeam: String,
    @ColumnInfo(name = "idHomeTeam") var idHomeTeam: String,
    @ColumnInfo(name = "idAwayTeam") var idAwayTeam: String,
    @ColumnInfo(name = "strLeague") var strLeague: String,
    @ColumnInfo(name = "strSeason") var strSeason: String,
    @ColumnInfo(name = "intHomeScore") var intHomeScore: String,
    @ColumnInfo(name = "intAwayScore") var intAwayScore: String,
    @ColumnInfo(name = "strHomeGoalDetails") var strHomeGoalDetails: String,
    @ColumnInfo(name = "strAwayGoalDetails") var strAwayGoalDetails: String,
    @ColumnInfo(name = "strHomeRedCards") var strHomeRedCards: String,
    @ColumnInfo(name = "strAwayRedCards") var strAwayRedCards: String,
    @ColumnInfo(name = "strHomeYellowCards") var strHomeYellowCards: String,
    @ColumnInfo(name = "strAwayYellowCards") var strAwayYellowCards: String,
    @ColumnInfo(name = "intHomeShots") var intHomeShots: String,
    @ColumnInfo(name = "intAwayShots") var intAwayShots: String,
    @ColumnInfo(name = "strHomeLineupGoalkeeper") var strHomeLineupGoalkeeper: String,
    @ColumnInfo(name = "strAwayLineupGoalkeeper") var strAwayLineupGoalkeeper: String,
    @ColumnInfo(name = "strHomeLineupDefense") var strHomeLineupDefense: String,
    @ColumnInfo(name = "strAwayLineupDefense") var strAwayLineupDefense: String,
    @ColumnInfo(name = "strHomeLineupMidfield") var strHomeLineupMidfield: String,
    @ColumnInfo(name = "strAwayLineupMidfield") var strAwayLineupMidfield: String,
    @ColumnInfo(name = "strHomeLineupForward") var strHomeLineupForward: String,
    @ColumnInfo(name = "strAwayLineupForward") var strAwayLineupForward: String,
    @ColumnInfo(name = "strHomeLineupSubstitutes") var strHomeLineupSubstitutes: String,
    @ColumnInfo(name = "strAwayLineupSubstitutes") var strAwayLineupSubstitutes: String,
    @ColumnInfo(name = "strHomeFormation") var strHomeFormation: String,
    @ColumnInfo(name = "strAwayFormation") var strAwayFormation: String,
    @ColumnInfo(name = "strTime") var strTime: String,
    @ColumnInfo(name = "dateEvent") var dateEvent: String,
    @ColumnInfo(name = "strHomeTeamBadge") var strHomeTeamBadge: String,
    @ColumnInfo(name = "strAwayTeamBadge") var strAwayTeamBadge: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false
)