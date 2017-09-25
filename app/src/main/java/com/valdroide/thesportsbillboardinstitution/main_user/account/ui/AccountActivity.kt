package com.valdroide.thesportsbillboardinstitution.main_user.account.ui

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.account.AccountActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.account.di.AccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import kotlinx.android.synthetic.main.activity_account.*
import android.view.View
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.content_account.*
import android.support.v4.content.ContextCompat
import com.google.firebase.crash.FirebaseCrash
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import kotlinx.android.synthetic.main.activity_tab.*

open class AccountActivity : AppCompatActivity(), AccountActivityView, View.OnClickListener {

    private lateinit var presenter: AccountActivityPresenter
    private lateinit var component: AccountActivityComponent
    private var isRegister: Boolean = false
    private lateinit var account: Account
    private var number_phone: String = ""
    private var to_email: String = ""
    private var url_web: String = ""
    private var face_url: String = ""
    private var insta_url: String = ""

    companion object {
        const val PERMISSION_CALL: Int = 101
        const val HTTP: String = "http://"
        const val FACEBOOK_PACK = "https://www.facebook.com/"
        const val INSTAGRAM_PACK = "https://www.instagram.com/_u/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setupInjection()
        initToobar()
        register()
        setOnclikFabButton()
        getAccount()
    }

    private fun getAccount() {
        showProgressBar()
        presenter.getAccount(this)
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getAccountActivityComponent(this, this)
        presenter = getPresenter()
    }

    open fun getPresenter(): AccountActivityPresenter =
            component.getPresenter()


    override fun setAccount(account: Account) {
        this.account = account
        fillViews(account)
    }

    private fun fillViews(account: Account) {
        with(account) {
            Utils.setPicasso(applicationContext, URL_IMAGE, R.drawable.adeful, imageView)
            textViewDescription.text = DESCRIPTION
            textViewPhone.text = PHONE
            textViewEmail.text = EMAIL
            textViewWeb.text = WEB
            textViewFace.text = FACEBOOK
            textViewInsta.text = INSTAGRAM
        }
    }

    private fun setOnclikFabButton() {
        fabPhone.setOnClickListener(this)
        fabEmail.setOnClickListener(this)
        fabWeb.setOnClickListener(this)
        fabFace.setOnClickListener(this)
        fabInsta.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabPhone -> if (textViewPhone.text != null && textViewPhone.text.isNotEmpty()) {
                number_phone = textViewPhone.text.toString()
                onClickFabPhone()
            } else
                snackBarEmptyData()
            fabEmail -> if (textViewEmail.text != null && !textViewEmail.text.isEmpty()) {
                to_email = textViewEmail.text.toString()
                onClickFabEmail()
            } else
                snackBarEmptyData()
            fabWeb -> if (textViewWeb.text != null && !textViewWeb.text.isEmpty()) {
                url_web = HTTP + textViewWeb.text.toString().trim()
                if (validateUrl(url_web))
                    onClickFabWeb()
                else
                    Utils.showSnackBar(conteiner, getString(R.string.web_address_invalid))
            } else
                snackBarEmptyData()
            fabFace -> if (textViewFace.text != null && !textViewFace.text.isEmpty()) {
                face_url = FACEBOOK_PACK + textViewFace.text.toString()
                if (validateUrl(face_url))
                    onClickFabFace()
            } else
                snackBarEmptyData()
            fabInsta -> if (textViewInsta.text != null && !textViewInsta.text.isEmpty()) {
                insta_url = INSTAGRAM_PACK + textViewInsta.text.toString()
                if (validateUrl(insta_url))
                    onClickFabInsta()
            } else
                snackBarEmptyData()
        }
    }

    private fun validateUrl(url: String): Boolean = URLUtil.isValidUrl(url)

    private fun getRedSocial(url: String, isFace: Boolean) {
        try {
//            val packageManager = getPackageManager()
//            if (packageManager.getLaunchIntentForPackage(setPackageRed(isFace)) != null) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//            } else {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//            }
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    /*
        private fun setPackageRed(isFace: Boolean): String {
            var pack = ""
            when (isFace) {
                true -> pack = "com.facebook.katana"
                false -> pack = "com.instagram.android"
            }
            return pack
        }
    */
    private fun snackBarEmptyData() {
        Utils.showSnackBar(conteiner, getString(R.string.datp_empty_account))
    }

    override fun onClickFabPhone() {
        if (!Utils.oldPhones()) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            Utils.checkForPermission(this, permissionCheck, PERMISSION_CALL, Manifest.permission.CALL_PHONE)
        }
        if (Utils.hasPermission(this, Manifest.permission.CALL_PHONE)) {
            callPhone(number_phone)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_CALL -> callPhone(number_phone)
            }
        }
    }

    private fun callPhone(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
            startActivity(intent)
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    fun emailIntent(to: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:" + to)
            startActivity(intent)
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    override fun onClickFabEmail() {
        emailIntent(to_email)
    }

    override fun onClickFabWeb() {
        openWebURL(url_web)
    }

    fun openWebURL(inURL: String) {
        try {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse(inURL))
            startActivity(browse)
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    override fun onClickFabFace() {
        getRedSocial(face_url, true)
    }

    override fun onClickFabInsta() {
        getRedSocial(insta_url, false)
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
