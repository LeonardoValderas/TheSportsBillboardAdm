package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsCreateFragmentRepository {
    fun getNews(context: Context, id_news: Int)
    fun saveNews(context: Context, news: News)
    fun updateNews(context: Context, news: News)
    fun getSubMenus(context: Context)
}