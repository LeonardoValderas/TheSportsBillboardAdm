package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.helper.ImageHelper

class NewsFragmentAdapter(context: Context) : GenericRecyclerAdapter<News>(context, null){


    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val view: View? = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false)
        return view!!
    }

    override fun bindView(new: News, itemView: ViewHolder) {

        with(new) {
            ImageHelper.setPicasso(context!!, URL_IMAGE, R.mipmap.ic_launcher, itemView.getView(R.id.iv_news) as ImageView)
            (itemView.getView(R.id.tv_title_news) as TextView).text = TITLE
            (itemView.getView(R.id.expand_text_view) as ExpandableTextView).text = DESCRIPTION
            (itemView.getView(R.id.tv_date_news) as TextView).text = DATE_NEWS
        }
    }
}

