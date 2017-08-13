package com.valdroide.thesportsbillboardinstitution.main.splash.di

import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionAppModule
import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SplashActivityModule::class, LibsModule::class, TheSportsBillboardInstitutionAppModule::class))
interface SplashActivityComponent {
    fun inject(activity: SplashActivity)
}
