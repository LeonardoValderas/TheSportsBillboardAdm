package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionCreateFragmentInteractor {
    fun getPlayerForIdSubMenu(context: Context, id_submenu: Int)
    fun getSanction(context: Context, id_Sanction: Int)
    fun saveSanction(context: Context, sanction: Sanction)
    fun updateSanction(context: Context, sanction: Sanction)
    fun getSubMenusAndPlayers(context: Context)
}