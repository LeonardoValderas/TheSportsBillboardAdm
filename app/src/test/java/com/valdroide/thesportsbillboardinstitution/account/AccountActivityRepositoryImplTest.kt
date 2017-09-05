package com.valdroide.thesportsbillboardinstitution.account

import android.content.Context
import com.google.firebase.FirebaseApp
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.lib.di.SchedulerProviderTest
import com.valdroide.thesportsbillboardinstitution.main_user.account.AccountActivityRepository
import com.valdroide.thesportsbillboardinstitution.main_user.account.AccountActivityRepositoryImpl
import com.valdroide.thesportsbillboardinstitution.main_user.account.events.AccountActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.AccountResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
class AccountActivityRepositoryImplTest : BaseTest() {

    lateinit var repository: AccountActivityRepository
    @Mock
    var view: AccountActivityView? = null
    @Mock
    lateinit var eventBus: EventBus
    @Mock
    lateinit var service: ApiService
    @Mock
    lateinit var scheduler: SchedulersInterface
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var event: AccountActivityEvent
    @Mock
    lateinit var account: Account
    @Mock
    lateinit var swResponse: WSResponse

    @Before
    override fun setUp() {
        super.setUp()
        val app = RuntimeEnvironment.application as TheSportsBillboardInstitutionApp
        FirebaseApp.initializeApp(app)
        scheduler = SchedulerProviderTest()
        repository = AccountActivityRepositoryImpl(eventBus, service, scheduler)
    }

    @Test
    fun shouldSetAccountPostEvent_getAccount() {
        swResponse = WSResponse(0, "0", "ok")
        val accountResponse = AccountResponse(swResponse, account)
        whenever(service.getAccount()).thenReturn(Observable.just(accountResponse))
        repository.getAccount(context)
        verify(service).getAccount()
        argumentCaptor<AccountActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(AccountActivityEvent.ACCOUNT, event.type)
        assertEquals(event.account, account)
    }

    @Test
    fun shouldSetErrorResponseNotZeroPostEvent_getAccount() {
        swResponse = WSResponse(0, "2", "Error")
        val accountResponse = AccountResponse(swResponse, account)
        whenever(service.getAccount()).thenReturn(Observable.just(accountResponse))
        repository.getAccount(context)
        verify(service).getAccount()
        argumentCaptor<AccountActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(AccountActivityEvent.ERROR, event.type)
        assertEquals(event.error, swResponse.MESSAGE)
    }

    @Test
    fun shouldSetErrorResponseNullPostEvent_getAccount() {
        whenever(service.getAccount()).thenReturn(Observable.just(AccountResponse(null, null)))
        repository.getAccount(context)
        verify(service).getAccount()
        argumentCaptor<AccountActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(AccountActivityEvent.ERROR, event.type)
        assertEquals(event.error, context.getString(R.string.null_response))
    }

    @Test
    fun shouldSetErrorExceptionPostEvent_getAccount() {
        whenever(service.getAccount()).thenReturn(Observable.error { Throwable("Error") })
        repository.getAccount(context)
        verify(service).getAccount()
        argumentCaptor<AccountActivityEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(AccountActivityEvent.ERROR, event.type)
        assertEquals(event.error, "Error")
    }
}