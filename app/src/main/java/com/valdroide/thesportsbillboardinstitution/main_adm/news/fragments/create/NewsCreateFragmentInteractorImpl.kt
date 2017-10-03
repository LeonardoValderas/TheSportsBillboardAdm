package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.News

class NewsCreateFragmentInteractorImpl(val repository: NewsCreateFragmentRepository) : NewsCreateFragmentInteractor {

    override fun getNews(context: Context, id_news: Int) {
        repository.getNews(context, id_news)
    }

    override fun saveNews(context: Context, news: News) {
        repository.saveNews(context, news)
    }

    override fun updateNews(context: Context, news: News) {
        repository.updateNews(context, news)
    }

    override fun getSubMenus(context: Context) {
        repository.getSubMenus(context)
    }
}