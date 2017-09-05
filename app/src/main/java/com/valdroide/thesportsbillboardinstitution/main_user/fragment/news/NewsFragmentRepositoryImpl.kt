package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.events.NewsFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class NewsFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : NewsFragmentRepository {
    private var news: MutableList<News>? = null
    private var response: WSResponse? = null

    override fun getNews(context: Context, id_submenu: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getNews(id_submenu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    news = result.news
                                    post(NewsFragmentEvent.NEWS, news!!)

                                } else {
                                    post(NewsFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, news: MutableList<News>) {
        post(types, news, null)
    }

    fun post(types: Int, news: MutableList<News>?, error: String?) {
        val event = NewsFragmentEvent()
        event.type = types
        event.news = news
        event.error = error
        eventBus.post(event)
    }
}