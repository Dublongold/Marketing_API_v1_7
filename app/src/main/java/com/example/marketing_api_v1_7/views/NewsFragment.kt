package com.example.marketing_api_v1_7.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.forRecyclerView.adapters.PreMatchListAdapter
import com.example.marketing_api_v1_7.viewModels.NewsViewModel
import com.example.marketing_api_v1_7.viewModels.PreMatchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NewsFragment: Fragment() {
    private val viewModel: NewsViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomButtonsListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.goToAnnoyingBannerDestination)
        }
    }

    private fun setBottomButtonsListeners() {
        view?.let { view ->
            view.findViewById<LinearLayout>(R.id.goToLiveContainer).setOnClickListener {
                findNavController().apply {
                    popBackStack()
                    navigate(R.id.goToLivePage)
                }
            }
            view.findViewById<LinearLayout>(R.id.goToTheLastContainer).setOnClickListener {
                findNavController().apply {
                    popBackStack()
                    navigate(R.id.goToTheLastPage)
                }
            }
            view.findViewById<LinearLayout>(R.id.goToPreMatchContainer).setOnClickListener {
                findNavController().apply {
                    popBackStack()
                    navigate(R.id.goToPreMatchPage)
                }
            }
            view.findViewById<LinearLayout>(R.id.goToNewsContainer).setOnClickListener {
                viewModel.testFun()
            }
        }
    }
}