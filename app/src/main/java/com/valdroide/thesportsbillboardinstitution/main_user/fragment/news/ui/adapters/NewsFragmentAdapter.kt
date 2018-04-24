package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
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
            (itemView.getView(R.id.expand_text_view) as TextView).text = DESCRIPTION
            (itemView.getView(R.id.tv_date_news) as TextView).text = DATE_NEWS
        }
        }

    }
    /*
      private val fragment: Fragment


    init {
        this.fragment = fragment as NewsFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindData(news!![position], position, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(new: News, position: Int, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(new) {
                ImageHelper.setPicasso(fragment.activity, URL_IMAGE, R.mipmap.ic_launcher, itemView.iv_news)
                itemView.tv_title_news.text = TITLE
                itemView.expand_text_view.text = DESCRIPTION
                itemView.tv_date_news.text = DATE_NEWS
            }
        }
    }

    override fun getItemCount(): Int = news!!.size

    fun setNews(news: MutableList<News>) {
        this.news = news
        notifyDataSetChanged()
    }
    */
