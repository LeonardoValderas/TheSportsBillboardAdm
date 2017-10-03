package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface SanctionCreateFragmentView {
    fun onClickButtonSave()
    fun setSanctionUpdate(sanction: Sanction)
    fun setSubMenusAndPlayers(submenus: MutableList<SubMenuDrawer>, players: MutableList<Player>)
    fun setPlayersForId(players: MutableList<Player>)
    fun fillViewUpdate()
    fun saveSuccess()
    fun editSuccess()
    fun cleanViews()
    fun setVisibilityViews(isVisible: Int)
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
}