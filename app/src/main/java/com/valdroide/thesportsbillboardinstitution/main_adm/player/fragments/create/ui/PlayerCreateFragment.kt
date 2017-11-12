package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui

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
import android.widget.ArrayAdapter
import com.google.firebase.crash.FirebaseCrash
import com.theartofdev.edmodo.cropper.CropImage
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.PlayerCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di.PlayerCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_team.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import java.io.IOException

class PlayerCreateFragment : Fragment(), PlayerCreateFragmentView, View.OnClickListener {

    private lateinit var component: PlayerCreateFragmentComponent
    private lateinit var presenter: PlayerCreateFragmentPresenter
    private lateinit var adapterSpinnerPosition: ArrayAdapter<Position>
    private lateinit var adapterSpinnerSubMenus: ArrayAdapter<SubMenuDrawer>
    private lateinit var communication: Communicator
    private var isRegister: Boolean = false
    private var positions: MutableList<Position>? = null
    private var submenus: MutableList<SubMenuDrawer>? = null
    private var player: Player = Player()
    private var position: Position = Position()
    private var submenu: SubMenuDrawer = SubMenuDrawer()
    private var is_update: Boolean = false
    private var id_player: Int = 0
    private var encode: String = ""
    private var name_image: String = ""
    private var name_before: String = ""
    private var url_image: String = ""
    private var imageByte: ByteArray? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_team, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        textViewButton.text = getString(R.string.save_button, "Jugador")
        isPlayerUpdate()
        initSpinnerAdapter()
        getPositionsAndSubMenus()
        if (is_update) {
            presenter.getPlayer(activity, id_player)
        } else {
            hideProgressDialog()
            setVisibilityViews(View.VISIBLE)
        }
        setOnclik()
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getPlayerCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterSpinnerPosition = getAdapterPosition()
        adapterSpinnerSubMenus = getAdapterSubMenus()
    }

    private fun getPresenterInj(): PlayerCreateFragmentPresenter =
            component.getPresenter()

    private fun getAdapterPosition(): ArrayAdapter<Position> =
            component.getAdapterPosition()

    private fun getAdapterSubMenus(): ArrayAdapter<SubMenuDrawer> =
            component.getAdapterSubMenus()

    private fun isPlayerUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_player = activity.intent.getIntExtra("id_player", 0)
            textViewButton.text = getString(R.string.update_button, "Jugador")
        }
    }

    private fun getPositionsAndSubMenus() {
        presenter.getPositionsSubMenus(activity)
    }

    private fun initSpinnerAdapter() {
        spinnerPosition.adapter = adapterSpinnerPosition
        spinnerSubMenu.adapter = adapterSpinnerSubMenus
    }

    private fun setOnclik() {
        imageViewPlayer.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
        imageViewInformationPlayer.setOnClickListener(this)
    }

    override fun setPositionsAndSubMenus(positions: MutableList<Position>, submenus: MutableList<SubMenuDrawer>) {
        this.positions = positions
        this.submenus = submenus
        spinnerPosition.adapter = ArrayAdapter<Position>(activity, android.R.layout.simple_list_item_1, positions)
        spinnerSubMenu.adapter = ArrayAdapter<SubMenuDrawer>(activity, android.R.layout.simple_list_item_1, submenus)
    }

    override fun onClick(onclick: View?) {
        when(onclick){
            imageViewPlayer -> onClickPhoto()
            buttonSave -> onClickButtonSave()
            imageViewInformationPlayer -> showAlertInformation()
        }
    }

    override fun onClickButtonSave() {
        if (editTextName.text.isEmpty())
            editTextName.error = getString(R.string.player_error_empty)
        else if (spinnerPosition.selectedItem == null)
            Utils.showSnackBar(conteiner, getString(R.string.position_empty_player))
        else if (spinnerSubMenu.selectedItem == null)
            Utils.showSnackBar(conteiner, getString(R.string.submenu_empty_player))
        else
            fillPlayerEntity()
    }

    private fun showAlertInformation() {
        alert(getString(R.string.alert_info_player)) {
            title = "JUGADORES"
            yesButton {
            }
        }.show()
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
                        Utils.showSnackBar(conteiner, "Error al asignar imagen: " + result.error)
                }
            }
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            setError(e.message!!)
        }
    }

    private fun assignImage(uri: Uri?) {
        Utils.setPicasso(activity, uri.toString(), android.R.drawable.ic_menu_camera, imageViewPlayer)
        try {
            imageByte = Utils.readBytes(uri, activity)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }

    private fun fillPlayerEntity() {
        player.NAME = editTextName.text.toString()
        position = spinnerPosition.selectedItem as Position
        submenu = spinnerSubMenu.selectedItem as SubMenuDrawer
        if (imageByte != null) {
            try {
                encode = Base64.encodeToString(imageByte,
                        Base64.DEFAULT)
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                encode = ""
            }
            name_image = Utils.getFechaOficial() + ".PNG"
            url_image = Utils.URL_PLAYER + name_image
        }

        player.ENCODE = encode
        player.NAME_IMAGE = name_image
        player.URL_IMAGE = url_image
        player.ID_POSITION = position.ID_POSITION_KEY
        player.ID_SUB_MENU = submenu.ID_SUBMENU_KEY
        if (is_update) {
            player.ID_PLAYER_KEY = id_player
            player.BEFORE = name_before
            editPlayer(player)
        } else {
            player.BEFORE = player.NAME_IMAGE
            savePlayer(player)
        }
    }

    private fun savePlayer(Player: Player) {
        presenter.savePlayer(activity, Player)
    }

    private fun editPlayer(Player: Player) {
        presenter.updatePlayer(activity, Player)
    }

    private fun getPositionSpinners(id: Int, isPosition: Boolean): Int {
        var index = 0
        if (isPosition) {
            for (i in 0 until positions!!.size) {
                if (positions!![i].ID_POSITION_KEY == id) {
                    index = i
                    break
                }
            }
        } else {
            for (i in 0 until submenus!!.size) {
                if (submenus!![i].ID_SUBMENU_KEY == id) {
                    index = i
                    break
                }
            }
        }
        return index
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

    override fun setPlayerUpdate(player: Player) {
        this.player = player
    }

    override fun fillViewUpdate() {
        with(player) {
            id_player = ID_PLAYER_KEY
            Utils.setPicasso(activity, URL_IMAGE, R.drawable.players_icon, imageViewPlayer)
            editTextName.text = Editable.Factory.getInstance().newEditable(NAME)
            spinnerPosition.setSelection(getPositionSpinners(player.ID_POSITION, true))
            spinnerSubMenu.setSelection(getPositionSpinners(player.ID_SUB_MENU, false))
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.player_save_success))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.player_update_success))
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
        player = Player()
        submenu = SubMenuDrawer()
        editTextName.text.clear()
        textViewButton.text = getString(R.string.save_button, "Jugador")
        is_update = false
        url_image = ""
        name_image = ""
        name_before = ""
        encode = ""
        imageByte = null
        imageViewPlayer.setImageResource(R.drawable.players_icon)
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerPlayer.visibility = isVisible
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