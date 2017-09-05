package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news

import android.content.Context

class NewsFragmentInteractorImpl(val repository: NewsFragmentRepository) : NewsFragmentInteractor {

    override fun getNews(context: Context, id_submenu: Int) {
        repository.getNews(context, id_submenu)
    }
}