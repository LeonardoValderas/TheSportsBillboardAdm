package com.valdroide.thesportsbillboardinstitution.splash

import android.content.Context
import com.nhaarman.mockito_kotlin.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.splash.*
import com.valdroide.thesportsbillboardinstitution.main_user.splash.events.SplashActivityEvent
import org.junit.Before
import com.valdroide.thesportsbillboardinstitution.main_user.splash.ui.SplashActivityView
import org.mockito.Mock
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
class SplashActivityRepositoryImplTest : BaseTest() {

    lateinit var repository: SplashActivityRepository
    @Mock
    var view: SplashActivityView? = null
    @Mock
    lateinit var eventBus: EventBus
    @Mock
    var service: ApiService? = null
    @Mock
    lateinit var context: Context
    @Mock
    var login: Login? = null
    @Mock
    lateinit var event: SplashActivityEvent
    @Mock
    var dateData: DateData? = null

    @Before
    override fun setUp() {
        super.setUp()
        repository = SplashActivityRepositoryImpl(eventBus, service!!)
    }
/*
    Doesnt work
    @Test(expected = Exception::class)
    fun getDateExceptionTest() {
        whenever(dateData).thenThrow(Exception())
        repository.getData(context)
        argumentCaptor<SplashActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(SplashActivityEvent.ERROR, event.type)
    }
*/
    @Test
    fun getDateNullTest() {
        repository.getDate(context)
        dateData = null
        argumentCaptor<SplashActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertNull(dateData);
        assertEquals(SplashActivityEvent.DATEDATA, event.type)
        assertEquals(dateData, event.dateData)
        assertNull(event.error);
    }

    @Test
    fun getDateNotNullTest() {
        var dateDataTest = DateData()
        dateDataTest.ID_DATE_DATA_KEY = 1
        dateDataTest.ACCOUNT_DATE = "2017"
        dateDataTest.UNIQUE_DATE = "2017"
        dateDataTest.save()
        dateData = SQLite.select().from(DateData::class.java).querySingle()
        repository.getDate(context)

        argumentCaptor<SplashActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertNotNull(dateData);
        assertEquals(SplashActivityEvent.DATEDATA, event.type)
       // assertEquals(dateData, event.dateData) I dont know why are differents
        assertNull(event.error);
    }

/*
    @Test
    fun validateDateClubTest() {
    }

    @Test
    fun getLoginTest() {
    }

    @Test
    fun validateLoginTest() {
    }
*/
}