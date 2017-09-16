package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di

import android.content.Context
import android.widget.ArrayAdapter
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlayerCreateFragmentModule(val view: PlayerCreateFragmentView, val context: Context) {

    @Provides
    @Singleton
    fun providePlayerCreateFragmentView(): PlayerCreateFragmentView = this.view

    @Provides
    @Singleton
    fun providePlayerCreateFragmentPresenter(view: PlayerCreateFragmentView, event: EventBus, interactor: PlayerCreateFragmentInteractor): PlayerCreateFragmentPresenter
            = PlayerCreateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun providePlayerCreateFragmentInteractor(repository: PlayerCreateFragmentRepository): PlayerCreateFragmentInteractor
            = PlayerCreateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun providePlayerCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): PlayerCreateFragmentRepository
            = PlayerCreateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }


    @Provides
    @Singleton
    fun provideArrayAdapterPosition(context: Context, layoutResourceId: Int, positions: MutableList<Position>): ArrayAdapter<Position> =
            ArrayAdapter(context, layoutResourceId, positions)


    @Provides
    @Singleton
    fun provideListPosition(): MutableList<Position> = arrayListOf()

    @Provides
    @Singleton
    fun provideArrayAdapterSubMenus(context: Context, layoutResourceId: Int, submenus: MutableList<SubMenuDrawer>): ArrayAdapter<SubMenuDrawer> =
            ArrayAdapter(context, layoutResourceId, submenus)


    @Provides
    @Singleton
    fun provideListSubMenus(): MutableList<SubMenuDrawer> = arrayListOf()

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideLayoutResourceId(): Int = android.R.layout.simple_list_item_1


}