package com.valdroide.thesportsbillboardinstitution.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.valdroide.thesportsbillboardinstitution.R
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_tab.*

abstract class BaseActivityTabs : AppCompatActivity() {

    var adapter: SectionsPagerAdapter? = null
    protected abstract val titles: Array<String>
    protected abstract val fragments: Array<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab)
        initToolBar()
        setPagerAdapter()
    }

    protected abstract fun getUpdateFragment(): Fragment
    //protected abstract fun getLayoutResourceId(): Int dont need in this case because is always same layout

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setPagerAdapter() {
        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
        Utils.setupViewpagerTabs(viewPager, tabs, adapter)
    }


}
