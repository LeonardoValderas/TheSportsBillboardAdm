package com.valdroide.thesportsbillboardinstitution.lib.base

import io.reactivex.Scheduler

interface SchedulersInterface {
    fun schedulerIO(): Scheduler
    fun schedulerCom(): Scheduler
    fun schedulerMainThreader(): Scheduler

}