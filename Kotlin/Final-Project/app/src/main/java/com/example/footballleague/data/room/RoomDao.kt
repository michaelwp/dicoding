package com.example.footballleague.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballleague.data.room.entity.DetailLeagueEntity
import com.example.footballleague.data.room.entity.ListEventEntity
import com.example.footballleague.data.room.entity.ListKlasemenEntity
import com.example.footballleague.data.room.entity.ListTeamEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface RoomDao {

    /**
     * detail league
     */
    @Query("SELECT * FROM detail_league_entity_table ORDER BY strLeague")
    fun getListDetailLeague(): Flowable<List<DetailLeagueEntity>>

    @Query("SELECT * FROM detail_league_entity_table WHERE idLeague=:idLeague")
    fun findDetailLeague(idLeague: String): Single<List<DetailLeagueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailLeague(detailLeagueEntity: DetailLeagueEntity): Completable

    /**
     * data event
     */
    @Query(
        "SELECT * FROM list_event_entity_table" +
                " WHERE strEvent LIKE :strEvent " +
                "OR strEvent LIKE :strEventA " +
                "OR strEvent LIKE :strEventB " +
                "ORDER BY idEvent"
    )
    fun findListEventStr(
        strEvent: String,
        strEventA: String,
        strEventB: String
    ): Maybe<List<ListEventEntity>>

    @Query("SELECT * FROM list_event_entity_table WHERE idEvent = :idEvent ORDER BY idEvent")
    fun findListEventId(idEvent: String): Single<List<ListEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListEvent(listEventEntity: ListEventEntity): Completable

    @Query(
        "SELECT * FROM list_event_entity_table WHERE idLeague = :idLeague " +
                "and schedule = :schedule ORDER BY favorite DESC"
    )
    fun findListEventSchedule(idLeague: String, schedule: String): Flowable<List<ListEventEntity>>

    @Query("UPDATE list_event_entity_table SET favorite = :fav WHERE idEvent = :idEvent")
    fun updateFavorite(fav: Boolean, idEvent: String): Completable

    @Query("SELECT * FROM list_event_entity_table WHERE favorite = 1 ORDER BY idEvent")
    fun getListFavouriteEvent(): Flowable<List<ListEventEntity>>

    @Query(
        "SELECT * FROM list_event_entity_table " +
                "WHERE favorite = 1 AND idLeague = :idLeague ORDER BY idEvent"
    )
    fun getListFavouriteEventbyIdLeague(idLeague: String): Flowable<List<ListEventEntity>>

    @Query("SELECT * FROM list_team_entity_table WHERE idTeam = :idTeam ORDER BY idTeam")
    fun findTeamByIdTeam(idTeam: String): Single<List<ListTeamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListTeam(listTeamEntity: ListTeamEntity): Completable

    @Query("SELECT * FROM list_team_entity_table WHERE idLeague = :idLeague ORDER BY favorite DESC")
    fun getListTeamByIdLeague(idLeague: String): Flowable<List<ListTeamEntity>>

    @Query("SELECT * FROM list_team_entity_table WHERE idTeam = :idTeam")
    fun getDetailTeam(idTeam: String): Single<ListTeamEntity>

    @Query("UPDATE list_team_entity_table SET favorite = :fav WHERE idTeam = :idTeam")
    fun updateTeamFavorite(fav: Boolean, idTeam: String): Completable

    @Query("SELECT * FROM list_team_entity_table WHERE strTeam LIKE :strTeam AND idLeague = :idLeague")
    fun searchTeam(strTeam: String, idLeague: String): Flowable<List<ListTeamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListKlasemen(listKlasemenEntity: ListKlasemenEntity): Completable

    @Query("SELECT * FROM list_klasemen_entity_table WHERE teamid = :idTeam")
    fun findListKlasemen(idTeam: String): Single<List<ListKlasemenEntity>>

    @Query("SELECT * FROM list_klasemen_entity_table WHERE idLeague = :idLeague ORDER BY name")
    fun getListKlasemen(idLeague: String): Flowable<List<ListKlasemenEntity>>

}