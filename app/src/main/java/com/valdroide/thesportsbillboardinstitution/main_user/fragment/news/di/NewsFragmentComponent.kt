package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.NewsFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters.NewsFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NewsFragmentModule::class, LibsModule::class))
interface NewsFragmentComponent {

    fun getPresenter(): NewsFragmentPresenter
    fun getAdapter(): NewsFragmentAdapter

}