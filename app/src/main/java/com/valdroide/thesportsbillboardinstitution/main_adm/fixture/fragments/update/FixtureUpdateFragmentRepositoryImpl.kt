package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events.FixtureUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class FixtureUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : FixtureUpdateFragmentRepository {
    private var fixtures: MutableList<Fixture>? = null
    private var times: MutableList<TimeMatch>? = null
    private var response: WSResponse? = null
    private var id_user = 1

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
                                    times = result.timeMatchs
                                    post(FixtureUpdateFragmentEvent.FIXTURES, fixtures!!, times!!)
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

    override fun setResultFixture(context: Context, fixture: Fixture) {
        if (id_user != 0) {
            try {
                apiService.updateResultFixture(fixture.ID_FIXTURE_KEY, fixture.RESULT_LOCAL, fixture.RESULT_VISITE,
                        fixture.ID_TIMES_MATCH, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(FixtureUpdateFragmentEvent.UPDATE, fixture)
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
        } else {
            post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun deleteFixture(context: Context, fixture: Fixture) {
        if (id_user != 0) {
            try {
                apiService.deleteFixture(fixture.ID_FIXTURE_KEY, id_user, Utils.getFechaOficialSeparate())
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
        } else {
            post(FixtureUpdateFragmentEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    fun post(types: Int) {
        post(types, null, null, null, null)
    }

    fun post(types: Int, fixture: Fixture?) {
        post(types, null, null, fixture, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, null, null, error)
    }

    fun post(types: Int, fixtures: MutableList<Fixture>, times: MutableList<TimeMatch>) {
        post(types, fixtures, times, null, null)
    }

    fun post(types: Int, fixtures: MutableList<Fixture>?, times: MutableList<TimeMatch>?, fixture: Fixture?, error: String?) {
        val event = FixtureUpdateFragmentEvent()
        event.type = types
        event.fixtures = fixtures
        event.times = times
        event.fixture = fixture
        event.error = error
        eventBus.post(event)
    }
}