package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.News

class NewsUpdateFragmentInteractorImpl(val repository: NewsUpdateFragmentRepository) : NewsUpdateFragmentInteractor {


    override fun getNews(context: Context) {
        repository.getNews(context)
    }

    override fun activeUnActiveNews(context: Context, news: News) {
        repository.activeUnActiveNews(context, news)
    }

    override fun deleteNews(context: Context, news: News) {
        repository.deleteNews(context, news)
    }
}