package com.example.marketing_api_v1_7.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.marketing_api_v1_7.models.preMatch.PreMatchSportsEvents
import com.example.marketing_api_v1_7.network.RetrofitClientBuilder
import com.example.marketing_api_v1_7.repository.PreMatchRepository
import kotlinx.coroutines.launch

class PreMatchViewModel: MatchesViewModel<PreMatchSportsEvents>() {
    override val repository = PreMatchRepository()
}