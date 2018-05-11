package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.events.NewsFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragmentView

interface NewsFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getNewsView(): NewsFragmentView
    fun setNewsView(view: NewsFragmentView)
    fun getNews(context: Context, id_submenu: Int)
    fun onEventMainThread(event: NewsFragmentEvent)
}