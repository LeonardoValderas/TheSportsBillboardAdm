package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureFragmentView {
    fun setFixture(fixtures: MutableList<Fixture>)
    fun setError(error: String)
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}