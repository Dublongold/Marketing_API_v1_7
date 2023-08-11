package com.example.marketing_api_v1_7.models.news

import kotlinx.serialization.Serializable

@Serializable
data class News (
    val text: String,
    val image: String
)