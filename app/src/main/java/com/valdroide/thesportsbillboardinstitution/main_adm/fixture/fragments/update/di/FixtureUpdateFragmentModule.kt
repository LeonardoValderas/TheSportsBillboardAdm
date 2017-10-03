package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter.FixtureUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FixtureUpdateFragmentModule(val view: FixtureUpdateFragmentView, val listener: GenericOnItemClickListener_2) {

    @Provides
    @Singleton
    fun provideFixtureUpdateFragmentView(): FixtureUpdateFragmentView = this.view

    @Provides
    @Singleton
    fun provideFixtureUpdateFragmentPresenter(view: FixtureUpdateFragmentView, event: EventBus, interactor: FixtureUpdateFragmentInteractor): FixtureUpdateFragmentPresenter
            = FixtureUpdateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideFixtureUpdateFragmentInteractor(repository: FixtureUpdateFragmentRepository): FixtureUpdateFragmentInteractor
            = FixtureUpdateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideFixtureUpdateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): FixtureUpdateFragmentRepository
            = FixtureUpdateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideFixtureUpdateFragmentAdapter(fixture: MutableList<Fixture>, listener: GenericOnItemClickListener_2, fragment: Fragment): FixtureUpdateFragmentAdapter
            = FixtureUpdateFragmentAdapter(fixture, listener, fragment)

    @Provides
    @Singleton
    fun provideListFixture(): MutableList<Fixture> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): GenericOnItemClickListener_2 = listener
}