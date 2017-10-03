package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.events.NewsUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsUpdateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): NewsUpdateFragmentView
    fun setViewPresenter(view: NewsUpdateFragmentView)
    fun getNews(context: Context)
    fun activeUnActiveNews(context: Context, news: News)
    fun deleteNews(context: Context, news: News)
    fun onEventMainThread(event: NewsUpdateFragmentEvent)
}