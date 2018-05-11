package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.events.NewsFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragmentView
import org.greenrobot.eventbus.Subscribe

class NewsFragmentPresenterImpl(var view: NewsFragmentView, val event: EventBus, val interactor: NewsFragmentInteractor) : NewsFragmentPresenter {


    override fun getNewsView(): NewsFragmentView = view

    override fun setNewsView(view: NewsFragmentView) {
       this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getNews(context: Context, id_submenu: Int) {
        interactor.getNews(context, id_submenu)
    }

    @Subscribe
    override fun onEventMainThread(event: NewsFragmentEvent) {
        if (view != null) {
            when (event.type) {
                NewsFragmentEvent.NEWS -> {
                    view.hideSwipeRefreshLayout()
                    view.setNews(event.news!!)
                }
                NewsFragmentEvent.ERROR -> {
                    view.hideSwipeRefreshLayout()
                    view.setError(event.error!!)
                }
            }
        }
    }
}