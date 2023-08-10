package com.example.marketing_api_v1_7.repository

import com.example.marketing_api_v1_7.util.SportIds
import kotlinx.coroutines.flow.StateFlow

interface MatchRepository<T> {
    val matches: StateFlow<List<T>>
    var currentSportId: Int
    suspend fun loadFirst100Matches(sportId: Int = SportIds.SELECTED_FOOTBALL)
    suspend fun loadNewMatches()
}