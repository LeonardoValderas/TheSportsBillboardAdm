package com.valdroide.thesportsbillboardadm.main.splash.di

import com.valdroide.thesportsbillboardadm.TheSportsBillboardAdmAppModule
import com.valdroide.thesportsbillboardadm.lib.di.LibsModule
import com.valdroide.thesportsbillboardadm.main.splash.ui.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SplashActivityModule::class, LibsModule::class, TheSportsBillboardAdmAppModule::class))
interface SplashActivityComponent {
    fun inject(activity: SplashActivity)
}
