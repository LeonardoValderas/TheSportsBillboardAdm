package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.ramotion.foldingcell.FoldingCell
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.utils.helper.ImageHelper


class NewsFragmentAdapter(internal var context: Context,
                          internal var layoutResourceId: Int,
                          internal var news: List<News>) : ArrayAdapter<News>(context, layoutResourceId, news) {

    private val unfoldedIndexes = HashSet<Int>()
    private var defaultRequestBtnClickListener: View.OnClickListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val new: News = news[position]
        var cell = convertView as FoldingCell?
        val viewHolder: ViewHolder

        if (cell == null) {
            viewHolder = ViewHolder()
            val vi = LayoutInflater.from(getContext())
            cell = vi.inflate(R.layout.cell_news, parent, false) as FoldingCell
            // binding view parts to view holder
            viewHolder.textViewTitle = cell.findViewById(R.id.textViewTitle)
            viewHolder.textViewDateTitle = cell.findViewById(R.id.textViewDateTitle)
            viewHolder.textViewTitleContent = cell.findViewById(R.id.textViewTitleContent)
            viewHolder.imageViewNews = cell.findViewById(R.id.imageViewNews)
            viewHolder.textViewDescription = cell.findViewById(R.id.textViewDescription)
            viewHolder.textViewDate = cell.findViewById(R.id.textViewDate)
            cell.tag = viewHolder
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true)
            } else {
                cell.fold(true)
            }
            viewHolder = cell.tag as ViewHolder
        }

        // bind data from selected element to view through view holder
        viewHolder.textViewTitle!!.text = new.TITLE
        viewHolder.textViewDateTitle!!.text = new.DATE_NEWS
        viewHolder.textViewTitleContent!!.text = new.TITLE
        ImageHelper.setPicassoWithOutRoundedConrners(context, new.URL_IMAGE, R.mipmap.ic_launcher, viewHolder.imageViewNews!!)
        viewHolder.textViewDescription!!.text = new.DESCRIPTION
        viewHolder.textViewDate!!.text = new.DATE_NEWS

        return cell;
    }

    fun registerToggle(position: Int) {
        if (unfoldedIndexes.contains(position))
            registerFold(position)
        else
            registerUnfold(position)
    }

    fun registerFold(position: Int) {
        unfoldedIndexes.remove(position)
    }

    fun registerUnfold(position: Int) {
        unfoldedIndexes.add(position)
    }

//    fun getDefaultRequestBtnClickListener(): View.OnClickListener? = defaultRequestBtnClickListener
//
//
//    fun setDefaultRequestBtnClickListener(defaultRequestBtnClickListener: View.OnClickListener) {
//        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener
//    }

    private class ViewHolder {
        internal var textViewTitle: TextView? = null
        internal var textViewDateTitle: TextView? = null
        internal var textViewTitleContent: TextView? = null
        internal var textViewDescription: TextView? = null
        internal var imageViewNews: ImageView? = null
        internal var textViewDate: TextView? = null
    }

    override fun getCount(): Int = news.size

    fun setNews(news: MutableList<News>) {
        this.news = news
        notifyDataSetChanged()
    }
}