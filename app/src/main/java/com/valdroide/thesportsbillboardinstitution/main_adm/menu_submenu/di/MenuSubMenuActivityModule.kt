package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di

import android.app.Activity
import android.content.Context
import android.widget.ArrayAdapter
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.*
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MenuSubMenuActivityModule(val view: MenuSubMenuActivityView, val context: Context) {

    @Provides
    @Singleton
    fun provideMenuSubMenuActivityView(): MenuSubMenuActivityView =
            this.view

    @Provides
    @Singleton
    fun provideMenuSubMenuActivityPresenter(view: MenuSubMenuActivityView, event: EventBus, interactor: MenuSubMenuActivityInteractor): MenuSubMenuActivityPresenter =
            MenuSubMenuActivityPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideMenuSubMenuActivityInteractor(repository: MenuSubMenuActivityRepository): MenuSubMenuActivityInteractor =
            MenuSubMenuActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideMenuSubMenuActivityRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): MenuSubMenuActivityRepository =
            MenuSubMenuActivityRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}