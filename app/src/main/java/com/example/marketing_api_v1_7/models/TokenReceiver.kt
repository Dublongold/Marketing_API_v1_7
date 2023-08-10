package com.example.marketing_api_v1_7.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenReceiver (
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int
)