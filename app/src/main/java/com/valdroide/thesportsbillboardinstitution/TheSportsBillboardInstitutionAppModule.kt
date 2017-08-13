package com.valdroide.thesportsbillboardinstitution

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TheSportsBillboardInstitutionAppModule(internal var application : Application) {
    @Provides
    @Singleton
    internal fun providesContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return application
    }
}
