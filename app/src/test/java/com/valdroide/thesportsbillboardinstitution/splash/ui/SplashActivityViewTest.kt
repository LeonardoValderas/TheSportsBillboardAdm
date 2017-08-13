package com.valdroide.thesportsbillboardinstitution.splash.ui

import android.view.View
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
import org.robolectric.annotation.Config
import junit.framework.Assert.assertEquals
import org.mockito.Mock

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23))
class SplashActivityViewTest : BaseTest() {

    lateinit var view: SplashActivityView

  //  lateinit var presenter: SplashActivityPresenter
    lateinit var activity: SplashActivity
      @Mock
    lateinit var event: SplashActivityEvent
    @Before
    override fun setUp() {
        super.setUp()
        activity = Robolectric.setupActivity(SplashActivity::class.java)
//        var activityController: SplashActivity = SplashActivity();
//        controller = ActivityController.of(Robolectric.getShadowsAdapter(), activityController).create().visible()
//        activity = controller.get()
        view = activity
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

    }


}
