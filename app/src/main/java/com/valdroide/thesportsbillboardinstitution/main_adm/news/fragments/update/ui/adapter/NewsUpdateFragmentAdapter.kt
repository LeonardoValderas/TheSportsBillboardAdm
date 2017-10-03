package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.player_item.view.*

class NewsUpdateFragmentAdapter(private var news: MutableList<News>?, private var listener: OnItemClickListener,
                                fragment: Fragment) : RecyclerView.Adapter<NewsUpdateFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as NewsUpdateFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(news!!.get(position), position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(news: News, position: Int, listener: OnItemClickListener, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(news) {
                itemView.textViewName.text = TITLE
                Utils.setPicasso(fragment.activity, URL_IMAGE, R.mipmap.ic_launcher, itemView.imageViewPlayer)
                itemView.textViewPosition.text = DESCRIPTION
                itemView.textViewMenuSubMenu.visibility = View.VISIBLE
                val conca = MENU + " - " + SUBMENU
                itemView.textViewMenuSubMenu.text = conca
                itemView.textViewActive.visibility = View.VISIBLE
                itemView.textViewActive.text = if (IS_ACTIVE == 0) "Inactivo" else "Activo"
            }
            setOnItemClickListener(listener, position, news, fragment)
        }

        fun setOnItemClickListener(listener: OnItemClickListener, position: Int, news: News, fragment: Fragment) {
            itemView.setOnClickListener {
                showPopupMenu(itemView.conteiner, fragment.activity, position, news, listener);
            }
        }

        private fun showPopupMenu(view: View, context: Context, position: Int, news: News, listener: OnItemClickListener) {
            val popup = PopupMenu(context, view, Gravity.END)
            if (news.IS_ACTIVE == 0)
                popup.getMenu().add(Menu.NONE, 1, 1, "Activar Noticia")
            else
                popup.getMenu().add(Menu.NONE, 2, 2, "Desactivar Noticia")
            popup.getMenu().add(Menu.NONE, 3, 3, "Editar Noticia")
            popup.getMenu().add(Menu.NONE, 4, 4, "Eliminar Noticia")
            popup.setOnMenuItemClickListener(MenuItemClickListener(position, news, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var position: Int, internal var news: News, internal var listener: OnItemClickListener) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    1 -> {
                        news.IS_ACTIVE = 1
                        listener.onClickActiveNews(position, news)
                        return true
                    }
                    2 -> {
                        news.IS_ACTIVE = 0
                        listener.onClickUnActiveNews(position, news)
                        return true
                    }
                    3 -> {
                        listener.onClickUpdateNews(position, news)
                        return true
                    }
                    4 -> {
                        listener.onClickDeleteNews(position, news)
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int =
            news!!.size

    fun setNews(news: MutableList<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    fun deleteNews(position: Int) {
        news!!.removeAt(position)
        notifyDataSetChanged()
    }
}