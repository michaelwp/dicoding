package com.example.footballleague.data

data class FavoriteEvent (
        val id: Long?,
        val idEvent: String?,
        val idLeague: String?,
        val strEvent: String?,
        val strLeague: String?,
        val strHomeTeam: String?,
        val strAwayTeam: String?,
        val dateEvent: String?,
        val strTime: String?,
        val intHomeScore: String?,
        val intAwayScore: String?,
        val strSeason: String?,
        val strHomeGoalDetails: String?,
        val strAwayGoalDetails: String?,
        val strHomeRedCards: String?,
        val strAwayRedCards: String?,
        val strHomeYellowCards: String?,
        val strAwayYellowCards: String?,
        val intHomeShots: String?,
        val intAwayShots: String?,
        val strHomeLineupGoalkeeper: String?,
        val strAwayLineupGoalkeeper: String?,
        val strHomeLineupDefense: String?,
        val strAwayLineupDefense: String?,
        val strHomeLineupForward: String?,
        val strAwayLineupForward: String?,
        val strHomeLineupSubstitutes: String?,
        val strAwayLineupSubstitutes: String?,
        val strHomeTeamBadge: String?,
        val strAwayTeamBadge: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "FAVORITE_EVENT_TABLE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val ID_LEAGUE: String = "ID_LEAGUE"
        const val STR_EVENT: String = "STR_EVENT"
        const val STR_LEAGUE: String = "STR_LEAGUE"
        const val STR_HOME_TEAM: String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM: String = "STR_AWAY_TEAM"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val STR_TIME: String = "STR_TIME"
        const val INT_HOME_SCORE: String = "INT_HOME_SCORE"
        const val INT_AWAY_SCORE: String = "INT_AWAY_SCORE"
        const val STR_SEASON: String = "STR_SEASON"
        const val STR_HOME_GOAL_DETAILS = "STR_HOME_GOAL_DETAILS"
        const val STR_AWAY_GOAL_DETAILS = "STR_AWAY_GOAL_DETAILS"
        const val STR_HOME_RED_CARDS: String = "STR_HOME_RED_CARDS"
        const val STR_AWAY_RED_CARDS: String = "STR_AWAY_RED_CARDS"
        const val STR_HOME_YELLOW_CARDS: String = "STR_HOME_YELLOW_CARDS"
        const val STR_AWAY_YELLOW_CARDS: String = "STR_AWAY_YELLOW_CARDS"
        const val INT_HOME_SHOTS: String = "STR_HOME_SHOTS"
        const val INT_AWAY_SHOTS: String = "STR_AWAY_SHOTS"
        const val STR_HOME_LINEUP_GOAL_KEEPER: String = "STR_HOME_LINEUP_GOAL_KEEPER"
        const val STR_AWAY_LINEUP_GOAL_KEEPER: String = "STR_AWAY_LINEUP_GOAL_KEEPER"
        const val STR_HOME_LINEUP_DEFENSE: String = "STR_HOME_LINEUP_DEFENSE"
        const val STR_AWAY_LINEUP_DEFENSE: String = "STR_AWAY_LINEUP_DEFENSE"
        const val STR_HOME_LINEUP_FORWARD: String = "STR_HOME_LINEUP_FORWARD"
        const val STR_AWAY_LINEUP_FORWARD: String = "STR_AWAY_LINEUP_FORWARD"
        const val STR_HOME_LINEUP_SUBTITUTES: String = "STR_HOME_LINEUP_SUBTITUTES"
        const val STR_AWAY_LINEUP_SUBTITUTES: String = "STR_AWAY_LINEUP_SUBTITUTES"
        const val STR_HOME_TEAM_BADGE: String = "STR_HOME_TEAM_BADGE"
        const val STR_AWAY_TEAM_BADGE: String = "STR_AWAY_TEAM_BADGE"
    }
}