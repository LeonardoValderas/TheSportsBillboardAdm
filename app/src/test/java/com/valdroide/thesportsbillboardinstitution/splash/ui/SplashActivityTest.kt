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
import com.valdroide.thesportsbillboardinstitution.main.MainActivity


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23))
class SplashActivityTest : BaseTest() {

    lateinit var view: SplashActivityView
    @Mock
    lateinit var event: SplashActivityEvent
  //  lateinit var presenter: SplashActivityPresenter
    lateinit var activity: SplashActivity
    lateinit var controller: ActivityController<SplashActivity>
    lateinit var shadowActivity: ShadowActivity

    @Before
    override fun setUp() {
        super.setUp()
      //  activity = Robolectric.setupActivity(SplashActivity::class.java)
        var activityController: SplashActivity = SplashActivity();
        controller = ActivityController.of(Robolectric.getShadowsAdapter(), activityController).create().visible()
        activity = controller.get()
        view = activity
        shadowActivity = Shadows.shadowOf(activity)
    }

    @Test
    fun SplashActivityProgressBarVisibilityTest() {
        var progressBar: ProgressBar = activity.progressBar
        var imageView: ImageView = activity.imageViewLogo
        assertEquals(View.VISIBLE, progressBar.visibility)
        assertEquals(View.VISIBLE, imageView.visibility)
    }
    @Test
    fun SplashActivityViewHideDialogProgressTest() {
        view.hideDialogProgress()
        var progressBar: ProgressBar = activity.progressBar
        assertEquals(View.INVISIBLE, progressBar.visibility)
    }

    @Test
    fun SplashActivityViewSetErrorTest() {
        whenever(event.error).thenReturn("Error")
        view.setError(event.error)
        assertEquals(event.error, "Error")
    }

    @Test
    fun SplashActivityViewGoToNavTest() {
        view.goToNav()
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(ComponentName(activity, MainActivity::class.java), intent.getComponent())
    }

    @Test
    fun SplashActivityViewGoToLogTest() {
        view.goToLog()
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(ComponentName(activity, MainActivity::class.java), intent.getComponent())
    }
}
