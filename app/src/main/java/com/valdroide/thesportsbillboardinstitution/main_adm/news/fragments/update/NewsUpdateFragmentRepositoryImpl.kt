package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.events.NewsUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class NewsUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : NewsUpdateFragmentRepository {

    private var news: MutableList<News>? = null
    private var response: WSResponse? = null

    override fun getNews(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getNewsList()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    news = result.news
                                    post(NewsUpdateFragmentEvent.NEWS, news!!)
                                } else {
                                    post(NewsUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsUpdateFragmentEvent.ERROR, e.message)
        }
    }

    override fun activeUnActiveNews(context: Context, news: News) {
        try {
            apiService.activeOrUnActiveNews(news.ID_NEWS_KEY, news.IS_ACTIVE, 1, getOficialDate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(NewsUpdateFragmentEvent.UPDATE)
                                } else {
                                    post(NewsUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsUpdateFragmentEvent.ERROR, e.message)
        }
    }

    private fun getOficialDate(): String = DateTimeHelper.getFechaOficialSeparate()

    override fun deleteNews(context: Context, news: News) {
        try {
            apiService.deleteNews(news.ID_NEWS_KEY, 1, getOficialDate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(NewsUpdateFragmentEvent.DELETE)
                                } else {
                                    post(NewsUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsUpdateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, Newss: MutableList<News>) {
        post(types, Newss, null)
    }

    fun post(types: Int, news: MutableList<News>?, error: String?) {
        val event = NewsUpdateFragmentEvent()
        event.type = types
        event.news = news
        event.error = error
        eventBus.post(event)
    }
}