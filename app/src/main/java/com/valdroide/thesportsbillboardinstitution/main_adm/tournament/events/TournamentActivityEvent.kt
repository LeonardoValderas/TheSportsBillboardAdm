package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events

import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

/**
 var b: String? = "abc" signific that accept null, can call NPE
 b = null // ok
 b?.lenght - > it means that validate if is null. It's same to if(b != null)
 b?.length ?: -1  -> short condition
 b!!.length doesn't realice a validation, so it can throw a NPE
 */

open class TournamentActivityEvent {
    var type: Int = 0
    var msg: String? = null
    var subMenuDrawer: SubMenuDrawer? = null
    var subMenuDrawers: MutableList<SubMenuDrawer>? = null
    var tournament: Tournament? = null
    var tournaments: MutableList<Tournament>? = null
    var isActual: Boolean = false

    companion object {
        const val GETSUBMENUSTORNAMENTS: Int = 0
        const val GETSUBMENUSFORIDTOURNAMENTS: Int = 1
        const val EVENTTORNAMENTSUCCESS: Int =  2
        const val EVENTASSIGNATIONSUCCESS: Int =  3
        const val ERROR: Int =  4
    }
}