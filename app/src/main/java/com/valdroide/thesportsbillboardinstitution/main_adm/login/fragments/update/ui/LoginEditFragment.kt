package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.login.activity.TabLoginActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.LoginEditFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.LoginEditFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.LoginEditFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_fixture.*

class LoginEditFragment : Fragment(), LoginEditFragmentView, OnItemClickListener {

    private lateinit var component: LoginEditFragmentComponent
    lateinit var presenter: LoginEditFragmentPresenter
    lateinit var adapterLogin: LoginEditFragmentAdapter
    var logins: MutableList<Login> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getLogins(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getLoginEditFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterLogin = getAdapter()
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

    open fun getPresenterInj(): LoginEditFragmentPresenter = component.getPresenter()


    open fun getAdapter(): LoginEditFragmentAdapter =
            component.getAdapter()

    override fun onClickEditLogin(position: Int, login: Login) {
        val i = Intent(activity, TabLoginActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_login", login.ID_LOGIN_KEY)
        startActivity(i)
    }

    override fun onClickActiveLogin(position: Int, login: Login) {
        presenter.activeOrUnActiveLogins(activity, login)
    }

    override fun onClickUnActiveLogin(position: Int, login: Login) {
        presenter.activeOrUnActiveLogins(activity, login)
    }

    override fun onClickDeleteLogin(position: Int, login: Login) {
        this.position = position
        showAlertDialog(activity.getString(R.string.alert_title), "Desea eliminar el login?", login)
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterLogin
        }
    }

    override fun setAllLogins(logins: MutableList<Login>) {
        this.logins = logins
        adapterLogin.setLogins(logins)
    }

    override fun updateLoginSuccess() {
        adapterLogin.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, activity.getString(R.string.update_success, "Login", "o"))
    }

    override fun deleteLoginSuccess() {
        adapterLogin.deleteLogin(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.login_delete_success))
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }

    fun showAlertDialog(title: String, msg: String, login: Login) {
        val alertDilog = AlertDialog.Builder(activity).create()
        alertDilog.setTitle(title)
        alertDilog.setMessage(msg)

        alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialogInterface, i ->
            presenter.deleteLogin(activity, login)
        })

        alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", { dialogInterface, j ->
            alertDilog.dismiss()
        })
        alertDilog.show()
    }

    private fun verifySwipeRefresh(show: Boolean) {
        try {
            if (swipeRefreshLayout != null) {
                if (show) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true)
                    }
                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false)
                    }
                }
            }
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayout.setOnRefreshListener({
                presenter.getLogins(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    fun refreshAdapter() {
        presenter.getLogins(activity)
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