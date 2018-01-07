package com.valdroide.thesportsbillboardinstitution.utils.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.BoringLayout
import com.valdroide.thesportsbillboardinstitution.R
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : AppCompatActivity() {

    private var isRegister: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        setupInjection()
        validateEvenBusRegisterForLifeCycle(register())
        initToolBar()
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean)
    protected abstract fun setupInjection()

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPause() {
        super.onPause()
        if (isRegister)
            validateEvenBusRegisterForLifeCycle(unregister())
//        mAd.pause(this)
//        if (mAdView != null) {
//            mAdView.pause()
//        }
    }

    override fun onResume() {
        super.onResume()
        if (!isRegister)
            validateEvenBusRegisterForLifeCycle(register())
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }

    override fun onStart() {
        super.onStart()
        if (!isRegister)
            validateEvenBusRegisterForLifeCycle(register())
    }

    override fun onStop() {
        super.onStop()
        if (isRegister)
            validateEvenBusRegisterForLifeCycle(unregister())
    }

    override fun onDestroy() {
        if (isRegister)
            validateEvenBusRegisterForLifeCycle(unregister())
        super.onDestroy()
    }

    fun register(): Boolean {
        isRegister = true
        return isRegister
    }

    fun unregister(): Boolean {
        isRegister = false
        return isRegister
    }
}