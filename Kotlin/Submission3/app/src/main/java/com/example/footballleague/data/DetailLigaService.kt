package com.example.footballleague.data

import com.example.footballleague.BuildConfig
import com.example.footballleague.data.pojo.dataliga.DataLiga
import com.example.footballleague.data.pojo.dataliga.DataListLiga
import com.example.footballleague.data.pojo.match.Match
import com.example.footballleague.data.pojo.team.DataTeam
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailLigaService {

    /**
     * Detail Liga
     */
    @GET("lookupleague.php")
    fun getDataLiga(
            @Query("id") idLeague: String
    ): Deferred<DataLiga>

    /**
     * List Liga
     */
    @GET("all_leagues.php")
    fun getListLiga(): Deferred<DataListLiga>

    /**
     * Previous Match
     */
    @GET("eventspastleague.php")
    fun getPastEvent(
            @Query("id") idLeague: String
    ): Deferred<Match>

    /**
     * Next Match
     */
    @GET("eventsnextleague.php")
    fun getNextEvent(
            @Query("id") idLeague: String
    ): Deferred<Match>

    /**
     * Searching Match
     */
    @GET("searchevents.php")
    fun getSearchEvent(
            @Query("e") event: String
    ): Deferred<Match>

    /**
     * Data Team
     */
    @GET("lookupteam.php")
    fun getDataTeam(
        @Query("id") idTeam: String
    ): Deferred<DataTeam>

    companion object {
        operator fun invoke(): DetailLigaService {
            val requestInterceptor = Interceptor { chain ->
                var url = chain.request()
                        .url()
                        .newBuilder()
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.BASE_URL + "api/v1/json/" + BuildConfig.TSDB_API_KEY + "/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DetailLigaService::class.java)
        }
    }


}