package com.valdroide.thesportsbillboardinstitution.main_user.splash.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.NavigationActivity
import com.valdroide.thesportsbillboardinstitution.main_user.splash.SplashActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.SplashActivityComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*


open class SplashActivity : AppCompatActivity(), SplashActivityView {

    private lateinit var presenter: SplashActivityPresenter
    private lateinit var component: SplashActivityComponent
    private var isRegister: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
       // ButterKnife.bind(this);
        setupInjection()
        register()
        getDate();
    }

    fun getDate() {
        presenter.getDate(this)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getSplashActivityComponent(this, this)
        presenter = getPresenter()
    }

    override fun hideDialogProgress() {
        progressBar.visibility = View.INVISIBLE;
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun goToNav() {
        startActivity(Intent(this, NavigationActivity::class.java))
    }

    override fun goToLog() {
     //   startActivity(Intent(this, MainActivity::class.java))
    }

    override fun setDate(dateData: DateData?) {
        if (dateData == null)
            presenter.getData(this)
        else
            presenter.validateDate(this, dateData)
    }

    open fun getPresenter(): SplashActivityPresenter =
         component.getPresenter()


    fun register() {
        if (!isRegister) {
            presenter.onCreate()
            isRegister = true
        }
    }

    fun unregister() {
        if (isRegister) {
            presenter.onDestroy()
            isRegister = false
        }
    }

    override fun onPause() {
        super.onPause()
        unregister()
//        mAd.pause(this)
//        if (mAdView != null) {
//            mAdView.pause()
//        }
    }

    override fun onResume() {
        super.onResume()
        register()
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    override fun onStop() {
        super.onStop()
        unregister()
    }


    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}
