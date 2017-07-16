package com.valdroide.thesportsbillboardadm.lib.di

import android.app.Activity
import com.valdroide.thesportsbillboardadm.lib.base.EventBus
import com.valdroide.thesportsbillboarduadm.lib.di.GreenRobotEventBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LibsModule {
    private var activity: Activity? = null;

    constructor() {
    }

    constructor(activity: Activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    fun providesEventBus(): EventBus {
        return GreenRobotEventBus();
    }

    @Provides
    @Singleton
    fun providesActivity(): Activity {
        return this.activity!!;
    }
}
