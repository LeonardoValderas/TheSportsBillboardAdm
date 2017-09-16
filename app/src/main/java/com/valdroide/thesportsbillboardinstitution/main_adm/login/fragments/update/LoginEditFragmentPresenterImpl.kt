package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.events.LoginEditFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import org.greenrobot.eventbus.Subscribe

class LoginEditFragmentPresenterImpl(var view: LoginEditFragmentView, val event: EventBus, val interactor: LoginEditFragmentInteractor) : LoginEditFragmentPresenter {


    override fun getViewPresenter(): LoginEditFragmentView = view

    override fun setViewPresenter(view: LoginEditFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getLogins(context: Context) {
        interactor.getLogins(context)
    }

    override fun deleteLogin(context: Context, login: Login) {
        interactor.deleteLogin(context, login)
    }

    override fun activeOrUnActiveLogins(context: Context, login: Login) {
        interactor.activeOrUnActiveLogins(context, login)
    }

    @Subscribe
    override fun onEventMainThread(event: LoginEditFragmentEvent) {
        when (event.type) {
            LoginEditFragmentEvent.LOGINS-> {
                view.hideSwipeRefreshLayout()
                view.setAllLogins(event.logins!!)
            }
            LoginEditFragmentEvent.EDIT -> {
                view.hideSwipeRefreshLayout()
                view.updateLoginSuccess()
            }
            LoginEditFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deleteLoginSuccess()
            }
            LoginEditFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}