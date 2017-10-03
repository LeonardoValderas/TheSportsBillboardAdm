package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.events.NewsUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import org.greenrobot.eventbus.Subscribe

class NewsUpdateFragmentPresenterImpl(var view: NewsUpdateFragmentView, val event: EventBus, val interactor: NewsUpdateFragmentInteractor) : NewsUpdateFragmentPresenter {

    override fun getViewPresenter(): NewsUpdateFragmentView = view

    override fun setViewPresenter(view: NewsUpdateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getNews(context: Context) {
        interactor.getNews(context)
    }

    override fun activeUnActiveNews(context: Context, news: News) {
        interactor.activeUnActiveNews(context, news)
    }

    override fun deleteNews(context: Context, News: News) {
        interactor.deleteNews(context, News)
    }

    @Subscribe
    override fun onEventMainThread(event: NewsUpdateFragmentEvent) {
        when (event.type) {
            NewsUpdateFragmentEvent.NEWS -> {
                view.hideSwipeRefreshLayout()
                view.setAllNews(event.news!!)
            }
            NewsUpdateFragmentEvent.UPDATE -> {
                view.hideSwipeRefreshLayout()
                view.updateNewsSuccess()
            }
            NewsUpdateFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deleteNewsSuccess()
            }
            NewsUpdateFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}