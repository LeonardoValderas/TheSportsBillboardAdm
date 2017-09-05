package com.valdroide.thesportsbillboardinstitution.fixture

import android.content.Context
import com.nhaarman.mockito_kotlin.verify
import com.squareup.picasso.Picasso
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity
import kotlin.test.assertNotNull
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentInteractor
import kotlinx.android.synthetic.main.fragment_fixture.*
import org.robolectric.shadows.support.v4.SupportFragmentController
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class,
        sdk = intArrayOf(23))
class FixtureFragmentTest : BaseTest() {

    @Mock
    lateinit var event: FixtureFragmentEvent
    @Mock
    lateinit var interactor: FixtureFragmentInteractor
    @Mock
    lateinit var eventBus: EventBus
    @Mock
    lateinit var presenter: FixtureFragmentPresenter
    @Mock
    lateinit var adapter: FixtureFragmentAdapter
    @Mock
    lateinit var context: Context
    @Mock
    var fixtures: MutableList<Fixture> = arrayListOf()
    @Mock
    var fixturesTest: MutableList<Fixture> = arrayListOf()
    @Mock
    lateinit var picasso: Picasso
    @Mock
    lateinit var utils: Utils
    @Mock
    lateinit var savedInstanceState: Bundle
    lateinit var view: FixtureFragmentView
    lateinit var fragment: FixtureFragment
    lateinit var controller: SupportFragmentController<FixtureFragment>
    lateinit var shadowFragment: ShadowActivity

    @Before
    override fun setUp() {
        super.setUp()

        controller = SupportFragmentController.of(FixtureFragment.create(presenter, adapter, fixturesTest)).
                create().start()
        fragment = controller.get()
        view = fragment
        context = fragment.context
        shadowFragment = Shadows.shadowOf(fragment.activity)
    }

    @Test
    fun shouldCallPresenterOnCreateAndGetFixtures() {
        assertNotNull(context)
        verify(presenter, times(1)).onCreate()
        val swipeRefreshLayout: SwipeRefreshLayout = fragment.swipeRefreshLayout
        assertNotNull(swipeRefreshLayout)
        assertEquals(swipeRefreshLayout.isRefreshing(), true)
        whenever(utils.getSubmenuId(context)).thenReturn(1) // doesn't work
        verify(presenter, times(1)).getFixtures(fragment.activity, 0, 0)
    }

    @Test
    fun shouldInitRecyclerView() {
        val recycler: RecyclerView = fragment.recyclerView
        assertNotNull(recycler)
        assertNotNull(recycler.layoutManager)
        assertNotNull(recycler.adapter)
        assertEquals(recycler.adapter, adapter)
    }

    @Test
    fun shouldShowSwipeRefreshLayout() {
        val swipeRefreshLayout: SwipeRefreshLayout = fragment.swipeRefreshLayout
        assertNotNull(swipeRefreshLayout)
        assertEquals(swipeRefreshLayout.isRefreshing(), true)
    }

    @Test
    fun shouldSetFixtureAdapterAndHideSwipeRefreshLayoutIsClickFalse() {
        fragment.getFixtures(false)
        view.setFixture(fixtures)
        view.hideSwipeRefreshLayout()
        verify(fragment.adapterFixture).setFixtures(fixturesTest, false)
        assertEquals(fragment.adapterFixture.itemCount, fixturesTest.size)
        val swipeRefreshLayout: SwipeRefreshLayout = fragment.swipeRefreshLayout
        assertNotNull(swipeRefreshLayout)
        assertEquals(swipeRefreshLayout.isRefreshing(), false)
    }

    //how to mock the real object?
    @Test
    fun shouldSetFixtureAdapterAndHideSwipeRefreshLayoutIsClickTrue() {
        fragment.getFixtures(true)
        view.setFixture(fixtures)
        view.hideSwipeRefreshLayout()
        verify(fragment.adapterFixture).setFixtures(fixturesTest, true)
        assertEquals(fragment.adapterFixture.itemCount, fixturesTest.size)
        val swipeRefreshLayout: SwipeRefreshLayout = fragment.swipeRefreshLayout
        assertNotNull(swipeRefreshLayout)
        assertEquals(swipeRefreshLayout.isRefreshing(), false)
    }

    fun onDestroyTest() {
        controller.destroy()
        verify(presenter).onDestroy()
    }
/*
    @Test
    fun onPauseTest() {
        controller.pause()
        verify(presenter).onDestroy()
    }
*/
    @Test
    fun onStartTest() {
        controller.start()
        verify(presenter).onCreate()
    }

    @Test
    fun onStopTest() {
        controller.stop()
        verify(presenter).onDestroy()
    }
}