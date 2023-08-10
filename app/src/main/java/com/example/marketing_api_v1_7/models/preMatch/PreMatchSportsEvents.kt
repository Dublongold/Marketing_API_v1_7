package com.example.marketing_api_v1_7.models.preMatch

import kotlinx.serialization.Serializable

@Serializable
data class PreMatchSportsEvents(
    val tournamentNameLocalization: String,
    val opponent1NameLocalization: String,
    val opponent2NameLocalization: String,
    val imageOpponent1: List<String>,
    val imageOpponent2: List<String>,
    val startDate: Int
)