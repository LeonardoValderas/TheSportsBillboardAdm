package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events.FixtureUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class FixtureUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : FixtureUpdateFragmentRepository {

    private var fixtures: MutableList<Fixture>? = null
    private var response: WSResponse? = null

    override fun getFixture(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getFixtures()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    fixtures = result.fixtures
                                    post(FixtureUpdateFragmentEvent.FIXTURES, fixtures!!)
                                } else {
                                    post(FixtureUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureUpdateFragmentEvent.ERROR, e.message)
        }
    }

    override fun deleteFixture(context: Context, fixture: Fixture) {
        try {
            apiService.deleteFixture(fixture.ID_FIXTURE_KEY, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(FixtureUpdateFragmentEvent.DELETE)
                                } else {
                                    post(FixtureUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureUpdateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, Fixtures: MutableList<Fixture>) {
        post(types, Fixtures, null)
    }

    fun post(types: Int, Fixtures: MutableList<Fixture>?, error: String?) {
        val event = FixtureUpdateFragmentEvent()
        event.type = types
        event.Fixtures = Fixtures
        event.error = error
        eventBus.post(event)
    }
}