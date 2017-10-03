package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface NewsUpdateFragmentInteractor {
    fun getNews(context: Context)
    fun deleteNews(context: Context, news: News)
    fun activeUnActiveNews(context: Context, news: News)
}