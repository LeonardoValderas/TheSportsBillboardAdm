package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

class SanctionFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var sanctions: MutableList<Sanction>? = null

    companion object {
        const val SANCTION: Int = 0
        const val ERROR: Int =  1
    }
}