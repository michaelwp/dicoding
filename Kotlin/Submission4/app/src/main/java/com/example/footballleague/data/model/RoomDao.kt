package com.example.footballleague.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballleague.data.model.entity.DetailLeagueEntity
import com.example.footballleague.data.model.entity.ListEventEntity
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
                "and schedule = :schedule ORDER BY idEvent"
    )
    fun findListEventSchedule(idLeague: String, schedule: String): Flowable<List<ListEventEntity>>

    @Query("UPDATE list_event_entity_table SET favorite = :fav WHERE idEvent = :idEvent")
    fun updateFavorite(fav: Boolean, idEvent: String): Completable

    @Query("SELECT * FROM list_event_entity_table WHERE favorite = :fav ORDER BY idEvent")
    fun getListFavouriteEvent(fav: Boolean = true): Flowable<List<ListEventEntity>>

    @Query(
        "SELECT * FROM list_event_entity_table WHERE favorite = :fav " +
                "AND idLeague = :idLeague ORDER BY idEvent"
    )
    fun getListFavouriteEventbyIdLeague(
        idLeague: String,
        fav: Boolean = true
    ): Flowable<List<ListEventEntity>>
}