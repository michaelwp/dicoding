package com.example.footballleague.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_team_entity_table")
data class ListTeamEntity(
    @PrimaryKey var idTeam: String,
    @ColumnInfo(name = "strTeam") val strTeam: String,
    @ColumnInfo(name = "strLeague") val strLeague: String,
    @ColumnInfo(name = "idLeague") val idLeague: String,
    @ColumnInfo(name = "strManager") val strManager: String,
    @ColumnInfo(name = "strTeamBadge") val strTeamBadge: String,
    @ColumnInfo(name = "strDescriptionEN") val strDescriptionEN: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean = false
)