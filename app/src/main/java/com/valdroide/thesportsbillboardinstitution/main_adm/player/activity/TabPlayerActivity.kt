package com.valdroide.thesportsbillboardinstitution.main_adm.player.activity

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragment
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivityTabs
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import kotlinx.android.synthetic.main.content_tab.*

open class TabPlayerActivity : BaseActivityTabs(), Communicator {

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.create_title_tab), getString(R.string.edit_title_tab))
    override val fragments: Array<Fragment>
        get() = arrayOf(PlayerCreateFragment(), PlayerUpdateFragment())

    override fun refreshAdapter() {
       val fragment = getUpdateFragment()
        (fragment as PlayerUpdateFragment).refreshAdapter()
    }

    override fun getUpdateFragment(): Fragment{
        val manager = supportFragmentManager
        return manager
                .findFragmentByTag("android:switcher:" + viewPager.id
                        + ":" + 1)
    }
}