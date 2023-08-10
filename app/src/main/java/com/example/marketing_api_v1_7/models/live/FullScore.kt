package com.example.marketing_api_v1_7.models.live

import kotlinx.serialization.Serializable

@Serializable
data class FullScore(
    val sc1: Int,
    val sc2: Int
)