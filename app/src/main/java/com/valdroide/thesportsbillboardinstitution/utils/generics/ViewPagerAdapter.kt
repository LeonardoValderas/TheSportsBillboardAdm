package com.valdroide.thesportsbillboardinstitution.utils.generics

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.valdroide.thesportsbillboardinstitution.utils.SectionsPagerAdapter

object ViewPagerAdapter {
    fun setupViewpagerTabs(viewPager: ViewPager, tabs: TabLayout, adapter: SectionsPagerAdapter?): ViewPager {
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
        return viewPager
    }
}