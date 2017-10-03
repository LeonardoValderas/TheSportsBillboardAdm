package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

class FixtureUpdateFragmentInteractorImpl(val repository: FixtureUpdateFragmentRepository) : FixtureUpdateFragmentInteractor {


    override fun getFixture(context: Context) {
        repository.getFixture(context)
    }

    override fun deleteFixture(context: Context, fixture: Fixture) {
        repository.deleteFixture(context, fixture)
    }
}