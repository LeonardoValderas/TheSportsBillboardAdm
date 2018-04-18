package com.valdroide.thesportsbillboardinstitution.tab

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.TabActivity
import com.valdroide.thesportsbillboardinstitution.utils.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_tab.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
class TabActivityTest : BaseTest() {

    @Mock
    lateinit var context: Context
    lateinit var activity: TabActivity
    lateinit var controller: ActivityController<TabActivity>
    lateinit var shadowActivity: ShadowActivity
    var adapter: SectionsPagerAdapter? = null

    @Before
    override fun setUp() {
        super.setUp()

        val tabActivity = object : TabActivity() {
//            override fun setTheme(resid: Int) {
//                super.setTheme(R.style.AppTheme_NoActionBar)
//            }
        }
        controller = ActivityController.of(Robolectric.getShadowsAdapter(), tabActivity as TabActivity).create().visible()
        activity = controller.get()
        context = activity
        shadowActivity = Shadows.shadowOf(activity)
    }

    @Test
    fun shouldInitToolBarOnCreate() {
        val toolBar: Toolbar = activity.toolbar
        assertNotNull(toolBar)
        assertEquals(toolBar.title, "leo")
    }

    @Test
    fun shouldInitViewPageAdapterTabsOnCreate() {
        val viewPager: ViewPager = activity.viewPager
        adapter = activity.adapter
        assertNotNull(adapter)
        viewPager.adapter
        assertEquals(viewPager.adapter, adapter)
        val tabs: TabLayout = activity.tabs
        tabs.setupWithViewPager(viewPager)
        assertEquals(tabs.tabMode, TabLayout.MODE_SCROLLABLE)
    }
}