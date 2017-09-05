package com.valdroide.thesportsbillboardinstitution.main_user.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.account.events.AccountActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivityView
import org.greenrobot.eventbus.Subscribe

class AccountActivityPresenterImpl(val view: AccountActivityView, val event: EventBus, val interactor: AccountActivityInteractor) : AccountActivityPresenter {

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getAccount(context: Context) {
        interactor.getAccount(context)
    }

    @Subscribe
    override fun onEventMainThread(event: AccountActivityEvent) {
        if (view != null) {
            when (event.type) {
                AccountActivityEvent.ACCOUNT -> {
                    view.hideProgressBar()
                    view.setAccount(event.account!!)
                }
                AccountActivityEvent.ERROR -> {
                    view.hideProgressBar()
                    view.setError(event.error!!)
                }
            }
        }
    }
}