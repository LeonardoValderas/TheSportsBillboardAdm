package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di

import android.app.Activity
import android.content.Context
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsCreateFragmentModule(val view: NewsCreateFragmentView, val context: Context) {

    @Provides
    @Singleton
    fun provideNewsCreateFragmentView(): NewsCreateFragmentView = this.view

    @Provides
    @Singleton
    fun provideNewsCreateFragmentPresenter(view: NewsCreateFragmentView, event: EventBus, interactor: NewsCreateFragmentInteractor): NewsCreateFragmentPresenter
            = NewsCreateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideNewsCreateFragmentInteractor(repository: NewsCreateFragmentRepository): NewsCreateFragmentInteractor
            = NewsCreateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideNewsCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): NewsCreateFragmentRepository
            = NewsCreateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

 //   @Provides
   // @Singleton
   // fun provideSubMenuActivityAdapter(context: Fragment, resourceSubMenu: Int, submenus: MutableList<SubMenuDrawer>): SubMenuActivityAdapter =
     //       SubMenuActivityAdapter(null, context, resourceSubMenu, submenus)

    @Provides
    @Singleton
    fun provideListSubMenus(): MutableList<SubMenuDrawer> = arrayListOf()

    @Provides
    @Singleton
    fun provideResourceMenu(): Int = R.layout.spinner_menu_item
}