package com.example.marketing_api_v1_7.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing_api_v1_7.network.RetrofitClientBuilder
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    fun testFun() {
        viewModelScope.launch {
            Log.i("Test1", RetrofitClientBuilder().newsClient.getFootball().string())
        }
    }
}