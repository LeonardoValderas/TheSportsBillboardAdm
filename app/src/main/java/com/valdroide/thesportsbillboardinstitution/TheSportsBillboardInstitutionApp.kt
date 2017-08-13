package com.valdroide.thesportsbillboardinstitution

import android.app.Application
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule

class TheSportsBillboardInstitutionApp : Application() {
    private var libsModule: LibsModule? = null
    private var theSportsBillboardAppModule: TheSportsBillboardInstitutionAppModule? = null

    override fun onCreate() {
        super.onCreate()
        initModules()
        initDB()
        Stetho.initializeWithDefaults(this);
    }
    private fun initModules() {
        libsModule = LibsModule()
        theSportsBillboardAppModule = TheSportsBillboardInstitutionAppModule(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        DBTearDown()
    }

    private fun DBTearDown() {
        FlowManager.destroy()
    }

    private fun initDB() {
        FlowManager.init(this)
    }
}
