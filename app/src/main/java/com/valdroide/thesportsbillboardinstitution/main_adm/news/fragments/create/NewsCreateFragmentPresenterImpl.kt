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
        showProgressAndSetVisivility()
        interactor.getNews(context, id_news)
    }

    override fun saveNews(context: Context, news: News) {
        showProgressAndSetVisivility()
        interactor.saveNews(context, news)
    }

    override fun updateNews(context: Context, news: News) {
        showProgressAndSetVisivility()
        interactor.updateNews(context, news)
    }

    override fun getSubMenus(context: Context) {
        showProgressAndSetVisivility()
        interactor.getSubMenus(context)
    }

    private fun showProgressAndSetVisivility(){
        view.showProgressDialog()
        view.setVisibilityViews(View.INVISIBLE)
    }
    private fun hideProgressAndSetVisivility(){
        view.hideProgressDialog()
        view.setVisibilityViews(View.VISIBLE)
    }


    @Subscribe
    override fun onEventMainThread(event: NewsCreateFragmentEvent) {
        when (event.type) {
            NewsCreateFragmentEvent.GETNEWS -> {
                view.setNewsUpdate(event.news!!)
                view.fillViewUpdate()
                hideProgressAndSetVisivility()
            }
            NewsCreateFragmentEvent.SAVENEWS -> {
                hideProgressAndSetVisivility()
                view.saveSuccess()
                view.cleanViews()
            }
            NewsCreateFragmentEvent.UPDATENEWS -> {
                hideProgressAndSetVisivility()
                view.editSuccess()
                view.cleanViews()
            }
            NewsCreateFragmentEvent.GETSUBMENU -> {
                view.setSubMenus(event.subMenuDrawers!!)
            }
            NewsCreateFragmentEvent.ERROR -> {
                hideProgressAndSetVisivility()
                view.setError(event.error!!)
            }
        }
    }
}