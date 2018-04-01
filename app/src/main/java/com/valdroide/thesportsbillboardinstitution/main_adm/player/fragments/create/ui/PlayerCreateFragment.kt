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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import com.google.firebase.crash.FirebaseCrash
import com.theartofdev.edmodo.cropper.CropImage
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.PlayerCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di.PlayerCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.adapters.PlayerCreateFragmentPositionSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.adapters.PlayerCreateFragmentSubMenuSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.dialogs.PlayerPositionDialog
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.*
import com.valdroide.thesportsbillboardinstitution.utils.helper.*
import kotlinx.android.synthetic.main.fragment_create_team.*
import java.io.IOException

class PlayerCreateFragment : Fragment(),
                             PlayerCreateFragmentView,
                             View.OnClickListener,
                             OnItemClickListenerDialog<Position> {

    //region INITVARIABLES
    private lateinit var component: PlayerCreateFragmentComponent
    private lateinit var presenter: PlayerCreateFragmentPresenter
    private lateinit var adapterSpinnerPosition: PlayerCreateFragmentPositionSpinnerAdapter
    private lateinit var adapterSpinnerSubMenus: PlayerCreateFragmentSubMenuSpinnerAdapter
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
    private var posi: Int = 0
    //endregion

    //region LIFECYCLE
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_team, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        buttonSave.text = getString(R.string.save_button, "Jugador")
        isPlayerUpdate()
        initSpinnerAdapter()
        getPositionsAndSubMenus()
        if (is_update) {
            presenter.getPlayer(activity, id_player)
        }
        setOnclik()
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
    //endregion

    //region INJECTION
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

    private fun getAdapterPosition(): PlayerCreateFragmentPositionSpinnerAdapter =
            component.getAdapterPosition()

    private fun getAdapterSubMenus(): PlayerCreateFragmentSubMenuSpinnerAdapter =
            component.getAdapterSubMenus()
    //endregion

    //region INTENT
    private fun isPlayerUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_player = activity.intent.getIntExtra("id_player", 0)
            buttonSave.text = getString(R.string.update_button, "Jugador")
        }
    }
    //endregion

    //region PRESENTER
    private fun getPositionsAndSubMenus() {
        presenter.getPositionsSubMenus(activity)
    }

    private fun fillPlayerEntity() {
        with(player) {
            NAME = editTextName.text.toString()
            if (imageByte != null) {
                try {
                    encode = ImageHelper.encodeToString(imageByte)
                } catch (e: Exception) {
                    FirebaseCrash.report(e)
                    encode = ""
                }
                name_image = DateTimeHelper.getFechaOficial() + ConstantHelper.PNG
                url_image = UrlHelper.URL_PLAYER + name_image
            }

            ENCODE = encode
            NAME_IMAGE = name_image
            URL_IMAGE = url_image
            ID_POSITION = position.ID_POSITION_KEY
            ID_SUB_MENU = submenu.ID_SUBMENU_KEY
            if (is_update) {
                ID_PLAYER_KEY = id_player
                BEFORE = name_before
                editPlayer(player)
            } else {
                BEFORE = player.NAME_IMAGE
                savePlayer(player)
            }
        }
    }

    private fun savePlayer(Player: Player) {
        presenter.savePlayer(activity, Player)
    }

    private fun editPlayer(Player: Player) {
        presenter.updatePlayer(activity, Player)
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

    //endregion

    //region SPINNER
    private fun initSpinnerAdapter() {
        spinnerPosition.adapter = adapterSpinnerPosition
        spinnerPosition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                position = positions!![pos]
                posi = pos
            }
            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }

        spinnerSubMenu.adapter = adapterSpinnerSubMenus
        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                submenu = submenus!![pos]
            }
            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
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
    //endregion

    //region ONCLICK
    private fun setOnclik() {
        imageViewPlayer.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
        imageViewInformationPlayer.setOnClickListener(this)
        fabCreatePosition.setOnClickListener(this)
        fabUpdatePosition.setOnClickListener(this)
        fabGoToMenu.setOnClickListener(this)
    }

    override fun onClick(onclick: View?) {
        when (onclick) {
            imageViewPlayer -> onClickPhoto()
            buttonSave -> onClickButtonSave()
            imageViewInformationPlayer -> showAlertInformation()
            fabCreatePosition -> PlayerPositionDialog(activity, false, getString(R.string.position_hint), position, this).show()
            fabUpdatePosition -> PlayerPositionDialog(activity, true, getString(R.string.position_hint), position, this).show()
            fabGoToMenu -> goToActivity()
        }
    }

    private fun goToActivity() {
        startActivity(Intent(activity, MenuSubMenuActivity::class.java))
    }

    private fun showAlertInformation() {
        ViewComponentHelper.showAlertInformation(activity, "JUGADORES", getString(R.string.alert_info_player))
    }
    //endregion

    //region VIEW
   override fun setPositionsAndSubMenus(positions: MutableList<Position>, submenus: MutableList<SubMenuDrawer>) {
        this.positions = positions
        this.submenus = submenus
        adapterSpinnerPosition.refresh(positions)
        adapterSpinnerSubMenus.refresh(submenus)
        if (!is_update) {
            hideProgressDialog()
            setVisibilityViews(View.VISIBLE)
        }
    }

    override fun onClickButtonSave() {
        if (editTextName.text.isEmpty())
            editTextName.error = getString(R.string.player_error_empty)
        else if (positions == null)
            showSnackBar(getString(R.string.position_empty_player))
        else if (submenus == null)
            showSnackBar(getString(R.string.submenu_empty_player))
        else
            fillPlayerEntity()
    }

    override fun onClickPhoto() {
        if (!PermissionHelper.oldPhones()) {
            val permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            PermissionHelper.checkForPermission(activity, permissionCheck, ConstantHelper.PERMISSION_GALERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (PermissionHelper.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            ViewComponentHelper.ImageDialogLogo(null, this, ConstantHelper.PERMISSION_GALERY);
    }

    override fun setPlayerUpdate(player: Player) {
        this.player = player
    }

    override fun fillViewUpdate() {
        with(player) {
            id_player = ID_PLAYER_KEY
            ImageHelper.setPicasso(activity, URL_IMAGE, R.drawable.players_icon, imageViewPlayer)
            editTextName.text = Editable.Factory.getInstance().newEditable(NAME)
            spinnerPosition.setSelection(getPositionSpinners(player.ID_POSITION, true))
            spinnerSubMenu.setSelection(getPositionSpinners(player.ID_SUB_MENU, false))
            url_image = URL_IMAGE
            name_image = NAME_IMAGE
            name_before = name_image
        }
    }

    override fun savePlayerSuccess() {
        showSnackBar(getString(R.string.save_success, "Jugador", "o"))
    }

    override fun editPlayerSuccess() {
        showSnackBar(getString(R.string.update_success, "Jugador", "o"))
    }

    private fun showSnackBar(msg: String) = ViewComponentHelper.showSnackBar(conteiner, msg)

    override fun savePositionSuccess(position: Position) {
        adapterSpinnerPosition.addItem(position)
        showSnackBar(getString(R.string.save_success, "Posición", "a"))
    }

    override fun editPositionSuccess(position: Position) {
        adapterSpinnerPosition.updateItem(posi, position)
        showSnackBar(getString(R.string.update_success, "Posición", "a"))
    }

    override fun refreshAdapter() {
        communication.refreshAdapter()
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

    override fun cleanViews() {
        player = Player()
        editTextName.text.clear()
        buttonSave.text = getString(R.string.save_button, "Jugador")
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
    //endregion

    //region ADAPTER INTERFACE METHODS
    override fun onClickSave(t: Position) {
        presenter.savePosition(activity, t)
    }

    override fun onClickUpdate(t: Position) {
        presenter.updatePosition(activity, t)
    }
    //endregion

    //region PHOTO METHODS
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ConstantHelper.PERMISSION_GALERY)
            if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ViewComponentHelper.ImageDialogLogo(null, this, ConstantHelper.PERMISSION_GALERY);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == ConstantHelper.PERMISSION_GALERY) {
                val imageUri = CropImage.getPickImageResultUri(activity, data)
                ImageHelper.startCropImageActivity(null, this, imageUri)
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
        ImageHelper.setPicasso(activity, uri.toString(), android.R.drawable.ic_menu_camera, imageViewPlayer)
        try {
            imageByte = ImageHelper.readBytes(uri, activity)
        } catch (e: IOException) {
            FirebaseCrash.report(e)
            e.printStackTrace()
        }
    }
    //endregion
}