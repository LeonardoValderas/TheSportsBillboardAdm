package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di

import android.content.Context
import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.*
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters.NewsFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsFragmentModule(val view: NewsFragmentView, val context: Context) {

    @Provides
    @Singleton
    fun provideNewsFragmentView(): NewsFragmentView = this.view

    @Provides
    @Singleton
    fun provideNewsFragmentPresenter(view: NewsFragmentView, event: EventBus, interactor: NewsFragmentInteractor): NewsFragmentPresenter
            = NewsFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideNewsFragmentInteractor(repository: NewsFragmentRepository): NewsFragmentInteractor
            = NewsFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideNewsFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): NewsFragmentRepository
            = NewsFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideNewsFragmentAdapter(context: Context): NewsFragmentAdapter
            = NewsFragmentAdapter(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
/*
    @Provides
    @Singleton
    fun provideLayoutResourceId(): Int = R.layout.cell_news

    @Provides
    @Singleton
    fun provideListNews(): MutableList<News> = arrayListOf()

¨*/
}