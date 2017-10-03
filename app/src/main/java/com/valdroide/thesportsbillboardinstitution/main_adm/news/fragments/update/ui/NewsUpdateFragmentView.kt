package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsUpdateFragmentView {
    fun setAllNews(news: MutableList<News>)
    fun setError(error: String)
    fun updateNewsSuccess()
    fun deleteNewsSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}