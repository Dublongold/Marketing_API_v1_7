package com.example.marketing_api_v1_7.models.theLast

data class TheLastSportsEvents (
    val tournamentId: Int,
    val tournamentNameLocalization: String,
    val opponent1NameLocalization: String,
    val opponent2NameLocalization: String,
    val imageOpponent1: List<String>,
    val imageOpponent2: List<String>,
    val score: String,
    val startDate: Int
)