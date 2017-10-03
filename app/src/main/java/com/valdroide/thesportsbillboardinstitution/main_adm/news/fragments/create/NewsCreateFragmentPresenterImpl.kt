package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.events.NewsCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import org.greenrobot.eventbus.Subscribe

class NewsCreateFragmentPresenterImpl(var view: NewsCreateFragmentView, val event: EventBus, val interactor: NewsCreateFragmentInteractor) : NewsCreateFragmentPresenter {

    override fun getViewPresenter(): NewsCreateFragmentView = view

    override fun setViewPresenter(view: NewsCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getNews(context: Context, id_news: Int) {
        interactor.getNews(context, id_news)
    }

    override fun saveNews(context: Context, news: News) {
        interactor.saveNews(context, news)
    }

    override fun updateNews(context: Context, news: News) {
        interactor.updateNews(context, news)
    }

    override fun getSubMenus(context: Context) {
        interactor.getSubMenus(context)
    }

    @Subscribe
    override fun onEventMainThread(event: NewsCreateFragmentEvent) {
        when (event.type) {
            NewsCreateFragmentEvent.GETNEWS -> {
                view.setNewsUpdate(event.news!!)
                view.fillViewUpdate()
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
            }
            NewsCreateFragmentEvent.SAVENEWS -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.saveSuccess()
                view.cleanViews()
            }
            NewsCreateFragmentEvent.UPDATENEWS -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.editSuccess()
                view.cleanViews()
            }
            NewsCreateFragmentEvent.GETSUBMENU -> {
                view.setVisibilityViews(View.VISIBLE)
                view.setSubMenus(event.subMenuDrawers!!)
                view.hideProgressDialog()
            }
            NewsCreateFragmentEvent.ERROR -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.setError(event.error!!)
            }
        }
    }
}