package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class NewsCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var subMenuDrawers: MutableList<SubMenuDrawer>? = null
    var news: News? = null

    companion object {
        const val GETSUBMENU: Int = 0
        const val SAVENEWS: Int = 1
        const val UPDATENEWS: Int = 2
        const val GETNEWS: Int = 3
        const val ERROR: Int = 4
    }
}