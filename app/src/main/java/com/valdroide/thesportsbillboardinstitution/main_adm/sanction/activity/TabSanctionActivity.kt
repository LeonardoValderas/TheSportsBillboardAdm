package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.activity

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui.SanctionCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragment
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivityTabs
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import kotlinx.android.synthetic.main.content_tab.*

open class TabSanctionActivity : BaseActivityTabs(), Communicator {

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.create_title_tab), getString(R.string.edit_title_tab))
    override val fragments: Array<Fragment>
        get() = arrayOf(SanctionCreateFragment(), SanctionUpdateFragment())

    override fun refreshAdapter() {
        val fragment = getUpdateFragment()
        (fragment as SanctionUpdateFragment).refreshAdapter()
    }

    override fun getUpdateFragment(): Fragment {
        val manager = supportFragmentManager
        return manager
                .findFragmentByTag("android:switcher:" + viewPager.id
                        + ":" + 1)
    }
}