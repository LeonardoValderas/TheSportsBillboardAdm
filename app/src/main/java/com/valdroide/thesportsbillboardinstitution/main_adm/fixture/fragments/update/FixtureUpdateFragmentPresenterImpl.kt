package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events.FixtureUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import org.greenrobot.eventbus.Subscribe

class FixtureUpdateFragmentPresenterImpl(var view: FixtureUpdateFragmentView, val event: EventBus, val interactor: FixtureUpdateFragmentInteractor) : FixtureUpdateFragmentPresenter {

    override fun getViewPresenter(): FixtureUpdateFragmentView = view

    override fun setViewPresenter(view: FixtureUpdateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getFixture(context: Context) {
        interactor.getFixture(context)
    }

    override fun setResultFixture(context: Context, fixture: Fixture) {
        view.showSwipeRefreshLayout()
        interactor.setResultFixture(context, fixture)
    }

    override fun deleteFixture(context: Context, fixture: Fixture) {
        view.showSwipeRefreshLayout()
        interactor.deleteFixture(context, fixture)
    }

    @Subscribe
    override fun onEventMainThread(event: FixtureUpdateFragmentEvent) {
        when (event.type) {
            FixtureUpdateFragmentEvent.FIXTURES -> {
                view.hideSwipeRefreshLayout()
                view.setAllFixture(event.fixtures!!, event.times!!)
            }
            FixtureUpdateFragmentEvent.UPDATE-> {
                view.updateFixtureSuccess(event.fixture!!)
                view.hideSwipeRefreshLayout()
            }
            FixtureUpdateFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deleteFixtureSuccess()
            }
            FixtureUpdateFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}