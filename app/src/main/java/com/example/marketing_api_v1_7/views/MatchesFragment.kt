package com.example.marketing_api_v1_7.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.util.SportIds
import com.example.marketing_api_v1_7.viewModels.MatchesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class MatchesFragment: Fragment() {
    abstract val viewModel: MatchesViewModel<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonsListeners()
        watchSelectedSport()
        setPageData()
        setBottomButtonsListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matchesLoaded.collect {
                    view.findViewById<ProgressBar>(R.id.loadingData)
                        .visibility = if(it) View.GONE else View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.goToAnnoyingBannerDestination)
        }
    }

    abstract fun setBottomButtonsListeners()

    private fun setButtonsListeners() {
        view?.let { view ->
            /** Find sport button and set on click listener */
            val fsbasocl = { buttonId: Int, sportId: Int ->
                view.findViewById<TextView>(buttonId).setOnClickListener {
                    if (viewModel.matchesLoaded.value) {
                        viewModel.setSelectedSport(sportId)
                    }
                }
            }
            fsbasocl(R.id.football_button, SportIds.SELECTED_FOOTBALL)
            fsbasocl(R.id.hockey_button, SportIds.SELECTED_HOCKEY)
            fsbasocl(R.id.basketball_button, SportIds.SELECTED_BASKETBALL)
            fsbasocl(R.id.tennis_button, SportIds.SELECTED_TENNIS)
        }
    }

    private fun watchSelectedSport() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedSport.collect {
                    view?.let { view ->
                        /** Find Text View By Id*/
                        val ftvby: (Int) -> TextView = {view.findViewById(it)}
                        listOf(ftvby(R.id.football_button),
                            ftvby(R.id.hockey_button),
                            ftvby(R.id.basketball_button),
                            ftvby(R.id.tennis_button)).forEachIndexed { index, textView ->
                                if(it != index + 1) {
                                    textView.setBackgroundColor(0x00000000)
                                }
                                else {
                                    textView.setBackgroundResource(R.drawable.sport_active_background)
                                }
                            }
                        viewModel.loadData(it)
                    }
                }
            }
        }
    }

    abstract fun setPageData()
}