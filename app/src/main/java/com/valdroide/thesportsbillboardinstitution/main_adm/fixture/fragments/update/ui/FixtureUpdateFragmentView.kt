package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureUpdateFragmentView {
    fun setAllFixture(Fixtures: MutableList<Fixture>)
    fun setError(error: String)
    fun updateFixtureSuccess()
    fun deleteFixtureSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}