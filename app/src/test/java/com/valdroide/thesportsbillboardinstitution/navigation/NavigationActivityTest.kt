package com.valdroide.thesportsbillboardinstitution.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.ui.NavigationActivity
import com.valdroide.thesportsbillboardinstitution.main_user.tab.TabActivity
import kotlinx.android.synthetic.main.toolbar_layout.*
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
class NavigationActivityTest : BaseTest() {

    @Mock
    lateinit var context: Context
    lateinit var activity: NavigationActivity
    lateinit var controller: ActivityController<NavigationActivity>
    lateinit var shadowActivity: ShadowActivity
    @Mock
    lateinit var menu: Menu
    @Mock
    lateinit var drawer: DrawerLayout
    @Mock
    lateinit var toggle: ActionBarDrawerToggle


    @Before
    override fun setUp() {
        super.setUp()

        val navigationActivity = object : NavigationActivity() {
//            override fun setTheme(resid: Int) {
//                super.setTheme(R.style.AppTheme_NoActionBar)
//            }
        }
        controller = ActivityController.of(Robolectric.getShadowsAdapter(), navigationActivity as NavigationActivity).create().visible()
        activity = controller.get()
        context = activity
        shadowActivity = Shadows.shadowOf(activity)
//        drawer activity.mNavigationDrawer = NavigationDrawerActivity.NavigationDrawerViewHolder()
//        activity.mNavigationDrawer.mLayout = drawer
    }


    @Test
    fun shouldInitToolBarOnCreate() {
        val toolBar: Toolbar = activity.toolbar
        assertNotNull(toolBar)
        //        assertEquals(toolBar.title, "leo")
    }

    @Test
    fun shouldGoToAccountIntentMenuClick() {
        shadowActivity.onCreateOptionsMenu(menu)
       // shadowActivity.clickMenuItem(R.id.navigation_home)
        val implicitIntent = Intent(activity, AccountActivity::class.java)
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertTrue(intent.filterEquals(implicitIntent));
    }


    /**
     * I can't verificar earch item. Always call the first item.
     */
    @Test
    fun shouldGoToTabActivityIntentNavDrawerMenuClick() {
     //   val navigationView: NavigationView = activity.nav_view
       // navigationView.callOnClick()
        val implicitIntent = Intent(activity, TabActivity::class.java)
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertTrue(intent.filterEquals(implicitIntent));
    }
}