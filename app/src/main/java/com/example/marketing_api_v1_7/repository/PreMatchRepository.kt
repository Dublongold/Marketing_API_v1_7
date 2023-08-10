package com.example.marketing_api_v1_7.repository

import android.util.Log
import com.example.marketing_api_v1_7.models.preMatch.PreMatchSportsEvents
import com.example.marketing_api_v1_7.network.JWTToken
import com.example.marketing_api_v1_7.network.MainRetrofitApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.java.KoinJavaComponent.inject
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PreMatchRepository: MatchRepository<PreMatchSportsEvents> {
    private val networkClient: MainRetrofitApi by inject(MainRetrofitApi::class.java)
    private var _matches = MutableStateFlow<List<PreMatchSportsEvents>>(emptyList())
    override var currentSportId = -1

    init {
        Log.i("Pre match", "Why pre match?")
    }

    private val jwt: JWTToken by inject(JWTToken::class.java)

    override val matches: StateFlow<List<PreMatchSportsEvents>>
        get() = _matches

    override suspend fun loadFirst100Matches(sportId: Int) {
        try {
            if(_matches.value.isEmpty() || sportId != currentSportId) {
                val response = networkClient.getPreMatchSportsEvents(
                    sportIds = listOf(sportId),
                    token = jwt.getJWTToken(true)
                )
                if(response.isSuccessful) {
                    _matches.update { response.body()!!.items ?: emptyList() }
                    currentSportId = sportId
                }
                else {
                    Log.e("loadFirst30Matches", "Response isn't successful (Status: ${response.code()}; jwt: {jwt: ${jwt.getJWTToken()}; jwtWithBearer: ${jwt.getJWTToken(true)}}.")
                }
            }
            else {
                throw IllegalStateException("Try to load matches, but the matches have been loaded.")
            }
        }
        catch(_: UnknownHostException) {}
        catch(_: SocketTimeoutException) {}
    }

    override suspend fun loadNewMatches() {
        TODO("Not yet implemented")
    }
}