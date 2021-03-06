package com.valdroide.thesportsbillboardinstitution.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager,
                           private val titles: Array<String>,
                           private val fragments: Array<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence = if(titles.isNotEmpty()) titles[position] else ""
}