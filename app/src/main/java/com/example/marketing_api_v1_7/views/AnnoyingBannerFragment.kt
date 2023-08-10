package com.example.marketing_api_v1_7.views

import android.app.Activity
import android.app.Notification.Action
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marketing_api_v1_7.R

class AnnoyingBannerFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.annoyingBannerStyle
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.annoying_banner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.closeAnnoyingBannerButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ&amp;ab_channel=RickAstley")))
            findNavController().popBackStack()
        }

    }
}