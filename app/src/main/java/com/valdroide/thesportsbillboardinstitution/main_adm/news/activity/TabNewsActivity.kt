package com.valdroide.thesportsbillboardinstitution.main_adm.news.activity

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragment
import com.valdroide.thesportsbillboardinstitution.utils.BaseActivityTabs
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import kotlinx.android.synthetic.main.content_tab.*

open class TabNewsActivity : BaseActivityTabs(), Communicator {

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.create_title_tab), getString(R.string.edit_title_tab))
    override val fragments: Array<Fragment>
        get() = arrayOf(NewsCreateFragment(), NewsUpdateFragment())


    override fun refreshAdapter() {
        val fragment = getUpdateFragment()
        (fragment as NewsUpdateFragment).refreshAdapter()
    }

    override fun getUpdateFragment(): Fragment{
        val manager = supportFragmentManager
        return manager
                .findFragmentByTag("android:switcher:" + viewPager.id
                        + ":" + 1)
    }
}