package com.example.marketing_api_v1_7.views

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.forRecyclerView.adapters.PreMatchListAdapter
import com.example.marketing_api_v1_7.viewModels.PreMatchViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PreMatchFragment: MatchesFragment() {
    override val viewModel: PreMatchViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = PreMatchListAdapter()
        view.findViewById<RecyclerView>(R.id.matchList).apply {
            this.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matches.collect {
                    adapter.submitList(it)
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setBottomButtonsListeners() {
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
            view.findViewById<LinearLayout>(R.id.goToNewsContainer).setOnClickListener {
                findNavController().apply {
                    popBackStack()
                    navigate(R.id.goToNewsPage)
                }
            }
        }
    }

    override fun setPageData() {
        view?.let { view ->
            view.findViewById<TextView>(R.id.titleOfPage).text = getString(R.string.pre_match)
            view.findViewById<ImageView>(R.id.goToPreMatchImage).foreground = Color.toArgb(0x00000000).toDrawable()
            view.findViewById<TextView>(R.id.goToPreMatchText).foreground = Color.toArgb(0x00000000).toDrawable()
        }
    }
}