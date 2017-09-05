package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news

import android.content.Context

interface NewsFragmentInteractor {
    fun getNews(context: Context, id_submenu: Int)
}