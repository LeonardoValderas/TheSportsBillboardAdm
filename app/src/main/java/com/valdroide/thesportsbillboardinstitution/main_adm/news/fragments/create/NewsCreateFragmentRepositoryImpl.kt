package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.events.NewsCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class NewsCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : NewsCreateFragmentRepository {

    private var news: News? = null
    private var response: WSResponse? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null

    override fun getNews(context: Context, id_news: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getNew(id_news)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    news = result.new
                                    post(NewsCreateFragmentEvent.GETNEWS, news!!)
                                } else {
                                    post(NewsCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun saveNews(context: Context, news: News) {
        try {
            apiService.saveNews(news.TITLE, news.DESCRIPTION, news.ID_SUB_MENU,
                    news.URL_IMAGE, news.NAME_IMAGE, news.ENCODE, 1,
                    Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(NewsCreateFragmentEvent.SAVENEWS)
                                } else {
                                    post(NewsCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun updateNews(context: Context, news: News) {
        try {
            apiService.updateNews(news.ID_NEWS_KEY, news.TITLE, news.DESCRIPTION, news.ID_SUB_MENU, news.URL_IMAGE, news.NAME_IMAGE, news.ENCODE, news.BEFORE,
                    news.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(NewsCreateFragmentEvent.UPDATENEWS)
                                } else {
                                    post(NewsCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun getSubMenus(context: Context) {
        try {
            apiService.getPositionsSubMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    subMenus = result.submunes
                                    post(NewsCreateFragmentEvent.GETSUBMENU, subMenus)
                                } else {
                                    post(NewsCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(NewsCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NewsCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NewsCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, null, error)
    }

    fun post(types: Int, news: News) {
        post(types, news, null, null)
    }

    fun post(types: Int, submenus: MutableList<SubMenuDrawer>?) {
        post(types, null, submenus, null)
    }

    fun post(types: Int, news: News?, submenus: MutableList<SubMenuDrawer>?, error: String?) {
        val event = NewsCreateFragmentEvent()
        event.type = types
        event.news = news
        event.subMenuDrawers = submenus
        event.error = error
        eventBus.post(event)
    }
}