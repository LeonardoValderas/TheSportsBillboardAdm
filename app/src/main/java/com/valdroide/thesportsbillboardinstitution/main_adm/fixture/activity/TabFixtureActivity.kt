package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.activity

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragment
import com.valdroide.thesportsbillboardinstitution.utils.BaseActivityTabs
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import kotlinx.android.synthetic.main.content_tab.*

open class TabFixtureActivity : BaseActivityTabs(), Communicator {

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.create_title_tab), getString(R.string.edit_title_tab))
    override val fragments: Array<Fragment>
        get() = arrayOf(FixtureCreateFragment(), FixtureUpdateFragment())

    override fun refreshAdapter() {
        val fragment = getUpdateFragment()
        (fragment as FixtureUpdateFragment).refreshAdapter()
    }

    override fun getUpdateFragment(): Fragment {
        val manager = supportFragmentManager
        return manager
                .findFragmentByTag("android:switcher:" + viewPager.id
                        + ":" + 1)
    }
}