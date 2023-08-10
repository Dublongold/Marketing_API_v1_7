package com.example.marketing_api_v1_7.repository

import android.util.Log
import com.example.marketing_api_v1_7.models.theLast.SportTournament
import com.example.marketing_api_v1_7.models.theLast.SportsEventsResults
import com.example.marketing_api_v1_7.models.theLast.TheLastSportsEvents
import com.example.marketing_api_v1_7.network.JWTToken
import com.example.marketing_api_v1_7.network.MainRetrofitApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.java.KoinJavaComponent.inject
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TheLastRepository: MatchRepository<TheLastSportsEvents> {
    private val networkClient: MainRetrofitApi by inject(MainRetrofitApi::class.java)
    private var _matches = MutableStateFlow<List<TheLastSportsEvents>>(emptyList())
    private var tournaments: List<SportTournament> = emptyList()
    private var tournamentForLoadId: Int? = null
    override var currentSportId = -1

    private val jwt: JWTToken by inject(JWTToken::class.java)

    override val matches: StateFlow<List<TheLastSportsEvents>>
        get() = _matches

    override suspend fun loadFirst100Matches(sportId: Int) {
        try {
            if(_matches.value.isEmpty() || sportId != currentSportId) {
                currentSportId = sportId
                Log.i("Sport id", currentSportId.toString())
                val response = networkClient.getListOfTournamentsForAGivenPeriod(
                    sportId = listOf(currentSportId),
                    token = jwt.getJWTToken(true)
                )
                if(response.isSuccessful) {
                    val responseBody = response.body()!!
                    Log.i("Test", "$sportId, $currentSportId")
                    tournaments = responseBody.items
                    tournamentForLoadId = tournaments.getOrNull(3)?.tournamentId

                    val resultList = mutableListOf<TheLastSportsEvents>()

                    for(i in tournaments.take(2)) {
                        val secondResponse = networkClient.getSportsEventResultsByTournament(
                            tournamentIds = listOf(i.tournamentId),
                            token = jwt.getJWTToken(true)
                        )
                        if (secondResponse.isSuccessful) {
                            val secondResponseBody = secondResponse.body()!!
                            for(j in secondResponseBody.items) {
                                resultList.add(
                                    createTheLastSportsEvents(i, j)
                                )
                            }
                        }
                    }
                    _matches.update { resultList }
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
        tournamentForLoadId?.let {tournamentForLoadId ->
            if (tournaments.isNotEmpty()) {
                val firstTournament =
                    tournaments.indexOfFirst { it.tournamentId == tournamentForLoadId }
                val newMatches = mutableListOf<TheLastSportsEvents>()
                for (tournament in tournaments.subList(firstTournament, firstTournament + 2)) {
                    val sportsEventsResults = networkClient.getSportsEventResultsByTournament(
                        tournamentIds = listOf(tournament.tournamentId),
                        token = jwt.getJWTToken(true)
                    )
                    if (sportsEventsResults.isSuccessful) {
                        for (match in sportsEventsResults.body()!!.items) {
                            newMatches.add(createTheLastSportsEvents(tournament, match))
                        }
                    }
                }
                _matches.update { it + newMatches }
                this.tournamentForLoadId = tournaments.getOrNull(tournamentForLoadId + 3)?.tournamentId
            }
        }
    }

    fun createTheLastSportsEvents(tournament: SportTournament, match: SportsEventsResults): TheLastSportsEvents {
        return TheLastSportsEvents(
            tournamentId = tournament.tournamentId,
            tournamentNameLocalization = tournament.tournamentNameLocalization,
            opponent1NameLocalization = match.opponent1NameLocalization,
            opponent2NameLocalization = match.opponent2NameLocalization,
            imageOpponent1 = match.imageOpponent1,
            imageOpponent2 = match.imageOpponent2,
            score = match.score,
            startDate = match.startDate
        )
    }
}