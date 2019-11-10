package com.example.footballleague.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_league_entity_table")
data class DetailLeagueEntity (
    @PrimaryKey
    var idLeague:String,
    @ColumnInfo(name = "strLeague") var strLeague:String,
    @ColumnInfo(name = "strDescriptionEN") var strDescriptionEN:String,
    @ColumnInfo(name = "strBadge") var strBadge:String
)