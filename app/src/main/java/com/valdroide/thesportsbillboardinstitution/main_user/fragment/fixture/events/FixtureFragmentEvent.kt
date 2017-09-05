package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

class FixtureFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var fixtures: MutableList<Fixture>? = null

    companion object {
        const val FIXTURES: Int = 0
        const val ERROR: Int =  1
    }
}