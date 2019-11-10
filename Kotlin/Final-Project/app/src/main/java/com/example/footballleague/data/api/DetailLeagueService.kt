package com.example.footballleague.data.api

import com.example.footballleague.BuildConfig
import com.example.footballleague.data.api.pojo.dataleague.DataLeague
import com.example.footballleague.data.api.pojo.dataleague.DataListLeague
import com.example.footballleague.data.api.pojo.klasemen.DataKlasemen
import com.example.footballleague.data.api.pojo.match.Match
import com.example.footballleague.data.api.pojo.team.DataTeam
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface DetailLeagueService {

    companion object {
        fun dataRepositoryServices(): DetailLeagueService {
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL + "api/v1/json/" + BuildConfig.TSDB_API_KEY + "/")
                .client(okHttpClient)
                .build()
            return retrofit.create(DetailLeagueService::class.java)
        }
    }

    /**
     * List Liga
     */
    @GET("all_leagues.php")
    fun getListLeague(): Observable<DataListLeague>

    /**
     * Detail Liga
     */
    @GET("lookupleague.php")
    fun getDataLeague(
        @Query("id") idLeague: String
    ): Observable<DataLeague>

    /**
     * Previous Match
     */
    @GET("eventspastleague.php")
    fun getPastEvent(
        @Query("id") idLeague: String
    ): Observable<Match>

    /**
     * Next Match
     */
    @GET("eventsnextleague.php")
    fun getNextEvent(
        @Query("id") idLeague: String
    ): Observable<Match>

    /**
     * Searching Match
     */
    @GET("searchevents.php")
    fun getSearchEvent(
        @Query("e") event: String
    ): Observable<Match>

    /**
     * Data Team
     */
    @GET("lookupteam.php")
    suspend fun getDataTeam(
        @Query("id") idTeam: String
    ): DataTeam

    @GET("lookupteam.php")
    fun getDetailTeam(
        @Query("id") idTeam: String
    ): Observable<DataTeam>

    /**
     * Data Team by League ID
     */
    @GET("lookup_all_teams.php")
    fun getDataTeamByLeagueId(
        @Query("id") idLeague: String
    ): Observable<DataTeam>

    /**
     * Data Team by League ID
     */
    @GET("searchteams.php")
    fun searchDataTeam(
        @Query("t") team: String
    ): Observable<DataTeam>

    /**
     * Data Team by League ID
     */
    @GET("lookuptable.php")
    fun getDataKlasemen(
        @Query("l") idLeague: String
    ): Observable<DataKlasemen>
}