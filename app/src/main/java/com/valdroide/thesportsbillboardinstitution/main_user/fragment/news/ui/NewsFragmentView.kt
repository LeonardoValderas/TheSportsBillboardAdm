package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsFragmentView {
    fun setNews(news: MutableList<News>)
    fun setError(error: String)
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}