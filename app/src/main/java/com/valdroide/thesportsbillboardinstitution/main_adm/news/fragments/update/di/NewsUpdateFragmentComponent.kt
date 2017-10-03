package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.NewsUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.NewsUpdateFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NewsUpdateFragmentModule::class, LibsModule::class))
interface NewsUpdateFragmentComponent {

    fun getPresenter(): NewsUpdateFragmentPresenter
    fun getAdapter(): NewsUpdateFragmentAdapter
}