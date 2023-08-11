package com.example.marketing_api_v1_7.forRecyclerView.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marketing_api_v1_7.R
import com.example.marketing_api_v1_7.models.news.News
import com.squareup.picasso.Picasso

class NewsListAdapter : ListAdapter<News, NewsListAdapter.NewsViewHolder>(
    object: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }

    }
) {

    lateinit var readNewsCallback: (News) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = currentList[position]



        holder.run {
            newsText.text = itemView.resources.getString(R.string.text_with_ellipsis, item.text.substring(0, 100))
            Picasso.get()
                .load(item.image)
                .into(newsImage)
            itemView.setOnClickListener {
                readNewsCallback(item)
            }

        }
    }

    class NewsViewHolder(view: View): ViewHolder(view) {
        val newsText: TextView = view.findViewById(R.id.newsText)
        val newsImage: ImageView = view.findViewById(R.id.newsImage)
    }

}