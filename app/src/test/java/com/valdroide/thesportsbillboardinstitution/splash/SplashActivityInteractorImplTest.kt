package com.valdroide.thesportsbillboardinstitution.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.splash.*
import org.junit.Before
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView
import org.mockito.Mock
import com.valdroide.thesportsbillboardinstitution.main.splash.events.SplashActivityEvent
import org.junit.Test
import org.mockito.Mockito.verify

class SplashActivityInteractorImplTest : BaseTest() {

    var interactor: SplashActivityInteractorImpl? = null
    @Mock
    var view: SplashActivityView? = null
    @Mock
    var eventBus: EventBus? = null
    @Mock
    var repository: SplashActivityRepository? = null
    @Mock
    var context: Context? = null
    @Mock
    var login: Login? = null
    @Mock
    var event: SplashActivityEvent? = null

    @Before
    override fun setUp() {
        super.setUp()
        interactor = SplashActivityInteractorImpl(repository!!)
    }


    @Test
    fun getDateClubTest() {
        repository?.getDateClub(context!!)
        verify(repository)?.getDateClub(context!!)
    }

    @Test
    fun validateDateClubTest() {
        repository?.validateDateClub(context!!)
        verify(repository)?.validateDateClub(context!!)
    }

    @Test
    fun getLoginTest() {
        repository?.getLogin(context!!)
        verify(repository)?.getLogin(context!!)
    }

    @Test
    fun validateLoginTest() {
        repository?.validateLogin(context!!, login!!)
        verify(repository)?.validateLogin(context!!, login!!)
    }
}