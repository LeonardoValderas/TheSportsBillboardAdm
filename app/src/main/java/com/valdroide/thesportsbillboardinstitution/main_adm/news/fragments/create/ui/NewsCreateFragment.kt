package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import com.google.firebase.crash.FirebaseCrash
import com.theartofdev.edmodo.cropper.CropImage
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.NewsCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di.NewsCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_news.*
import java.io.IOException

class NewsCreateFragment : Fragment(), NewsCreateFragmentView, View.OnClickListener {

    private lateinit var component: NewsCreateFragmentComponent
    private lateinit var presenter: NewsCreateFragmentPresenter
    private var subMenuDrawer = SubMenuDrawer()
    private lateinit var subMenuDrawers: MutableList<SubMenuDrawer>
    private lateinit var communication: Communicator
    private var isRegister: Boolean = false
    private var news: News = News()
   // private lateinit var adapterSpinnerSubMenus: SubMenuActivityAdapter
    private var is_update: Boolean = false
    private var id_news: Int = 0
    private var encode: String = ""
    private var name_image: String = ""
    private var name_before: String = ""
    private var url_image: String = ""
    private var imageByte: ByteArray? = null

    companion object {
        const val PERMISSION_GALERY: Int = 101
        const val URL: String = "http://10.0.3.2:8080/the_sports_billboard_institution/adm/news/image_news/"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_news, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        textViewButton.text = getString(R.string.save_button, "Noticia")
        isNewsUpdate()
        //initSpinnerAdapter()
        getSubMenu()
        if (is_update) {
            presenter.getNews(activity, id_news)
        } else {
            setVisibilityViews(View.VISIBLE)
            hideProgressDialog()
        }
        setOnclik()
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getNewsCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
       // adapterSpinnerSubMenus = getAdapterSubMenus()
    }

    private fun getSubMenu() {
        presenter.getSubMenus(activity)
    }

    override fun setSubMenus(subMenuDrawers: MutableList<SubMenuDrawer>) {
        this.subMenuDrawers = subMenuDrawers
//        adapterSpinnerSubMenus.refresh(subMenuDrawers)
    }

    private fun getPresenterInj(): NewsCreateFragmentPresenter =
            component.getPresenter()

    ///private fun getAdapterSubMenus(): SubMenuActivityAdapter =
        //    component.getAdapterSubMenus()

    private fun isNewsUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_news = activity.intent.getIntExtra("id_news", 0)
            textViewButton.text = getString(R.string.update_button, "Noticia")
        }
    }

    private fun setOnclik() {
        imageViewNews.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun initSpinnerAdapter() {
  //      spinnerSubMenu.adapter = adapterSpinnerSubMenus
        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                subMenuDrawer = subMenuDrawers[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
    }

    override fun onClick(onclick: View?) {
        if (onclick == imageViewNews)
            onClickPhoto()
        else if (onclick == buttonSave)
            onClickButtonSave()
    }

    override fun onClickButtonSave() {
        if (editTextTitleNews.text.isEmpty())
            editTextTitleNews.error = getString(R.string.news_title_error_empty)
        else if (editTextBodyNews.text.isEmpty())
            Utils.showSnackBar(conteiner, getString(R.string.news_body_error_empty))
        else
            fillNewsEntity()
    }

    override fun onClickPhoto() {
        if (!Utils.oldPhones()) {
            val permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            Utils.checkForPermission(activity, permissionCheck, PERMISSION_GALERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Utils.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Utils.ImageDialogLogo(null, this, PERMISSION_GALERY);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_GALERY)
            if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Utils.ImageDialogLogo(null, this, PERMISSION_GALERY);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == PERMISSION_GALERY) {
                val imageUri = CropImage.getPickImageResultUri(activity, data)
                Utils.startCropImageActivity(null, this, imageUri)
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    assignImage(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    if (!result.error.toString().contains("ENOENT"))
                        Utils.showSnackBar(conteiner, "Error al asignar imagen: " + result.error)
                }
            }
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    private fun assignImage(uri: Uri?) {
        Utils.setPicasso(activity, uri.toString(), android.R.drawable.ic_menu_camera, imageViewNews)
        try {
            imageByte = Utils.readBytes(uri, activity)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }

    private fun fillNewsEntity() {
        news.TITLE = editTextTitleNews.text.toString()
        news.DESCRIPTION = editTextBodyNews.text.toString()
        news.ID_SUB_MENU = subMenuDrawer.ID_SUBMENU_KEY
        if (imageByte != null) {
            try {
                encode = Base64.encodeToString(imageByte,
                        Base64.DEFAULT)
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                encode = ""
            }
            name_image = Utils.getFechaOficial() + ".PNG"
            url_image = URL + name_image
        }
        news.ENCODE = encode
        news.NAME_IMAGE = name_image
        news.URL_IMAGE = url_image
        if (is_update) {
            news.ID_NEWS_KEY = id_news
            news.BEFORE = name_before
            editNews(news)
        } else {
            news.BEFORE = news.NAME_IMAGE
            saveNews(news)
        }
    }

    private fun saveNews(news: News) {
        presenter.saveNews(activity, news)
    }

    private fun editNews(news: News) {
        presenter.updateNews(activity, news)
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

    override fun setNewsUpdate(news: News) {
        this.news = news
    }

    override fun fillViewUpdate() {
        with(news) {
            id_news = ID_NEWS_KEY
            Utils.setPicasso(activity, URL_IMAGE, android.R.drawable.ic_menu_camera, imageViewNews)
            editTextTitleNews.text = Editable.Factory.getInstance().newEditable(TITLE)
            editTextBodyNews.text = Editable.Factory.getInstance().newEditable(DESCRIPTION)
            spinnerSubMenu.setSelection(getPositionSpinners(news.ID_SUB_MENU))
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
        }
    }

    private fun getPositionSpinners(id: Int): Int {
        var index = 0
        for (i in 0 until subMenuDrawers.size) {
            if (subMenuDrawers[i].ID_SUBMENU_KEY == id) {
                index = i
                return index
            }
        }
        return index
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.save_success, "Noticia"))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Noticia"))
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideProgressDialog() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
    }

    override fun cleanViews() {
        news = News()
        subMenuDrawer = SubMenuDrawer()
        editTextTitleNews.text.clear()
        editTextBodyNews.text.clear()
        textViewButton.text = getString(R.string.save_button, "Noticia")
        is_update = false
        url_image = ""
        name_image = ""
        name_before = ""
        encode = ""
        imageByte = null
        imageViewNews.setImageResource(android.R.drawable.ic_menu_camera)
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerNews.visibility = isVisible
        buttonSave.visibility = isVisible
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