package com.example.marketing_api_v1_7.network

import com.example.marketing_api_v1_7.models.TokenReceiver
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

class JWTToken {
    private val clientId = "partners-81e1a437adca001af0b62d03d2ddc9cd"
    private val clientSecret = "tSIDE9j4lkUFcp@Q8pUIH&FcUltUNtVUNmQT!t2LN!tdqGUvOUPkSnIie&#*fq7n"
    private val clientToo = OkHttpClient.Builder()
        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY})
        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC})
        .addInterceptor (HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS})
        .build()
    private val client: JWTTokenApi = Retrofit.Builder()
        .baseUrl("https://cpservm.com/gateway/")
        .client(clientToo)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(JWTTokenApi::class.java)

    private var jwt: String = ""
    private val jwtWithBearer
        get() = "Bearer $jwt"
    private val mutex = Mutex()

    suspend fun getJWTToken(withBearer: Boolean = false): String {
        return if(jwt == "") {
            loadJWTToken()
            if(withBearer) jwtWithBearer else jwt
        }
        else {
            if(withBearer) jwtWithBearer else jwt
        }
    }

    suspend fun loadJWTToken() {
        mutex.lock()
        if(jwt != "") return

        val jwtResponse = client.getJWT(clientId, clientSecret)
        if (jwtResponse.isSuccessful) {
            val jwtBody = jwtResponse.body()!!
            jwt = jwtBody.accessToken
            if(mutex.isLocked) mutex.unlock()
            eraseJwtTokenAfterSeconds(jwtBody.expiresIn.toLong())
        }
        if(mutex.isLocked) mutex.unlock()
    }

    private suspend fun eraseJwtTokenAfterSeconds(seconds: Long) {
        delay(seconds * 1000)
        jwt = ""
    }

    interface JWTTokenApi {

        @POST("token")
        @Headers("Content-Type: application/x-www-form-urlencoded")
        @FormUrlEncoded
        suspend fun getJWT(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String
        ): Response<TokenReceiver>
    }
}