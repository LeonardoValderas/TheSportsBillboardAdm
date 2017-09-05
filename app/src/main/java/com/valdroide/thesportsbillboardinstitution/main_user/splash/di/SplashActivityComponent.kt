package com.valdroide.thesportsbillboardinstitution.main_user.splash.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.splash.SplashActivityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SplashActivityModule::class, LibsModule::class))
interface SplashActivityComponent {
   // fun inject(activity: SplashActivity)
    fun getPresenter(): SplashActivityPresenter
}
