package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture

import android.content.Context

class FixtureFragmentInteractorImpl(val repository: FixtureFragmentRepository) : FixtureFragmentInteractor {

    override fun getFixtures(context: Context, id_submenu: Int, quantity: Int) {
        repository.getFixtures(context, id_submenu, quantity)
    }
}