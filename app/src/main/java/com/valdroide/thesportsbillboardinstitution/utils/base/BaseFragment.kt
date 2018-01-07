package com.valdroide.thesportsbillboardinstitution.utils.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.utils.Communicator

abstract class BaseFragment : Fragment() {

    private var isRegister: Boolean = false
    private lateinit var communicator: Communicator

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(getLayoutResourceId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        setCommunicator(activity as Communicator)
        validateEvenBusRegisterForLifeCycle(register())
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean)
    protected abstract fun setCommunicator(communicator: Communicator)
    protected abstract fun setupInjection()


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