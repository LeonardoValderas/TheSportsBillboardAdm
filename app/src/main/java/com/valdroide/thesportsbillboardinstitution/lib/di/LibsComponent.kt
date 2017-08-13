package com.valdroide.thesportsbillboardinstitution.lib.di

import dagger.Component
import javax.inject.Singleton
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionAppModule

@Singleton
@Component(modules = arrayOf(LibsModule::class, TheSportsBillboardInstitutionAppModule::class))
interface LibsComponent{
}
