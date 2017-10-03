package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.events.SanctionCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui.SanctionCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): SanctionCreateFragmentView
    fun setViewPresenter(view: SanctionCreateFragmentView)
    fun getPlayerForIdSubMenu(context: Context, id_submenu: Int)
    fun getSanction(context: Context, id_sanction: Int)
    fun saveSanction(context: Context, sanction: Sanction)
    fun updateSanction(context: Context, sanction: Sanction)
    fun getSubMenusAndPlayers(context: Context)
    fun onEventMainThread(event: SanctionCreateFragmentEvent)
}