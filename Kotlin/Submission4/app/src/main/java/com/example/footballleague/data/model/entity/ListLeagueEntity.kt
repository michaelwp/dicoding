package com.example.footballleague.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_league_entity_table")
data class ListLeagueEntity(
    @PrimaryKey
    var idLeague:String,
    @ColumnInfo(name = "strLeague") var strLeague:String,
    @ColumnInfo(name = "strSport") var strSport:String,
    @ColumnInfo(name = "strLeagueAlternate") var strLeagueAlternate:String
)