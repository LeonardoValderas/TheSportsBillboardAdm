package com.valdroide.thesportsbillboardinstitution.splash.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.nhaarman.mockito_kotlin.whenever
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.main.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivity
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView
import kotlinx.android.synthetic.main.activity_splash.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import junit.framework.Assert.assertEquals
import org.mockito.Mock
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowActivity
import android.content.ComponentName
import android.content.Context
import com.nhaarman.mockito_kotlin.verify
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.MainActivity
import com.valdroide.thesportsbillboardinstitution.main.splash.SplashActivityInteractor
import com.valdroide.thesportsbillboardinstitution.main.splash.SplashActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main.splash.SplashActivityPresenterImpl
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import org.mockito.InjectMocks
import javax.inject.Inject
import kotlin.test.assertNotNull
import kotlin.test.assertNull


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
//@Config(constants = BuildConfig::class, sdk = intArrayOf(25), App)
class SplashActivityTest : BaseTest() {

    @Mock
    lateinit var event: SplashActivityEvent
    @Mock
    lateinit var presenter: SplashActivityPresenter
    @Mock
    lateinit var context: Context


    lateinit var view: SplashActivityView
    lateinit var activity: SplashActivity
    lateinit var controller: ActivityController<SplashActivity>
    lateinit var shadowActivity: ShadowActivity

    @Before
    override fun setUp() {
        super.setUp()

        val splashActivity = object : SplashActivity() {
            override fun getPresenter(): SplashActivityPresenter {
                return presenter
            }
        }

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), splashActivity as SplashActivity).create().visible()
        activity = controller.get()
        view = activity
        context = activity
        shadowActivity = Shadows.shadowOf(activity)
    }

    @Test
    fun onCreateTest() {
        verify(presenter).onCreate()
    }

    @Test
    fun onDestroyTest() {
        controller.destroy()
        verify(presenter).onDestroy()
    }

    @Test
    fun progressBarVisibilityTest() {
        val progressBar: ProgressBar = activity.progressBar
        val imageView: ImageView = activity.imageViewLogo
        assertEquals(View.VISIBLE, progressBar.visibility)
        assertEquals(View.VISIBLE, imageView.visibility)
    }
    @Test
    fun viewHideDialogProgressTest() {
        view.hideDialogProgress()
        val progressBar: ProgressBar = activity.progressBar
        assertEquals(View.INVISIBLE, progressBar.visibility)
    }

    @Test
    fun viewSetErrorTest() {
        whenever(event.error).thenReturn("Error")
        view.hideDialogProgress()
        view.setError(event.error!!)
        val progressBar: ProgressBar = activity.progressBar
        assertEquals(View.INVISIBLE, progressBar.visibility)
        assertEquals(event.error, "Error")
    }

    @Test
    fun viewGoToNavTest() {
        val progressBar: ProgressBar = activity.progressBar
        view.hideDialogProgress()
        view.goToNav()
        assertEquals(View.INVISIBLE, progressBar.visibility)
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(ComponentName(activity, MainActivity::class.java), intent.getComponent())
    }

    @Test
    fun goToLogTest() {
        val progressBar: ProgressBar = activity.progressBar
        view.hideDialogProgress()
        view.goToLog()
        assertEquals(View.INVISIBLE, progressBar.visibility)
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(ComponentName(activity, MainActivity::class.java), intent.getComponent())
    }

    @Test
    fun setDateNullTest() {
        whenever(event.dateData).thenReturn(null)
        val progressBar: ProgressBar = activity.progressBar
        view.setDate(event.dateData)
        assertNull(event.dateData)
        verify(presenter).getData(context)
        assertEquals(View.VISIBLE, progressBar.visibility)
    }

    @Test
    fun setDateNotNullTest() {
        var dateDataTest = DateData()
        dateDataTest.ID_DATE_DATA_KEY = 1
        dateDataTest.ACCOUNT_DATE = "2017"
        dateDataTest.UNIQUE_DATE = "2017"
        val progressBar: ProgressBar = activity.progressBar
        whenever(event.dateData).thenReturn(dateDataTest)
        view.setDate(event.dateData)
        assertNotNull(event.dateData)
        verify(presenter).validateDate(context, event.dateData!!)
        assertEquals(View.VISIBLE, progressBar.visibility)
    }
}
