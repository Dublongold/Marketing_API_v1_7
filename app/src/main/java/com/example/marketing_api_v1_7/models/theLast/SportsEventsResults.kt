package com.example.marketing_api_v1_7.models.theLast

import kotlinx.serialization.Serializable

@Serializable
data class SportsEventsResults(
    val sportEventId: Int,
    val opponent1NameLocalization: String,
    val opponent2NameLocalization: String,
    val imageOpponent1: List<String>,
    val imageOpponent2: List<String>,
    val score: String,
    val startDate: Int
)