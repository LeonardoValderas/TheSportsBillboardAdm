package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.events.LoginCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui.LoginCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import org.greenrobot.eventbus.Subscribe

class LoginCreateFragmentPresenterImpl(var view: LoginCreateFragmentView, val event: EventBus, val interactor: LoginCreateFragmentInteractor) : LoginCreateFragmentPresenter {

    override fun getViewPresenter(): LoginCreateFragmentView = view

    override fun setViewPresenter(view: LoginCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getLogin(context: Context, id_login: Int) {
        interactor.getLogin(context, id_login)
    }

    override fun saveLogin(context: Context, login: Login) {
        interactor.saveLogin(context, login)
    }

    override fun editLogin(context: Context, login: Login) {
        interactor.editLogin(context, login)
    }


    @Subscribe
    override fun onEventMainThread(event: LoginCreateFragmentEvent) {
        when (event.type) {
            LoginCreateFragmentEvent.GETLOGIN -> {
                view.hideProgressDialog()
                view.setLoginEdit(event.login!!)
            }
            LoginCreateFragmentEvent.SAVELOGIN -> {
                view.hideProgressDialog()
                view.saveSuccess()
                view.cleanViews()
            }
            LoginCreateFragmentEvent.EDITLOGIN -> {
                view.hideProgressDialog()
                view.editSuccess()
                view.cleanViews()
            }
            LoginCreateFragmentEvent.ERROR -> {
                view.hideProgressDialog()
                view.setError(event.error!!)
            }
        }
    }
}