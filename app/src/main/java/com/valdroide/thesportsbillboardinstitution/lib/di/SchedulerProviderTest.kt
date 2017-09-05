package com.valdroide.thesportsbillboardinstitution.lib.di

import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

class SchedulerProviderTest : SchedulersInterface {

    object Singleton

    override fun schedulerIO(): Scheduler = Schedulers.trampoline()
    override fun schedulerCom(): Scheduler = Schedulers.trampoline()
    override fun schedulerMainThreader(): Scheduler = Schedulers.trampoline()
}