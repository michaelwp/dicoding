package com.example.footballleague.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_klasemen_entity_table")
data class ListKlasemenEntity(
    @PrimaryKey
    var teamid: String,
    @ColumnInfo(name = "idLeague") var idLeague:String,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "played") var played:Int?,
    @ColumnInfo(name = "goalsfor") var goalsfor: Int?,
    @ColumnInfo(name = "goalsagainst") var goalsagainst: Int?,
    @ColumnInfo(name = "goalsdifference") var goalsdifference: Int?,
    @ColumnInfo(name = "win") var win: Int?,
    @ColumnInfo(name = "draw") var draw: Int?,
    @ColumnInfo(name = "loss") var loss: Int?,
    @ColumnInfo(name = "total") var total: Int?
)