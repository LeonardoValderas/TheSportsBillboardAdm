package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui

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
import com.google.firebase.crash.FirebaseCrash
import com.theartofdev.edmodo.cropper.CropImage
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.TeamCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.TeamCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_team.*
import java.io.IOException

class TeamCreateFragment : Fragment(), TeamCreateFragmentView, View.OnClickListener {

    private lateinit var component: TeamCreateFragmentComponent
    private var isRegister: Boolean = false
    lateinit var presenter: TeamCreateFragmentPresenter
    lateinit private var communication: Communicator
    private var team: Team = Team()
    private var is_update: Boolean = false
    private var id_team: Int = 0
    private var encode: String = ""
    private var name_image: String = ""
    private var name_before: String = ""
    private var url_image: String = ""
    private var imageByte: ByteArray? = null


    companion object {
        const val PERMISSION_GALERY: Int = 101
        const val URL: String = "http://10.0.3.2:8080/the_sports_billboard_institution/adm/team/image_team/"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_team, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        linearConteinerPlayer.visibility = View.GONE
        isTeamUpdate()
        if (is_update) {
            setVisibilityViews(View.INVISIBLE)
            presenter.getTeam(activity, id_team)
        } else
            setVisibilityViews(View.VISIBLE)
        setOnclik()
    }

    private fun setOnclik() {
        imageViewTeam.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    override fun onClick(onclick: View?) {
        if (onclick == imageViewTeam)
            onClickPhoto()
        else if (onclick == buttonSave)
            onClickButtonSave()
    }

    override fun onClickButtonSave() {
        if (editTextNameTeam.text.isEmpty())
            editTextNameTeam.setError(getString(R.string.team_error_empty))
        else
            fillTeamEntity()
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
        Utils.setPicasso(activity, uri.toString(), android.R.drawable.ic_menu_camera, imageViewTeam)
        try {
            imageByte = Utils.readBytes(uri, activity)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }

    private fun fillTeamEntity() {
        showProgressDialog()
        setVisibilityViews(View.INVISIBLE)
        team.NAME = editTextNameTeam.text.toString()
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

        team.ENCODE = encode
        team.NAME_IMAGE = name_image
        team.URL_IMAGE = url_image

        if (is_update) {
            team.ID_TEAM_KEY = id_team
            team.BEFORE = name_before
            editTeam(team)
        } else {
            team.BEFORE = team.NAME_IMAGE
            saveTeam(team)
        }
    }

    private fun saveTeam(team: Team) {
        presenter.saveTeam(activity, team)
    }

    private fun editTeam(team: Team) {
        presenter.updateTeam(activity, team)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getTeamCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
    }

    private fun getPresenterInj(): TeamCreateFragmentPresenter =
            component.getPresenter()

    private fun isTeamUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_team = activity.intent.getIntExtra("id_team", 0)
            textViewButton.text = "Editar Equipo"
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

    override fun setTeamEdit(team: Team) {
        this.team = team
    }

    override fun fillViewUpdate() {
        with(team) {
            id_team = ID_TEAM_KEY
            Utils.setPicasso(activity, URL_IMAGE, android.R.drawable.ic_menu_camera, imageViewTeam)
            editTextNameTeam.text = Editable.Factory.getInstance().newEditable(NAME)
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.team_save_success))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.team_update_success))
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
        team = Team()
        editTextNameTeam.text.clear()
        is_update = false
        url_image = ""
        name_image = ""
        name_before = ""
        encode = ""
        imageByte = null
        imageViewTeam.setImageResource(android.R.drawable.ic_menu_camera)
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerTeam.visibility = isVisible
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