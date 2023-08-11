package com.example.marketing_api_v1_7.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing_api_v1_7.models.news.News
import com.example.marketing_api_v1_7.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val repository = NewsRepository()
    private var _newsLoaded = MutableStateFlow(false)


    val news: StateFlow<List<News>>
        get() = repository.news
    val newsLoaded: StateFlow<Boolean>
        get() = _newsLoaded

    fun loadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _newsLoaded.update { false }
            repository.loadNews()
            _newsLoaded.update { true }
        }
    }
}