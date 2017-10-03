package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

class FixtureCreateFragmentInteractorImpl(val repository: FixtureCreateFragmentRepository) : FixtureCreateFragmentInteractor {


    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int){
        repository.getPlayerForIdSubMenu(context, id_submenu)
    }

    override fun getFixture(context: Context, id_fixture: Int) {
        repository.getFixture(context, id_fixture)
    }

    override fun saveFixture(context: Context, fixture: Fixture) {
        repository.saveFixture(context, fixture)
    }

    override fun updateFixture(context: Context, fixture: Fixture) {
        repository.updateFixture(context, fixture)
    }

    override fun getSpinnerData(context: Context) {
        repository.getSpinnerData(context)
    }
}