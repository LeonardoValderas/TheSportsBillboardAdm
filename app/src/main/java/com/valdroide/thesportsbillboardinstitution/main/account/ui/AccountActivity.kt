package com.valdroide.thesportsbillboardinstitution.main.account.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main.account.AccountActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main.account.di.AccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import kotlinx.android.synthetic.main.activity_account.*
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.content_account.*
import android.R.id.toggle
import android.widget.Switch
import android.widget.Toast


class AccountActivity : AppCompatActivity(), AccountActivityView, View.OnClickListener {

    private lateinit var presenter: AccountActivityPresenter
    private lateinit var component: AccountActivityComponent
    private var isRegister: Boolean = false
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)
        setupInjection()
        register()
        setOnclikFabButton()
        showProgressBar()
        presenter.getAccount(this)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getAccountActivityComponent(this, this)
        presenter = getPresenter()
    }

    fun getPresenter(): AccountActivityPresenter {
        return component.getPresenter()
    }

    override fun setAccount(account: Account) {
        this.account = account
        fillViews(account)
    }

    private fun fillViews(account: Account){
        Utils.setPicasso(this, account.URL_IMAGE, R.mipmap.ic_launcher, imageView)
        textViewDescription.text = account.DESCRIPTION
        textViewPhone.text = account.PHONE
        textViewEmail.text = account.EMAIL
        textViewWeb.text = account.WEB
        textViewFace.text = account.FACEBOOK
        textViewInsta.text = account.INSTAGRAM
    }

    private fun setOnclikFabButton(){
        fabPhone.setOnClickListener(this)
        fabEmail.setOnClickListener(this)
        fabWeb.setOnClickListener(this)
        fabFace.setOnClickListener(this)
        fabInsta.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v) {
            fabPhone -> onClickFabPhone()
            fabEmail -> onClickFabEmail()
            fabWeb -> onClickFabWeb()
            fabFace -> onClickFabFace()
            fabInsta -> onClickFabInsta()
        }
    }

    override fun onClickFabPhone() {
        Utils.showSnackBar(conteiner, "onClickFabPhone")
    }

    override fun onClickFabEmail() {
        Utils.showSnackBar(conteiner, "onClickFabEmail")
    }

    override fun onClickFabWeb() {
        Utils.showSnackBar(conteiner, "onClickFabWeb")
    }

    override fun onClickFabFace() {
        Utils.showSnackBar(conteiner, "onClickFabFace")
    }

    override fun onClickFabInsta() {
        Utils.showSnackBar(conteiner, "onClickFabInsta")
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        linearConteiner.visibility = View.VISIBLE
        fabConteiner.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        linearConteiner.visibility = View.INVISIBLE
        fabConteiner.visibility = View.INVISIBLE
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

    override fun onPause() {
        super.onPause()
        unregister()
//        mAd.pause(this)
//        if (mAdView != null) {
//            mAdView.pause()
//        }
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
        super.onStop()
        unregister()
    }


    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}
