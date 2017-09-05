package com.valdroide.thesportsbillboardinstitution.main_user.navigation.di

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService

import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.*
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.NavigationActivityView
import dagger.Module
import dagger.Provides
import java.util.ArrayList
import javax.inject.Singleton

@Module
class NavigationActivityModule(val context: Context, var view: NavigationActivityView) {

    @Provides
    @Singleton
    fun providesNavigationActivityView(): NavigationActivityView =
            this.view

    @Provides
    @Singleton
    fun providesNavigationActivityPresenter(eventBus: EventBus, view: NavigationActivityView, listInteractor: NavigationActivityInteractor): NavigationActivityPresenter =
            NavigationActivityPresenterImpl(view, eventBus, listInteractor)

    @Provides
    @Singleton
    fun providesNavigationActivityInteractor(repository: NavigationActivityRepository): NavigationActivityInteractor =
            NavigationActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun providesNavigationActivityRepository(eventBus: EventBus, apiService: ApiService, scheduler: SchedulersInterface): NavigationActivityRepository =
            NavigationActivityRepositoryImpl(eventBus, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun providesExpandableListTitle(): List<String> =
            ArrayList()

//    @Provides
//    @IntoMap
//    @ClassKey(SubMenuDrawer::class)
//    fun provideSubMenus(): List<SubMenuDrawer> =
//            ArrayList<SubMenuDrawer>()

//    @Provides
//    @Singleton
//    fun providesExpandableListDetail(): Map<String, List<SubMenuDrawer>> {
//        return TreeMap()
//    }

//    @Provides
//    @Singleton
//    fun providesCustomExpandableListAdapter(context: Context, expandableListTitle: List<String>,
//                                            expandableListDetail: Map<String, List<SubMenuDrawer>>): CustomExpandableListAdapter =
//            CustomExpandableListAdapter(context, expandableListTitle, expandableListDetail)

}
