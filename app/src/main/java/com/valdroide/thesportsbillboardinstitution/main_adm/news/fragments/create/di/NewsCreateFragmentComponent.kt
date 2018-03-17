package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.NewsCreateFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NewsCreateFragmentModule::class, LibsModule::class))
interface NewsCreateFragmentComponent {
    fun getPresenter(): NewsCreateFragmentPresenter
 //   fun getAdapterSubMenus(): SubMenuActivityAdapter
}