package com.valdroide.thesportsbillboardinstitution.main_adm.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.account.events.AccountAdmActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.account.ui.AccountAdmActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import org.greenrobot.eventbus.Subscribe

class AccountAdmActivityPresenterImpl(val view: AccountAdmActivityView, val event: EventBus, val interactor: AccountAdmActivityInteractor) : AccountAdmActivityPresenter {

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getAccount(context: Context) {
        interactor.getAccount(context)
    }

    override fun saveAccount(context: Context, account: Account) {
        interactor.saveAccount(context, account)
    }

    @Subscribe
    override fun onEventMainThread(event: AccountAdmActivityEvent) {
        when (event.type) {
            AccountAdmActivityEvent.ACCOUNT -> {
                view.setAccount(event.account!!)
                view.fillViews(event.account!!)
                view.hideProgressBar()
            }
            AccountAdmActivityEvent.SAVE -> {
                view.setEnableViews(false)
                view.menuVisibility(false, true)
                view.cleanViews()
                view.hideProgressBar()
                view.saveSuccess()
            }
            AccountAdmActivityEvent.ERROR -> {
                view.hideProgressBar()
                view.setError(event.error!!)
            }
        }
    }
}