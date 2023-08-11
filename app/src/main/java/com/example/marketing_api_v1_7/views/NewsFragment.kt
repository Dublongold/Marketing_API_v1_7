package com.example.marketing_api_v1_7.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.forRecyclerView.adapters.NewsListAdapter
import com.example.marketing_api_v1_7.viewModels.NewsViewModel
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsLoaded.collect {
                    view.findViewById<ProgressBar>(R.id.loadingData).visibility =
                        if(it) View.GONE else View.VISIBLE
                }
            }
        }

        val adapter = NewsListAdapter()
        adapter.readNewsCallback = {
            findNavController().navigate(
                R.id.goToReadNewsPage,
                bundleOf(
                    "newsText" to it.text,
                    "newsImage" to it.image,
                    )
            )
        }

        view.findViewById<RecyclerView>(R.id.newsList).apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager( context, 2).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = if (position < 3) 2 else 1
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.news.collect {
                    adapter.submitList(it)
                }
            }
        }

        viewModel.loadNews()

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
        }
    }
}