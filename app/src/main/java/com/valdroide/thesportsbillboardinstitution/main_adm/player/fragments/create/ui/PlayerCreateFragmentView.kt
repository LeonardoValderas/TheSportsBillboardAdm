package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface PlayerCreateFragmentView {
    fun onClickPhoto()
    fun onClickButtonSave()
    fun setPlayerUpdate(player: Player)
    fun setPositionsAndSubMenus(positions: MutableList<Position>, submenus: MutableList<SubMenuDrawer>)
    fun fillViewUpdate()
    fun savePlayerSuccess()
    fun editPlayerSuccess()
    fun savePositionSuccess(position: Position)
    fun editPositionSuccess(position: Position)
    fun cleanViews()
    fun setVisibilityViews(isVisible: Int)
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
    fun refreshAdapter()
}