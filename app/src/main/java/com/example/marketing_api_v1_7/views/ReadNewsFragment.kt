package com.example.marketing_api_v1_7.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.marketing_api_v1_7.R
import com.squareup.picasso.Picasso

class ReadNewsFragment: DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.readNewsStyle
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.read_news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null) {
            view.findViewById<TextView>(R.id.newsText).text = arguments?.getString("newsText")
            Picasso.get()
                .load(arguments?.getString("newsImage"))
                .into(view.findViewById<ImageView>(R.id.newsImage))
        }
        view.findViewById<ImageButton>(R.id.goBackToNews).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}