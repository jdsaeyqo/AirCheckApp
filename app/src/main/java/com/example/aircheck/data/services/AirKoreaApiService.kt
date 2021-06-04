package com.example.aircheck.data.services

import com.example.aircheck.BuildConfig
import com.example.aircheck.model.airquality.AirQualityResponse
import com.example.aircheck.model.monitoringstation.MonitoringStationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {

    @GET("B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList" +
            "?serviceKey=${BuildConfig.AIRKOREA_SERVICE_KEY}" +
            "&returnType=json")
    suspend fun getNearbyMonitoringStation(
        @Query("tmX") tmX : Double,
        @Query("tmY") tmY : Double

    ): Response<MonitoringStationsResponse>

    @GET("B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"+
    "?servicekey=${BuildConfig.AIRKOREA_SERVICE_KEY}"+
    "&returnType=json"+
    "&dataTerm=DAILY"+
    "&ver=1.3")
    suspend fun getRealtimeAirQualities(

        @Query("stationName") stationName : String

    ) : Response<AirQualityResponse>


}