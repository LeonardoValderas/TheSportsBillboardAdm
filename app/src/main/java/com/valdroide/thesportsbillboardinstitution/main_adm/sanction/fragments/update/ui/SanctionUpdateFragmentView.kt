package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction


interface SanctionUpdateFragmentView {
    fun setAllSanction(sanctions: MutableList<Sanction>)
    fun setError(error: String)
    fun updateSanctionSuccess()
    fun deleteSanctionSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}