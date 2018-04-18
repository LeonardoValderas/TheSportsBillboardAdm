package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.tab.events.TabActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.TabActivityView
import org.greenrobot.eventbus.Subscribe

class TabActivityPresenterImpl(private val view: TabActivityView?, private val eventBus: EventBus, private val interactor: TabActivityInteractor): TabActivityPresenter {


    override fun onCreate() {
        eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
    }

    override fun getTournamentoForIdSubmenu(context: Context, id_menu: Int) {
       interactor.getTournamentoForIdSubmenu(context, id_menu)
    }

    @Subscribe
    override fun onEventMainThread(event: TabActivityEvent) {
        if (this.view != null) {
            when (event.type) {
                TabActivityEvent.GET_TOURNAMENT-> {
                    view.setTournaments(event.tournaments!!)
                    view.hideProgressBar()
                }
                TabActivityEvent.ERROR -> {
                    view.hideProgressBar()
                    view.setError(event.error!!)
                }
            }
        }
    }
}