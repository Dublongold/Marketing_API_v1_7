package com.example.marketing_api_v1_7.viewModels

import androidx.lifecycle.viewModelScope
import com.example.marketing_api_v1_7.models.theLast.TheLastSportsEvents
import com.example.marketing_api_v1_7.repository.TheLastRepository
import kotlinx.coroutines.launch

class TheLastViewModel: MatchesViewModel<TheLastSportsEvents>() {
    override val repository = TheLastRepository()


    fun loadMoreData() {
        if(!matchesLoaded.value) return
        viewModelScope.launch {
            updateMarchesLoaded( false)
            repository.loadNewMatches()
            updateMarchesLoaded( true)
        }
    }
}