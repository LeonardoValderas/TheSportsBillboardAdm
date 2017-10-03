package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

class SanctionUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var sanctions: MutableList<Sanction>? = null

    companion object {
        const val SANCTIONS: Int = 0
        const val DELETE: Int =  1
        const val ERROR: Int =  2
    }
}