package com.example.marketing_api_v1_7.viewModels

import com.example.marketing_api_v1_7.models.live.LiveSportsEvents
import com.example.marketing_api_v1_7.repository.LiveRepository

class LiveViewModel: MatchesViewModel<LiveSportsEvents>() {
    override val repository = LiveRepository()
}