package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.*
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FixtureFragmentModule(val view: FixtureFragmentView, val listener: OnItemClickListener) {
    @Provides
    @Singleton
    fun provideFixtureFragmentView(): FixtureFragmentView = this.view

    @Provides
    @Singleton
    fun provideFixtureFragmentPresenter(view: FixtureFragmentView, event: EventBus, interactor: FixtureFragmentInteractor): FixtureFragmentPresenter
            = FixtureFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideFixtureFragmentInteractor(repository: FixtureFragmentRepository): FixtureFragmentInteractor
            = FixtureFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideFixtureFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): FixtureFragmentRepository
            = FixtureFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideFixtureFragmentAdapter(fixtures: MutableList<Fixture>, listener: OnItemClickListener, fragment: Fragment): FixtureFragmentAdapter
            = FixtureFragmentAdapter(fixtures, listener, fragment)


    @Provides
    @Singleton
    fun provideListFixture(): MutableList<Fixture>  = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): OnItemClickListener = listener
}