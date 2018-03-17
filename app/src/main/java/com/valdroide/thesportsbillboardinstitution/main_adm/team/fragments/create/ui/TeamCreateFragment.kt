package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
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
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_team.*
import java.io.IOException
import javax.inject.Inject

class TeamCreateFragment : BaseFragment(), TeamCreateFragmentView, View.OnClickListener {

    @Inject
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonSave.text = getString(R.string.save_button, "Equipo")
        linearConteinerPlayer.visibility = View.GONE
        if (isTeamUpdate()) {
            presenter.getTeam(activity, id_team)
        } else
            setVisibilityViews(View.VISIBLE)
        setOnclik()
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getTeamCreateFragmentComponent(this, this).inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_create_team

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setCommunicator(communicator: Communicator) {
        communication = communicator
    }

    private fun setOnclik() {
        imageViewTeam.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
        imageViewInformationTeam.setOnClickListener(this)
    }

    override fun onClick(onclick: View?) {
        when (onclick) {
            imageViewTeam -> onClickPhoto()
            buttonSave -> onClickButtonSave()
            imageViewInformationTeam -> showAlertInformation()
        }
    }

    private fun showAlertInformation() {
        Utils.showAlertInformation(activity, "EQUIPOS", getString(R.string.alert_info_team))
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
            Utils.checkForPermission(activity, permissionCheck, Utils.PERMISSION_GALERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Utils.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Utils.ImageDialogLogo(null, this, Utils.PERMISSION_GALERY);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.PERMISSION_GALERY)
            if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Utils.ImageDialogLogo(null, this, Utils.PERMISSION_GALERY);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == Utils.PERMISSION_GALERY) {
                val imageUri = CropImage.getPickImageResultUri(activity, data)
                Utils.startCropImageActivity(null, this, imageUri)
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    assignImage(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    if (!result.error.toString().contains("ENOENT"))
                        showSnackBar(getString(R.string.error_crop_image) + result.error)
                }
            }
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    private fun assignImage(uri: Uri?) {
        Utils.setPicasso(activity, uri.toString(), R.drawable.shield_icon, imageViewTeam)
        try {
            imageByte = Utils.readBytes(uri, activity)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }

    private fun fillTeamEntity() {
        with(team) {
            NAME = editTextNameTeam.text.toString()
            if (imageByte != null) {
                try {
                    encode = Utils.encodeToString(imageByte)
                } catch (e: Exception) {
                    FirebaseCrash.report(e)
                    encode = ""
                }
                name_image = Utils.getFechaOficial() + Utils.PNG
                url_image = Utils.URL_TEAM + name_image
            }

            ENCODE = encode
            NAME_IMAGE = name_image
            URL_IMAGE = url_image

            if (is_update) {
                ID_TEAM_KEY = id_team
                BEFORE = name_before
                editTeam(team)
            } else {
                BEFORE = team.NAME_IMAGE
                saveTeam(team)
            }
        }
    }

    private fun saveTeam(team: Team) {
        presenter.saveTeam(activity, team)
    }

    private fun editTeam(team: Team) {
        presenter.updateTeam(activity, team)
    }

    private fun isTeamUpdate(): Boolean {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_team = activity.intent.getIntExtra("id_team", 0)
            buttonSave.text = getString(R.string.update_button, "Equipo")
        }
        return is_update
    }

    override fun setTeamEdit(team: Team) {
        this.team = team
    }

    override fun fillViewUpdate() {
        with(team) {
            id_team = ID_TEAM_KEY
            Utils.setPicasso(activity, URL_IMAGE, R.drawable.shield_icon, imageViewTeam)
            editTextNameTeam.text = Editable.Factory.getInstance().newEditable(NAME)
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        showSnackBar( getString(R.string.save_success, "Equipo", "o"))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        showSnackBar(getString(R.string.update_success, "Equipo", "o"))
    }

    override fun setError(error: String) {
        showSnackBar(error)
    }

    override fun hideProgressDialog() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
    }

    private fun showSnackBar(msg: String){
        Utils.showSnackBar(conteiner, msg)
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
        imageViewTeam.setImageResource(R.drawable.shield_icon)
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerTeam.visibility = isVisible
        buttonSave.visibility = isVisible
    }
}