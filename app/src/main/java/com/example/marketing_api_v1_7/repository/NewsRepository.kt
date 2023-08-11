package com.example.marketing_api_v1_7.repository

import android.util.Log
import com.example.marketing_api_v1_7.models.news.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jsoup.Jsoup

class NewsRepository {
    private var _news: MutableStateFlow<List<News>> = MutableStateFlow(emptyList())
    val news: StateFlow<List<News>>
        get() = _news
    fun loadNews() {
        val links = getBlogLinks()
        val parsedLinks = mutableListOf<Pair<String, String>>()
        for(link in links) {
            parsedLinks.add(parseBlog(link.first) to link.second)
        }
        _news.update {
            parsedLinks.map {pl ->
                News(pl.first, pl.second)
            }
        }
        Log.i("loadNews", "_news size: ${_news.value.size};\nparsedLinks size: ${parsedLinks.size}")
    }

    private fun parseBlog(link : String): String {
        val doc = Jsoup.connect(link).get()

        //val header = doc.select("h1").text()
        val blogText = doc.getElementsByClass("blog_content")

        return blogText.text()
    }

    private fun getBlogLinks() : List<Pair<String, String>> {
        val link = "https://spinbetter1.online"

        val doc = Jsoup.connect("$link/ru/blog/football").get()

        val blogLinks  = doc.select("div.b-news-con__item > a[href]").map { el ->
            val imgUrl = el.select("div.b-news__back").attr("style")
                .substringAfter("url(")
                .substringBefore(")")
            val titleLink = link + el.attr("href")
            Pair(titleLink, imgUrl)
        }

        return blogLinks
    }
}