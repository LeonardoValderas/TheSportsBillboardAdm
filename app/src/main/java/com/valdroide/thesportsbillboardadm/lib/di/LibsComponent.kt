package com.valdroide.thesportsbillboardadm.lib.di

import dagger.Component
import dagger.Module
import javax.inject.Singleton
import com.valdroide.thesportsbillboardadm.TheSportsBillboardAdmAppModule

@Singleton
@Component(modules = arrayOf(LibsModule::class, TheSportsBillboardAdmAppModule::class))
interface LibsComponent{
}
