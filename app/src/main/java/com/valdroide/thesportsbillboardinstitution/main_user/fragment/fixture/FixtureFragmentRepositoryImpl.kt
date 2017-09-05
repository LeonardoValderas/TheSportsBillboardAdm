package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class FixtureFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : FixtureFragmentRepository {
    private var fixtures: MutableList<Fixture>? = null
    private var response: WSResponse? = null

    override fun getFixtures(context: Context, id_submenu: Int, quantity: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getFixtureForDivision(id_submenu, quantity)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    fixtures = result.fixtures
                                    post(FixtureFragmentEvent.FIXTURES, fixtures!!)

                                } else {
                                    post(FixtureFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, fixtures: MutableList<Fixture>) {
        post(types, fixtures, null)
    }

    fun post(types: Int, fixtures: MutableList<Fixture>?, error: String?) {
        val event = FixtureFragmentEvent()
        event.type = types
        event.fixtures = fixtures
        event.error = error
        eventBus.post(event)
    }
}