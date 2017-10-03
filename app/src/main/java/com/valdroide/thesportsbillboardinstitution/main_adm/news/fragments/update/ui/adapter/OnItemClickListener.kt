package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter

import com.valdroide.thesportsbillboardinstitution.model.entities.News

interface OnItemClickListener {
    fun onClickActiveNews(position: Int, news: News)
    fun onClickUnActiveNews(position: Int, news: News)
    fun onClickUpdateNews(position: Int, news: News)
    fun onClickDeleteNews(position: Int, news: News)
}