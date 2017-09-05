package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView

interface FixtureFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): FixtureFragmentView
    fun setViewPresenter(view: FixtureFragmentView)
    fun getFixtures(context: Context, id_submenu: Int, quantity: Int)
    fun onEventMainThread(event: FixtureFragmentEvent)
}