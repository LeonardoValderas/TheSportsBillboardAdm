package com.valdroide.thesportsbillboardinstitution.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.splash.*
import org.junit.Before
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView
import org.mockito.Mock
import com.valdroide.thesportsbillboardinstitution.main.splash.events.SplashActivityEvent
import org.junit.Test

class SplashActivityRepositoryImplTest : BaseTest() {

    var repository: SplashActivityRepository? = null
    @Mock
    var view: SplashActivityView? = null
    @Mock
    var eventBus: EventBus? = null
    @Mock
    var service: ApiService? = null
    @Mock
    var context: Context? = null
    @Mock
    var login: Login? = null
    @Mock
    var event: SplashActivityEvent? = null

    @Before
    override fun setUp() {
        super.setUp()
        repository = SplashActivityRepositoryImpl(eventBus, service)
    }


    @Test
    fun getDateClubTest() {
    }

    @Test
    fun validateDateClubTest() {
    }

    @Test
    fun getLoginTest() {
    }

    @Test
    fun validateLoginTest() {
    }
}