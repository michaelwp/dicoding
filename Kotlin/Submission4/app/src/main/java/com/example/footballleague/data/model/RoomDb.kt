package com.example.footballleague.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
import com.example.footballleague.data.model.entity.ListLeagueEntity

@Database(
    entities = [
        ListLeagueEntity::class,
        DetailLeagueEntity::class,
        ListEventEntity::class
    ],
    version = 1
)
abstract class RoomDb : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var instance: RoomDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            RoomDb::class.java, "soccer_league.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}