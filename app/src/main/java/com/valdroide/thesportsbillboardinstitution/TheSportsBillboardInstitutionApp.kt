package com.valdroide.thesportsbillboardinstitution

import android.app.Application
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.valdroide.thesportsbillboardinstitution.main.splash.di.SplashActivityModule
import com.valdroide.thesportsbillboardinstitution.main.splash.di.DaggerSplashActivityComponent
import android.app.Activity
import android.os.Build
import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView
import com.valdroide.thesportsbillboardinstitution.main.splash.di.SplashActivityComponent
import android.os.Build.FINGERPRINT
import com.google.firebase.analytics.FirebaseAnalytics
import com.valdroide.thesportsbillboardinstitution.main.account.di.AccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.main.account.di.AccountActivityModule
import com.valdroide.thesportsbillboardinstitution.main.account.di.DaggerAccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.main.account.ui.AccountActivityView


class TheSportsBillboardInstitutionApp : Application() {
    lateinit var libsModule: LibsModule
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        initModules()
        initDB()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (!isRoboUnitTest())
            Stetho.initializeWithDefaults(this);
    }

    private fun initModules() {
        libsModule = LibsModule()
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

    fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }

    open fun firebaseAnalyticsInstance(): FirebaseAnalytics {
        return firebaseAnalytics;
    }

    fun getSplashActivityComponent(view: SplashActivityView, activity: Activity): SplashActivityComponent {
        return DaggerSplashActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .splashActivityModule(SplashActivityModule(view))
                .build()
    }
    fun getAccountActivityComponent(view: AccountActivityView, activity: Activity): AccountActivityComponent {
        return DaggerAccountActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .accountActivityModule(AccountActivityModule(view))
                .build()
    }
}
