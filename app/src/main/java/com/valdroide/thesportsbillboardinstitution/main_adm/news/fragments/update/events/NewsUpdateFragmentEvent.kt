package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.News

class NewsUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var news: MutableList<News>? = null

    companion object {
        const val NEWS: Int = 0
        const val UPDATE: Int =  1
        const val DELETE: Int =  2
        const val ERROR: Int =  3
    }
}