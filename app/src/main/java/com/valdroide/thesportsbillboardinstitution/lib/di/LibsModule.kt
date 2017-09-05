package com.valdroide.thesportsbillboardinstitution.lib.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LibsModule {
    lateinit var activity: Activity
    lateinit var fragment: Fragment

    constructor() {
    }

    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
    }

    @Provides
    @Singleton
    fun providesEventBus(): EventBus = GreenRobotEventBus()

    @Provides
    @Singleton
    fun providesSchedulerProvider(): SchedulersInterface = SchedulerProvider()

//    @Provides
//    @Singleton
//    fun providesSchedulerProviderTest(): SchedulersInterface = SchedulerProviderTest()


    @Provides
    @Singleton
    fun providesActivity(): Activity = this.activity


    @Provides
    @Singleton
    fun providesFragment(): Fragment = this.fragment

}
