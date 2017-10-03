package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events.FixtureUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureUpdateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): FixtureUpdateFragmentView
    fun setViewPresenter(view: FixtureUpdateFragmentView)
    fun getFixture(context: Context)
    fun deleteFixture(context: Context, fixture: Fixture)
    fun onEventMainThread(event: FixtureUpdateFragmentEvent)
}