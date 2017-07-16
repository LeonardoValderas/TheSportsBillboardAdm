package com.valdroide.thesportsbillboardadm

import android.app.Application
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.valdroide.thesportsbillboardadm.lib.di.LibsModule

class TheSportsBillboardAdmApp : Application() {
    private var libsModule: LibsModule? = null
    private var theSportsBillboardAppModule: TheSportsBillboardAdmAppModule? = null

    override fun onCreate() {
        super.onCreate()
        initModules()
        initDB()
        Stetho.initializeWithDefaults(this);
    }
    private fun initModules() {
        libsModule = LibsModule()
        theSportsBillboardAppModule = TheSportsBillboardAdmAppModule(this)
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
