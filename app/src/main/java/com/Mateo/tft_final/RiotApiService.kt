package com.Mateo.tft_final

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RiotApiService {
    @Headers("X-Riot-Token: RGAPI-d3982fb3-e6ca-4c4a-9091-d81defb8468e")
    @GET("/tft/summoner/v1/summoners/by-name/{summonerName}")
    suspend fun getSummonerByName(
        @Path("summonerName") summonerName: String,
        @Query("api_key") apiKey: String
    ): TftPlayerStats

     @GET("/tft/league/v1/entries/by-summoner/{summonerId}")
    suspend fun getTftPlayerStats(
        @Path("summonerId") summonerId: String,
        @Query("api_key") apiKey: String
    ): List<TftPlayerStats>

}