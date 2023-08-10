package com.example.marketing_api_v1_7.network

import com.example.marketing_api_v1_7.models.*
import com.example.marketing_api_v1_7.models.live.LiveSportsEventsReceiver
import com.example.marketing_api_v1_7.models.preMatch.PreMatchSportsEventsReceiver
import com.example.marketing_api_v1_7.models.theLast.ListOfTournamentsForAGivenPeriod
import com.example.marketing_api_v1_7.models.theLast.SportsEventsResultsReceiver
import com.example.marketing_api_v1_7.util.SportIds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.ZoneOffset

interface MainRetrofitApi {
    @GET("datafeed/prematch/api/v2/sportevents")
    suspend fun getPreMatchSportsEvents(
        @Header("Authorization") token: String,
        @Query("sportids") sportIds: List<Int> = listOf(SportIds.SELECTED_FOOTBALL),
        @Query("ref") ref: String = "178",
        @Query("count") count: Int = 100,
    ): Response<PreMatchSportsEventsReceiver>

    @GET("datafeed/live/api/v2/sportevents")
    suspend fun getLiveSportsEvents(
        @Header("Authorization") token: String,
        @Query("sportids") sportIds: List<Int> = listOf(SportIds.SELECTED_FOOTBALL),
        @Query("ref") ref: String = "178",
        @Query("count") count: Int = 100,
    ): Response<LiveSportsEventsReceiver>

    @GET("result/api/v1/tournaments")
    suspend fun getListOfTournamentsForAGivenPeriod(
        @Header("Authorization") token: String,
        @Query("sportId") sportId: List<Int>,
        @Query("dateFrom") dateFrom: Int = LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC).toInt(),
        @Query("dateTo") dateTo: Int = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt(),
        @Query("ref") ref: String = "178",
    ): Response<ListOfTournamentsForAGivenPeriod>

    @GET("result/api/v1/sportevents")
    suspend fun getSportsEventResultsByTournament(
        @Header("Authorization") token: String,
        @Query("tournamentIds") tournamentIds: List<Int>,
        @Query("dateFrom") dateFrom: Int = LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC).toInt(),
        @Query("dateTo") dateTo: Int = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt(),
        @Query("ref") ref: String = "178",
        @Query("count") count: Int = 100,
    ): Response<SportsEventsResultsReceiver>
}