package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import com.valdroide.thesportsbillboardinstitution.R
import android.support.v4.view.ViewPager
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragment
import kotlinx.android.synthetic.main.content_tab.*
import kotlinx.android.synthetic.main.activity_tab.*
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.valdroide.thesportsbillboardinstitution.utils.Utils


open class TabActivity : AppCompatActivity() {

    var adapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        initToobar()
        setPagerAdapter()
        setupNavigation()
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    private fun initToobar(){
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        val title: String = Utils.getSubmenuTitle(this)
        getSupportActionBar()!!.setTitle(title)
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
        val titles = arrayOf(getString(R.string.news_title_tab), getString(R.string.fixture_title_tab),
                getString(R.string.table_title_tab), getString(R.string.sanstion_title_tab))
        val fragments = arrayOf(NewsFragment(), FixtureFragment(), LeaderBoardFragment(), SanctionFragment())
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
}
