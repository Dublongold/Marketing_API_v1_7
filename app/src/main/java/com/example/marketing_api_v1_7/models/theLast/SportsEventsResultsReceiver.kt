package com.example.marketing_api_v1_7.models.theLast

import kotlinx.serialization.Serializable

@Serializable
data class SportsEventsResultsReceiver(
    val count: Int,
    val items: List<SportsEventsResults>
)