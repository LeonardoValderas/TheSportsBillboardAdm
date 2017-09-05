package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture

import android.content.Context

interface FixtureFragmentInteractor {
    fun getFixtures(context: Context, id_submenu: Int, quantity: Int)
}