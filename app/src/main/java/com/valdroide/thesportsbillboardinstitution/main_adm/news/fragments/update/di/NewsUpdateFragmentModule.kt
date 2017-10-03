package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.NewsUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsUpdateFragmentModule(val view: NewsUpdateFragmentView, val listener: OnItemClickListener) {

    @Provides
    @Singleton
    fun provideNewsUpdateFragmentView(): NewsUpdateFragmentView = this.view

    @Provides
    @Singleton
    fun provideNewsUpdateFragmentPresenter(view: NewsUpdateFragmentView, event: EventBus, interactor: NewsUpdateFragmentInteractor): NewsUpdateFragmentPresenter
            = NewsUpdateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideNewsUpdateFragmentInteractor(repository: NewsUpdateFragmentRepository): NewsUpdateFragmentInteractor
            = NewsUpdateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideNewsUpdateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): NewsUpdateFragmentRepository
            = NewsUpdateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideNewsUpdateFragmentAdapter(news: MutableList<News>, listener: OnItemClickListener, fragment: Fragment): NewsUpdateFragmentAdapter
            = NewsUpdateFragmentAdapter(news, listener, fragment)

    @Provides
    @Singleton
    fun provideListNews(): MutableList<News> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): OnItemClickListener = listener
}