package com.valdroide.thesportsbillboardinstitution.fixture

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
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentRepository
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentRepositoryImpl
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.FixtureResponse
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
class FixtureFragmentRepositoryImplTest : BaseTest() {

    lateinit var repository: FixtureFragmentRepository
    @Mock
    lateinit var view: FixtureFragmentView
    @Mock
    lateinit var eventBus: EventBus
    @Mock
    lateinit var service: ApiService
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var event: FixtureFragmentEvent
    @Mock
    lateinit var fixture: Fixture
    @Mock
    lateinit var fixtures: MutableList<Fixture>
    @Mock
    lateinit var swResponse: WSResponse
    @Mock
    lateinit var scheduler: SchedulersInterface

    @Before
    override fun setUp() {
        super.setUp()
        val app = RuntimeEnvironment.application as TheSportsBillboardInstitutionApp
        FirebaseApp.initializeApp(app)
        scheduler = SchedulerProviderTest()
        repository = FixtureFragmentRepositoryImpl(eventBus, service, scheduler)
    }

    @Test
    fun shouldSetFixturesFromService() {
        swResponse = WSResponse(0, "0", "ok")
        val fixtureResponse = FixtureResponse(swResponse, fixtures, null, null, null, null, null)
        whenever(service.getFixtureForDivision(1,0)).thenReturn(Observable.just(fixtureResponse))
        repository.getFixtures(context, 1, 0)
        verify(service).getFixtureForDivision(1, 0)
        argumentCaptor<FixtureFragmentEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(FixtureFragmentEvent.FIXTURES, event.type)
        assertEquals(event.fixtures, fixtures)
    }

    @Test
    fun shouldSetErrorResponseNotZeroPostEventGetFixtureForDivision() {
        swResponse = WSResponse(0, "2", "Error")
        val fixtureResponse = FixtureResponse(swResponse, fixtures, null, null, null, null, null)
        whenever(service.getFixtureForDivision(1, 0)).thenReturn(Observable.just(fixtureResponse))
        repository.getFixtures(context, 1, 0)
        verify(service).getFixtureForDivision(1, 0)
        argumentCaptor<FixtureFragmentEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(FixtureFragmentEvent.ERROR, event.type)
        assertEquals(event.error, swResponse.MESSAGE)
    }

    @Test
    fun shouldSetErrorResponseNullPostEventGetFixtureForDivision() {
        whenever(service.getFixtureForDivision(1, 0)).thenReturn(Observable.just(FixtureResponse(null, null, null, null, null, null, null)))
        repository.getFixtures(context, 1, 0)
        verify(service).getFixtureForDivision(1, 0)
        argumentCaptor<FixtureFragmentEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(FixtureFragmentEvent.ERROR, event.type)
        assertEquals(event.error, context.getString(R.string.null_response))
    }

    @Test
    fun shouldSetErrorExceptionPostEventGetFixtureForDivision() {
        whenever(service.getFixtureForDivision(1, 0)).thenReturn(Observable.error { Throwable("Error") })
        repository.getFixtures(context, 1, 0)
        verify(service).getFixtureForDivision(1, 0)
        argumentCaptor<FixtureFragmentEvent>().apply {
            verify(eventBus).post(capture())
            event = firstValue
        }
        assertEquals(FixtureFragmentEvent.ERROR, event.type)
        assertEquals(event.error, "Error")
    }
}