package com.valdroide.thesportsbillboardinstitution.main_adm.account.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.account.AccountAdmActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.account.di.AccountAdmActivityComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_adm_account.*
import kotlinx.android.synthetic.main.content_adm_account.*
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_tab.*
import java.io.IOException


open class AccountAdmActivity : AppCompatActivity(), AccountAdmActivityView {

    private lateinit var presenter: AccountAdmActivityPresenter
    private lateinit var component: AccountAdmActivityComponent
    private var isRegister: Boolean = false
    private lateinit var account: Account
    private var imageByte: ByteArray? = null
    private var uriExtra: Uri? = null
    private var menu: Menu? = null
    private var encode: String = ""
    private var name_image: String = ""
    private var name_before: String = ""
    private var url_image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_account)
        setupInjection()
        initToobar()
        register()
        getAccount()
        onClickImageView()
        setEnableViews(false)
    }

    override fun onClickImageView() {
        imageView.setOnClickListener({
            getPhoto()
        })
    }

    private fun getAccount() {
        presenter.getAccount(this)
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getAccountAdmActivityComponent(this, this)
        presenter = getPresenter()
    }

    open fun getPresenter(): AccountAdmActivityPresenter =
            component.getPresenter()

    override fun setAccount(account: Account) {
        this.account = account
    }

    override fun setEnableViews(enable: Boolean) {
        imageView.isEnabled = enable
        editTextDescription.isEnabled = enable
        editTextAdress.isEnabled = enable
        editTextEmail.isEnabled = enable
        editTextFace.isEnabled = enable
        editTextPhone.isEnabled = enable
        editTextWeb.isEnabled = enable
        editTextInsta.isEnabled = enable
    }

    override fun getPhoto() {
        if (!Utils.oldPhones()) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            Utils.checkForPermission(this, permissionCheck, Utils.PERMISSION_GALERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Utils.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Utils.ImageDialogLogo(this, null, Utils.PERMISSION_GALERY);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.PERMISSION_GALERY)
            if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Utils.ImageDialogLogo(this, null, Utils.PERMISSION_GALERY);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == Utils.PERMISSION_GALERY) {
                val imageUri = CropImage.getPickImageResultUri(this, data)
                Utils.startCropImageActivity(this, null, imageUri)
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    uriExtra = result.uri
                    assignImage(uriExtra)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    if (!result.error.toString().contains("ENOENT"))
                        Utils.showSnackBar(conteiner, getString(R.string.error_crop_image) + result.error)
                }
            }
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    private fun assignImage(uri: Uri?) {
        Utils.setPicasso(this, uri.toString(), android.R.drawable.ic_menu_crop, imageView)
        try {
            imageByte = Utils.readBytes(uri, this)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }

    override fun fillViews(account: Account) {
        with(account) {
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
            Utils.setPicasso(applicationContext, url_image, R.drawable.adeful, imageView)
            editTextDescription.text = Editable.Factory.getInstance().newEditable(DESCRIPTION)
            editTextAdress.text = Editable.Factory.getInstance().newEditable(ADDRESS)
            editTextPhone.text = Editable.Factory.getInstance().newEditable(PHONE)
            editTextEmail.text = Editable.Factory.getInstance().newEditable(EMAIL)
            editTextWeb.text = Editable.Factory.getInstance().newEditable(WEB)
            editTextFace.text = Editable.Factory.getInstance().newEditable(FACEBOOK)
            editTextInsta.text = Editable.Factory.getInstance().newEditable(INSTAGRAM)
        }
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun saveSuccess() {
        Utils.showSnackBar(conteiner, getString(R.string.account_success))
    }

    override fun cleanViews() {
        url_image = ""
        name_image = ""
        name_before = ""
        encode = ""
        imageByte = null
    }

    override fun menuVisibility(isSave: Boolean, isEdit: Boolean) {
        menu!!.getItem(0).isVisible = isSave// save
        menu!!.getItem(1).isVisible = isEdit// edit
    }

    private fun fillAccount(): Account {
        if (imageByte != null) {
            try {
                encode = Base64.encodeToString(imageByte,
                        Base64.DEFAULT)
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                encode = ""
            }
            name_before = account.NAME_IMAGE
            name_image = Utils.getFechaOficial() + Utils.PNG
            url_image = Utils.URL_ACCOUNT + name_image
        } else {
            name_before = account.NAME_IMAGE
            name_image = name_before
            url_image = account.URL_IMAGE
        }
        with(account) {
            DESCRIPTION = editTextDescription.text.toString()
            ADDRESS = editTextAdress.text.toString()
            PHONE = editTextPhone.text.toString()
            EMAIL = editTextEmail.text.toString()
            WEB = editTextWeb.text.toString()
            FACEBOOK = editTextFace.text.toString()
            INSTAGRAM = editTextInsta.text.toString()
            URL_IMAGE = url_image
            NAME_IMAGE = name_image
            ENCODE = encode
            BEFORE = name_before
        }
        return account
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        linearConteiner.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        linearConteiner.visibility = View.INVISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu, menu)
        this.menu = menu
        menuVisibility(false, true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == R.id.action_save) {
            presenter.saveAccount(this, fillAccount())
        } else if (id == R.id.action_edit) {
            setEnableViews(true)
            menuVisibility(true, false)
        }
        return super.onOptionsItemSelected(item)
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