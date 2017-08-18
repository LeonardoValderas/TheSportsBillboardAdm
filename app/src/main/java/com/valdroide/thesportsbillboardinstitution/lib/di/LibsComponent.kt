package com.valdroide.thesportsbillboardinstitution.lib.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LibsModule::class))
interface LibsComponent{
}
