package com.valdroide.thesportsbillboardinstitution.utils.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Spinner
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.adapter.TournamentSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.SectionsPagerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.generics.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_tab.*
import kotlinx.android.synthetic.main.content_tab.*

abstract class BaseActivityUserTabs : AppCompatActivity(){

    var adapter: SectionsPagerAdapter? = null
    var adapterSpinner: TournamentSpinnerAdapter? = null
    var positionViewPager: Int = 0
    private var isRegister: Boolean = false

    protected abstract val titles: Array<String>
    protected abstract val fragments: Array<Fragment>
    protected abstract val tournaments: MutableList<Tournament>
    protected abstract fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean)
    protected abstract fun setupInjection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_tab)
        setupInjection()
        validateEvenBusRegisterForLifeCycle(register())
        initToolBar()
        //setPagerAdapter()
    }

    protected abstract fun getUpdateFragment(): Fragment

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setPagerAdapter() {
        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
        ViewPagerAdapter.setupViewpagerTabs(viewPager, tabs, adapter)
    }

    fun setSpinnerAdapter(): TournamentSpinnerAdapter = TournamentSpinnerAdapter(tournaments, applicationContext)

    fun getSpinner(): Spinner {
        sp_tournament.adapter = setSpinnerAdapter()
        return sp_tournament
    }

    fun updateAdapterForTournament(){
        adapter?.notifyDataSetChanged()
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
