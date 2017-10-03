package com.valdroide.thesportsbillboardinstitution.main_adm.news.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragment
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_tab.*

open class TabNewsActivity : AppCompatActivity(), Communicator {

    var adapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        initToobar()
        setPagerAdapter()
        setupNavigation()
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //   val title: String = Utils.getSubmenuTitle(this)
        // getSupportActionBar()!!.setTitle(title)
    }

    //      SAN ESTEBAN
//    private fun setPagerAdapter() {
//        val titles = arrayOf(getString(R.string.news_title_tab), getString(R.string.fixture_title_tab),
//                getString(R.string.table_title_tab), getString(R.string.sanstion_title_tab), getString(R.string.player_title_tab))
//        val fragments = arrayOf(NewsFragment(), FixtureFragment(), LeaderBoardFragment(), SanctionFragment(), PlayerFragment())
//        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
//    }
//     ADEFUL
    private fun setPagerAdapter() {
        val titles = arrayOf(getString(R.string.create_title_tab), getString(R.string.edit_title_tab))
        val fragments = arrayOf(NewsCreateFragment(), NewsUpdateFragment())
        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
    }

    private fun setupNavigation() {
        viewPager.setAdapter(adapter)
        viewPager.setPageTransformer(true, RotateUpTransformer())
        tabs.setupWithViewPager(viewPager)
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //              hideKeyBoard()
            }

            override fun onPageSelected(position: Int) {
                //            hideKeyBoard()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun refreshAdapter() {
        getNewsUpdateFragment().refreshAdapter()
    }

    fun getNewsUpdateFragment(): NewsUpdateFragment {
        val manager = supportFragmentManager
        return manager
                .findFragmentByTag("android:switcher:" + viewPager.id
                        + ":" + 1) as NewsUpdateFragment
    }
}