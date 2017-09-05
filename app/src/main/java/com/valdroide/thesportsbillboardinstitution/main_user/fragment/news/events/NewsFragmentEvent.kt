package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.events

import com.valdroide.thesportsbillboardinstitution.model.entities.News

class NewsFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var news: MutableList<News>? = null

    companion object {
        const val NEWS: Int = 0
        const val ERROR: Int =  1
    }
}