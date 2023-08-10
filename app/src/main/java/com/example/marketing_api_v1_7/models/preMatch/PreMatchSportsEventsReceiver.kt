package com.example.marketing_api_v1_7.models.preMatch

import com.example.marketing_api_v1_7.models.preMatch.PreMatchSportsEvents
import kotlinx.serialization.Serializable

@Serializable
data class PreMatchSportsEventsReceiver (
    val count: Int,
    val items: List<PreMatchSportsEvents>?
)