package com.example.marketing_api_v1_7.models.live

import com.example.marketing_api_v1_7.models.live.FullScore
import kotlinx.serialization.Serializable

@Serializable
data class LiveSportsEvents(
    val tournamentNameLocalization: String,
    val opponent1NameLocalization: String,
    val opponent2NameLocalization: String,
    val imageOpponent1: List<String>,
    val imageOpponent2: List<String>,
    val startDate: Int,
    /*
    0 – match in progress;
    1 – match finished;
    2 – halftime;
    4 – match canceled;
    8 – opponent 1 takes a penalty;
    16 – opponent 2 takes a penalty
    */
    val gameStatus: Int,
    val fullScore: FullScore
)