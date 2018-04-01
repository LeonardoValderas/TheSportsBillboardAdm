package com.valdroide.thesportsbillboardinstitution.fixture

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockito_kotlin.whenever
import com.squareup.picasso.Picasso
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentController
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.robolectric.shadow.api.Shadow
import android.support.v7.widget.LinearLayoutManager
import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentPresenter
import org.robolectric.Robolectric


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class,
        sdk = intArrayOf(23), shadows = arrayOf(ShadowRecyclerViewAdapter::class))
class FixtureFragmentAdapterTest : BaseTest() {

    @Mock
    lateinit private var adapter: FixtureFragmentAdapter
    @Mock
    lateinit private var context: Context
    @Mock
    lateinit var presenter: FixtureFragmentPresenter
    @Mock
    lateinit private var fixtures: MutableList<Fixture>
    @Mock
    lateinit private var fixturesTest: MutableList<Fixture>
    @Mock
    lateinit private var fixture: Fixture
    @Mock
    lateinit private var picasso: Picasso
    @Mock
    lateinit private var utils: Utils
    @Mock
    lateinit private var onItemClickListener: OnItemClickListener

    lateinit private var fragment: FixtureFragment

    lateinit private var shadowAdapter: ShadowRecyclerViewAdapter

    lateinit private var URL_LOCAL: String
    lateinit private var URL_VISIT: String
    lateinit var controller: SupportFragmentController<FixtureFragment>

    @Before
    override fun setUp() {
        super.setUp()
        controller = SupportFragmentController.of(FixtureFragment.create(presenter, adapter, fixturesTest)).
                create().start()
        fragment = controller.get()

        adapter = FixtureFragmentAdapter(fixtures, onItemClickListener, fragment)
        shadowAdapter = Shadow.extract(adapter)
        val activity = Robolectric.setupActivity(Activity::class.java)
        val recyclerView = RecyclerView(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    @Test
    fun shouldReturnItemCount() {
        val itemCount = 5
        whenever(fixtures.size).thenReturn(itemCount)
        adapter.setFixtures(fixtures, false)
        assertEquals(itemCount, adapter.itemCount)
    }

    @Test
    fun shouldClickFixtureFirstPosition() {
        val positionToClick = 0
        URL_LOCAL = "http://www.caratulasylogos.com/sites/default/files/almirante_brown.jpg"
        URL_VISIT = "http://www.caratulasylogos.com/sites/default/files/argentinos_juniors.gif"
        whenever(fixture.URL_LOCAL_TEAM).thenReturn(URL_LOCAL)
        whenever(fixture.URL_VISIT_TEAM).thenReturn(URL_VISIT)
        adapter.setFixtures(fixtures, false)
        whenever(fixtures.get(positionToClick)).thenReturn(fixture);
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);
        shadowAdapter.itemVisible(0)
        //   val view = shadowAdapter.getViewForHolderPosition(positionToClick);
        //  val imageViewLocalTeam =  view.findViewById<ImageView>(R.id.imageViewLocalTeam);
        // val imageViewVisitTeam =  view.findViewById<ImageView>(R.id.imageViewVisitTeam);
        //     whenever(utils.setPicasso(context, URL_LOCAL, R.mipmap.ic_launcher, imageViewLocalTeam)).thenReturn(utils.setPicasso(context, URL_LOCAL, R.mipmap.ic_launcher, imageViewLocalTeam))
        // utils.setPicasso(context, URL_VISIT, R.mipmap.ic_launcher, imageViewVisitTeam)

        // verify(onItemClickListener).onClickFixture(positionToClick, fixture);
    }

    @Test
    fun shouldShowFixtureItem() {
        URL_LOCAL = "http://www.caratulasylogos.com/sites/default/files/almirante_brown.jpg"
        URL_VISIT = "http://www.caratulasylogos.com/sites/default/files/argentinos_juniors.gif"
        val date = "2017"
        val hour = "22-08"
        val r_l = "2"
        val r_v = "3"
        val fiel = "cancha"
        val address = "dire"
        val obser = "ob"
        whenever(fixture.DATE_MATCH).thenReturn(date)
        whenever(fixture.HOUR_MATCH).thenReturn(hour)

        whenever(fixture.URL_LOCAL_TEAM).thenReturn(URL_LOCAL)
        whenever(fixture.URL_VISIT_TEAM).thenReturn(URL_VISIT)
        whenever(fixture.RESULT_LOCAL).thenReturn(r_l)
        whenever(fixture.RESULT_VISITE).thenReturn(r_v)
        whenever(fixture.NAME_FIELD).thenReturn(fiel)
        whenever(fixture.ADDRESS).thenReturn(address)
        whenever(fixture.OBSERVATION).thenReturn(obser)

        adapter.setFixtures(fixtures, false)
        whenever(fixtures.get(0)).thenReturn(fixture)
        whenever(fixture.URL_LOCAL_TEAM).thenReturn(URL_LOCAL)
        whenever(fixture.URL_VISIT_TEAM).thenReturn(URL_VISIT)
        shadowAdapter.itemVisible(0)

        val view = shadowAdapter.getViewForHolderPosition(0)
        val textViewDate: TextView = view.findViewById<TextView>(R.id.textViewDateHour)
       // val textViewHour: TextView = view.findViewById<TextView>(R.id.textViewHour)
        val imageViewLocalTeam: ImageView = view.findViewById<ImageView>(R.id.imageViewLocalTeam)
        val imageViewVisitTeam: ImageView = view.findViewById<ImageView>(R.id.imageViewVisitTeam)
        val textViewLocalResult: TextView = view.findViewById<TextView>(R.id.textViewLocalResult)
        val textViewVisitResult: TextView = view.findViewById<TextView>(R.id.textViewVisitResult)
        val textViewFieldMatch: TextView = view.findViewById<TextView>(R.id.textViewFieldMatch)
        val textViewAddress: TextView = view.findViewById<TextView>(R.id.textViewAddress)
        val textViewObservation: TextView = view.findViewById<TextView>(R.id.textViewObservation)

        assertEquals(textViewDate.text, date)
      //  assertEquals(textViewHour.text, hour)
        assertEquals(textViewLocalResult.text, r_l)
        assertEquals(textViewVisitResult.text, r_v)
        assertEquals(textViewFieldMatch.text, fiel)
        assertEquals(textViewAddress.text, address)
        assertNotNull(textViewObservation.text)
        assertEquals(textViewObservation.visibility, View.VISIBLE)
    }
}