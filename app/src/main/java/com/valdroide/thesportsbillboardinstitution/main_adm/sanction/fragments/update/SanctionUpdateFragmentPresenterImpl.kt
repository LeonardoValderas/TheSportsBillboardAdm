package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.events.SanctionUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import org.greenrobot.eventbus.Subscribe

class SanctionUpdateFragmentPresenterImpl(var view: SanctionUpdateFragmentView, val event: EventBus, val interactor: SanctionUpdateFragmentInteractor) : SanctionUpdateFragmentPresenter {

    override fun getViewPresenter(): SanctionUpdateFragmentView = view

    override fun setViewPresenter(view: SanctionUpdateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getSanction(context: Context) {
        interactor.getSanction(context)
    }

    override fun deleteSanction(context: Context, sanction: Sanction) {
        interactor.deleteSanction(context, sanction)
    }

    @Subscribe
    override fun onEventMainThread(event: SanctionUpdateFragmentEvent) {
        when (event.type) {
            SanctionUpdateFragmentEvent.SANCTIONS -> {
                view.hideSwipeRefreshLayout()
                view.setAllSanction(event.sanctions!!)
            }
            SanctionUpdateFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deleteSanctionSuccess()
            }
            SanctionUpdateFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}