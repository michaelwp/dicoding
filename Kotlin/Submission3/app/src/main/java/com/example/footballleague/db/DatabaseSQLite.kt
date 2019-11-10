package com.example.footballleague.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.footballleague.data.FavoriteEvent
import org.jetbrains.anko.db.*

class DatabaseSQLite(
        context: Context
) : ManagedSQLiteOpenHelper(
        context,
        "favorites_events_db",
        null,
        1
) {
    companion object {
        private var instance: DatabaseSQLite? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseSQLite {
            if (instance == null) {
                instance = DatabaseSQLite(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
                FavoriteEvent.TABLE_FAVORITE,
                true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.ID_EVENT to TEXT + UNIQUE,
                FavoriteEvent.ID_LEAGUE to TEXT,
                FavoriteEvent.STR_EVENT to TEXT,
                FavoriteEvent.STR_LEAGUE to TEXT,
                FavoriteEvent.STR_HOME_TEAM to TEXT,
                FavoriteEvent.STR_AWAY_TEAM to TEXT,
                FavoriteEvent.DATE_EVENT to TEXT,
                FavoriteEvent.STR_TIME to TEXT,
                FavoriteEvent.INT_HOME_SCORE to TEXT,
                FavoriteEvent.INT_AWAY_SCORE to TEXT,
                FavoriteEvent.STR_SEASON to TEXT,
                FavoriteEvent.STR_HOME_GOAL_DETAILS to TEXT,
                FavoriteEvent.STR_AWAY_GOAL_DETAILS to TEXT,
                FavoriteEvent.STR_HOME_RED_CARDS to TEXT,
                FavoriteEvent.STR_AWAY_RED_CARDS to TEXT,
                FavoriteEvent.STR_HOME_YELLOW_CARDS to TEXT,
                FavoriteEvent.STR_AWAY_YELLOW_CARDS to TEXT,
                FavoriteEvent.INT_HOME_SHOTS to TEXT,
                FavoriteEvent.INT_AWAY_SHOTS to TEXT,
                FavoriteEvent.STR_HOME_LINEUP_GOAL_KEEPER to TEXT,
                FavoriteEvent.STR_AWAY_LINEUP_GOAL_KEEPER to TEXT,
                FavoriteEvent.STR_HOME_LINEUP_DEFENSE to TEXT,
                FavoriteEvent.STR_AWAY_LINEUP_DEFENSE to TEXT,
                FavoriteEvent.STR_HOME_LINEUP_FORWARD to TEXT,
                FavoriteEvent.STR_AWAY_LINEUP_FORWARD to TEXT,
                FavoriteEvent.STR_HOME_LINEUP_SUBTITUTES to TEXT,
                FavoriteEvent.STR_AWAY_LINEUP_SUBTITUTES to TEXT,
                FavoriteEvent.STR_HOME_TEAM_BADGE to TEXT,
                FavoriteEvent.STR_AWAY_TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(FavoriteEvent.TABLE_FAVORITE, true)
    }

}

val Context.database: DatabaseSQLite
    get() = DatabaseSQLite.getInstance(applicationContext)