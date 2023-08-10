package com.example.marketing_api_v1_7.models.live

import com.example.marketing_api_v1_7.models.live.LiveSportsEvents
import kotlinx.serialization.Serializable

@Serializable
data class LiveSportsEventsReceiver (
    val count: Int,
    val items: List<LiveSportsEvents>?
)