package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.events.SanctionFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragmentView
import org.greenrobot.eventbus.Subscribe

class SanctionFragmentPresenterImpl(var view: SanctionFragmentView, val event: EventBus, val interactor: SanctionFragmentInteractor) : SanctionFragmentPresenter {


    override fun getViewPresenter(): SanctionFragmentView = view

    override fun setViewPresenter(view: SanctionFragmentView) {
       this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getSanctions(context: Context, id_submenu: Int) {
        interactor.getSanctions(context, id_submenu)
    }

    @Subscribe
    override fun onEventMainThread(event: SanctionFragmentEvent) {
        if (view != null) {
            when (event.type) {
                SanctionFragmentEvent.SANCTION -> {
                    view.hideSwipeRefreshLayout()
                    view.setSanctions(event.sanctions!!)
                }
                SanctionFragmentEvent.ERROR -> {
                    view.hideSwipeRefreshLayout()
                    view.setError(event.error!!)
                }
            }
        }
    }
}