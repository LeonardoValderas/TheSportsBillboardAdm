package com.valdroide.thesportsbillboardinstitution.main_user.tab.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TabActivityView {
    fun setTournaments(tournaments: MutableList<Tournament>)
    fun setError(msg: String)
    fun hideProgressBar()
    fun showProgressBar()
}