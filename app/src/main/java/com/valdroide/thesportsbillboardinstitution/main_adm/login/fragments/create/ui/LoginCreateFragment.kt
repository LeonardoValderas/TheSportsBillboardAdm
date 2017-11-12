package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.LoginCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_login_create.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.LoginCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction


class LoginCreateFragment : Fragment(), LoginCreateFragmentView {

    private lateinit var component: LoginCreateFragmentComponent
    private var isRegister: Boolean = false
    lateinit var presenter: LoginCreateFragmentPresenter
    lateinit private var communication: Communicator
    var login: Login = Login()
    var is_update: Boolean = false
    var id_login: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_login_create, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        isLoginUpdate()
        if (is_update)
            presenter.getLogin(activity, id_login)
        editTextObservables()
        buttonCreate.setOnClickListener({
            fillLogin()
        })
    }

    private fun editTextObservables() {
        val obUser = RxTextView.textChanges(editTextUser).skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
        val obPass = RxTextView.textChanges(editTextPass).skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)

        var isUser = false
        var isPass = false
        val isLoginEnabled: Observable<Boolean> = Observable.combineLatest(
                obUser,
                obPass,
                BiFunction { u, p ->
                    isUser = u.count() > 4
                    isPass = p.count() > 4
                    isUser && isPass
                })

        isLoginEnabled.observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ obBoolean ->

                    if (obBoolean)
                        buttonCreate.isEnabled = true
                    else {
                        buttonCreate.isEnabled = false
                        if (isUser == false)
                            editTextUser.error = getString(R.string.login_error_user)

                        if (isPass == false)
                            editTextPass.error = getString(R.string.login_error_pass)
                    }
                }, { e -> Utils.showSnackBar(conteiner, e.message.toString()) })
    }

    private fun fillLogin() {
        login.USER = editTextUser.text.toString()
        login.PASS = editTextPass.text.toString()
        login.TYPE_ADM = 1

        if (is_update) {
            login.ID_LOGIN_KEY = id_login
            editLogin(login)
        } else
            saveLogin(login)
    }

    private fun saveLogin(login: Login) {
        presenter.saveLogin(activity, login)
    }

    private fun editLogin(login: Login) {
        presenter.editLogin(activity, login)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getLoginCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
    }

    private fun getPresenterInj(): LoginCreateFragmentPresenter =
            component.getPresenter()

    private fun isLoginUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_login = activity.intent.getIntExtra("id_login", 0)
            buttonCreate.text = "Editar"
        }
    }

    fun register() {
        if (!isRegister) {
            presenter.onCreate()
            isRegister = true
        }
    }

    fun unregister() {
        if (isRegister) {
            presenter.onDestroy()
            isRegister = false
        }
    }

    override fun setLoginEdit(login: Login) {
        this.login = login
        filEditText(login)
    }

    private fun filEditText(login: Login) {
        with(login) {
            id_login = ID_LOGIN_KEY
            editTextUser.text = Editable.Factory.getInstance().newEditable(USER)
            editTextPass.text = Editable.Factory.getInstance().newEditable(PASS)
        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.login_save_success))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.login_edit_success))
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideProgressDialog() {
        progressBar.visibility = View.GONE
        relativeBody.visibility = View.VISIBLE
    }

    override fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
        relativeBody.visibility = View.GONE
    }

    override fun cleanViews() {
        login = Login()
        editTextUser.text.clear()
        editTextPass.text.clear()
        buttonCreate.text = "Crear"
        buttonCreate.isEnabled = false
        is_update = false
    }

    override fun onPause() {
        unregister()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        register()
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    override fun onStop() {
        unregister()
        super.onStop()
    }

    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}
