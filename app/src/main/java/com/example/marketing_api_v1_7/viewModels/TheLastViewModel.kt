package com.example.marketing_api_v1_7.viewModels

import com.example.marketing_api_v1_7.models.live.LiveSportsEvents
import com.example.marketing_api_v1_7.models.theLast.TheLastSportsEvents
import com.example.marketing_api_v1_7.repository.LiveRepository
import com.example.marketing_api_v1_7.repository.TheLastRepository

class TheLastViewModel: MatchesViewModel<TheLastSportsEvents>() {
    override val repository = TheLastRepository()
}