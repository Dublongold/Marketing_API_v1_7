package com.example.marketing_api_v1_7.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientBuilder {
    private val baseUrl = "https://cpservm.com/gateway/marketing/"
//    private val clientToo = OkHttpClient.Builder()
//        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY})
//        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC})
//        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS})
//        .build()
    val client: MainRetrofitApi = Retrofit.Builder()
        .baseUrl(baseUrl)
//        .client(clientToo)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MainRetrofitApi::class.java)

    val newsClient: NewsApi = Retrofit.Builder()
        .baseUrl("https://spinbetter1.online/ru/blog/")
        .build()
        .create(NewsApi::class.java)
}