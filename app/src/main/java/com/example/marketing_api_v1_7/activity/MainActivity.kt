package com.example.marketing_api_v1_7.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.network.JWTToken
import com.example.marketing_api_v1_7.network.RetrofitClientBuilder
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.example.marketing_api_v1_7.viewModels.*
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainActivity : AppCompatActivity() {
    private val jwt = JWTToken()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            jwt.loadJWTToken()
        }

        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules (module {
                viewModelOf(::PreMatchViewModel)
                viewModelOf(::LiveViewModel)
                viewModelOf(::TheLastViewModel)
                viewModelOf(::NewsViewModel)
                single { RetrofitClientBuilder().client }
                single { jwt }
            })
        }
    }
}