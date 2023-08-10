package com.example.marketing_api_v1_7.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing_api_v1_7.repository.MatchRepository
import com.example.marketing_api_v1_7.util.SportIds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MatchesViewModel<T>: ViewModel() {
    private var _selectedSport: MutableStateFlow<Int> = MutableStateFlow(SportIds.SELECTED_FOOTBALL)
    private var _matchesLoaded: MutableStateFlow<Boolean> = MutableStateFlow(true)

    abstract val repository: MatchRepository<T>

    val selectedSport: StateFlow<Int>
        get() = _selectedSport
    val matchesLoaded: StateFlow<Boolean>
        get() = _matchesLoaded
    val matches: StateFlow<List<T>>
        get() = repository.matches

    fun setSelectedSport(value: Int) {
        _selectedSport.update { value }
    }

    fun loadData(id: Int) {
        if(id == repository.currentSportId && matches.value.isNotEmpty() || !_matchesLoaded.value) {
            return
        }
        viewModelScope.launch {
            _matchesLoaded.update { false }
            repository.loadFirst100Matches(id)
            _matchesLoaded.update { true }
        }
    }

    fun loadMoreData() {
        if(!_matchesLoaded.value) return
        viewModelScope.launch {
            _matchesLoaded.update { false }
            repository.loadNewMatches()
            _matchesLoaded.update { true }
        }
    }
}