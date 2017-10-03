package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.events.FixtureCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): FixtureCreateFragmentView
    fun setViewPresenter(view: FixtureCreateFragmentView)
    fun getFixture(context: Context, id_fixture: Int)
    fun saveFixture(context: Context, fixture: Fixture)
    fun updateFixture(context: Context, fixture: Fixture)
    fun getSpinnerData(context: Context)
    fun onEventMainThread(event: FixtureCreateFragmentEvent)
}