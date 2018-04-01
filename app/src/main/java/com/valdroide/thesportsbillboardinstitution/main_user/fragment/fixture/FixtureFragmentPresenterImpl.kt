package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView
import org.greenrobot.eventbus.Subscribe

class FixtureFragmentPresenterImpl(var view: FixtureFragmentView, val event: EventBus, val interactor: FixtureFragmentInteractor) : FixtureFragmentPresenter {

    override fun getViewPresenter(): FixtureFragmentView = view

    override fun setViewPresenter(view: FixtureFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getFixtures(context: Context, id_submenu: Int, quantity: Int) {
        interactor.getFixtures(context, id_submenu, quantity)
    }

    @Subscribe
    override fun onEventMainThread(event: FixtureFragmentEvent) {
        //   if (view != null) {
        when (event.type) {
            FixtureFragmentEvent.FIXTURES -> {
                view.hideSwipeRefreshLayout()
                view.setFixture(event.fixtures!!)
            }
            FixtureFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
        // }
    }
}