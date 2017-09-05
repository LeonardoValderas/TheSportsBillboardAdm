package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionFragmentView {
    fun setSanctions(sactions: MutableList<Sanction>)
    fun setError(error: String)
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}