package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface FixtureUpdateFragmentInteractor {
    fun getFixture(context: Context)
    fun deleteFixture(context: Context, fixture: Fixture)
 }