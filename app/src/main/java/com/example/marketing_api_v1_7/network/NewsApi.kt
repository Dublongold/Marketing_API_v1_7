package com.example.marketing_api_v1_7.network

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET

interface NewsApi {
    @GET("football")
    suspend fun getFootball(): ResponseBody


}