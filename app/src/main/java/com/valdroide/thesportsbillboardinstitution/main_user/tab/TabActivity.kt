package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.os.Bundle
import android.support.v4.app.Fragment

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragment
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import kotlinx.android.synthetic.main.activity_tab.*
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivityTabs


open class TabActivity : BaseActivityTabs() {

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.news_title_tab), getString(R.string.fixture_title_tab),
                getString(R.string.table_title_tab), getString(R.string.sanction_title_tab))
    override val fragments: Array<Fragment>
        get() = arrayOf(NewsFragment(), FixtureFragment(), LeaderBoardFragment(), SanctionFragment())

    override fun getUpdateFragment(): Fragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToobar()
    }

    private fun initToobar(){
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        val title: String = SharedHelper.getSubmenuTitle(this)
        getSupportActionBar()!!.setTitle(title)
    }
//      SAN ESTEBAN
//    private fun setPagerAdapter() {
//        val titles = arrayOf(getString(R.string.news_title_tab), getString(R.string.fixture_title_tab),
//                getString(R.string.table_title_tab), getString(R.string.sanstion_title_tab), getString(R.string.player_title_tab))
//        val fragments = arrayOf(NewsFragment(), FixtureFragment(), LeaderBoardFragment(), SanctionFragment(), PlayerFragment())
//        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
//    }

}
