package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.events.NewsCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): NewsCreateFragmentView
    fun setViewPresenter(view: NewsCreateFragmentView)
    fun getNews(context: Context, id_news: Int)
    fun saveNews(context: Context, news: News)
    fun updateNews(context: Context, news: News)
    fun getSubMenus(context: Context)
    fun onEventMainThread(event: NewsCreateFragmentEvent)
}