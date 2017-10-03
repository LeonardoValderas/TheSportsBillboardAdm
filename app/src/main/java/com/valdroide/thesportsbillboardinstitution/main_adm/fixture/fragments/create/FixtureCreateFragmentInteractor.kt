package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureCreateFragmentInteractor {
    fun getPlayerForIdSubMenu(context: Context, id_submenu: Int)
    fun getFixture(context: Context, id_fixture: Int)
    fun saveFixture(context: Context, fixture: Fixture)
    fun updateFixture(context: Context, fixture: Fixture)
    fun getSpinnerData(context: Context)
}