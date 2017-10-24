package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch

interface FixtureUpdateFragmentView {
    fun setAllFixture(fixtures: MutableList<Fixture>, times: MutableList<TimeMatch>)
    fun setError(error: String)
    fun updateFixtureSuccess(fixture: Fixture)
    fun deleteFixtureSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}