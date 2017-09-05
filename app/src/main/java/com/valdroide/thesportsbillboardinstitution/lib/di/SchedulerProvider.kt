package com.valdroide.thesportsbillboardinstitution.lib.di

import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulerProvider : SchedulersInterface {

    object Singleton

    override fun schedulerIO(): Scheduler = Schedulers.io()
    override fun schedulerCom(): Scheduler = Schedulers.computation()
    override fun schedulerMainThreader(): Scheduler = AndroidSchedulers.mainThread()
}
