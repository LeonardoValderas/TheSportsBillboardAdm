package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.SanctionCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di.SanctionCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_sanction.*

class SanctionCreateFragment : Fragment(), SanctionCreateFragmentView, View.OnClickListener {

    private lateinit var component: SanctionCreateFragmentComponent
    private lateinit var presenter: SanctionCreateFragmentPresenter
    private var subMenuDrawer = SubMenuDrawer()
    private var player = Player()
    private lateinit var subMenuDrawers: MutableList<SubMenuDrawer>
    private lateinit var players: MutableList<Player>
    private lateinit var communication: Communicator
    private var isRegister: Boolean = false
    private var sanction: Sanction = Sanction()
    private lateinit var adapterSpinnerSubMenus: GenericSpinnerAdapter
    private lateinit var adapterSpinnerPlayers: GenericSpinnerAdapter
    private var is_update: Boolean = false
    private var id_sanction: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_sanction, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        textViewButton.text = getString(R.string.save_button, "Sanción")
        isSanctionUpdate()
        initSpinnerAdapter()
        getSubMenuAndPlayers()
        if (is_update) {
            presenter.getSanction(activity, id_sanction)
        } else {
            setVisibilityViews(View.VISIBLE)
            hideProgressDialog()
        }
        setOnclik()
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getSanctionCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterSpinnerSubMenus = getAdapterSubMenus()
        adapterSpinnerPlayers = getAdapterPlayers()
    }

    override fun onClick(onclick: View?) {
        if (onclick == buttonSave)
            onClickButtonSave()
    }

    private fun getSubMenuAndPlayers() {
        presenter.getSubMenusAndPlayers(activity)
    }

    override fun setSubMenusAndPlayers(subMenuDrawers: MutableList<SubMenuDrawer>, players: MutableList<Player>) {
        this.subMenuDrawers = subMenuDrawers
        this.players = players
        adapterSpinnerSubMenus.refresh(subMenuDrawers, 1)
        adapterSpinnerPlayers.refresh(players, 3)
    }

    override fun setPlayersForId(players: MutableList<Player>) {
        this.players = players
        adapterSpinnerPlayers.refresh(players, 3)
    }

    private fun getPresenterInj(): SanctionCreateFragmentPresenter =
            component.getPresenter()

    private fun getAdapterSubMenus(): GenericSpinnerAdapter =
            component.getAdapterSubMenus()

    private fun getAdapterPlayers(): GenericSpinnerAdapter =
            component.getAdapterPlayer()

    private fun isSanctionUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_sanction = activity.intent.getIntExtra("id_sanction", 0)
            textViewButton.text = getString(R.string.update_button, "Sanción")
        }
    }

    private fun setOnclik() {
        buttonSave.setOnClickListener(this)
    }

    private fun initSpinnerAdapter() {
        spinnerSubMenu.adapter = adapterSpinnerSubMenus
        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                subMenuDrawer = subMenuDrawers[pos]
                getPlayerForIdSubMenu(subMenuDrawer.ID_SUBMENU_KEY)
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerPlayer.adapter = adapterSpinnerPlayers
        spinnerPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                player = players[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
    }

    private fun getPlayerForIdSubMenu(id_submenu: Int) {
        presenter.getPlayerForIdSubMenu(activity, id_submenu)
    }

    override fun onClickButtonSave() {
        if (subMenuDrawers.isEmpty())
            setError(getString(R.string.spinner_empty, "un submenu"))
        if (players.isEmpty())
            setError(getString(R.string.spinner_empty, "un jugador"))
        else if (editTextSanction.text.isEmpty())
            editTextSanction.error = getString(R.string.sanction_empty)
        else
            fillSanctionEntity()
    }

    private fun fillSanctionEntity() {
        sanction.YELLOW = editTextYellow.text.toString()
        sanction.RED = editTextRed.text.toString()
        sanction.SANCTION = editTextSanction.text.toString()
        sanction.OBSERVATION = editTextObservation.text.toString()
        sanction.ID_SUB_MENU = subMenuDrawer.ID_SUBMENU_KEY
        sanction.ID_PLAYER = player.ID_PLAYER_KEY
        if (is_update) {
            sanction.ID_SANCTION_KEY = id_sanction
            editSanction(sanction)
        } else {
            saveSanction(sanction)
        }
    }

    private fun saveSanction(sanction: Sanction) {
        presenter.saveSanction(activity, sanction)
    }

    private fun editSanction(sanction: Sanction) {
        presenter.updateSanction(activity, sanction)
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

    override fun setSanctionUpdate(sanction: Sanction) {
        this.sanction = sanction
    }

    override fun fillViewUpdate() {
        with(sanction) {
            id_sanction = ID_SANCTION_KEY
            spinnerSubMenu.setSelection(getPositionSpinners(ID_SUB_MENU, true))
            spinnerPlayer.setSelection(getPositionSpinners(ID_PLAYER, false))
            editTextYellow.text = Editable.Factory.getInstance().newEditable(YELLOW)
            editTextRed.text = Editable.Factory.getInstance().newEditable(RED)
            editTextSanction.text = Editable.Factory.getInstance().newEditable(SANCTION)
            editTextObservation.text = Editable.Factory.getInstance().newEditable(OBSERVATION)
        }
    }

    private fun getPositionSpinners(id: Int, isSubMenu: Boolean): Int {
        var index = 0
        if (isSubMenu) {
            for (i in 0 until subMenuDrawers.size) {
                if (subMenuDrawers[i].ID_SUBMENU_KEY == id) {
                    index = i
                    return index
                }
            }
        } else {
            for (i in 0 until players.size) {
                if (players[i].ID_PLAYER_KEY == id) {
                    index = i
                    return index
                }
            }
        }
        return index
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.save_success, "Sanción"))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Sanción"))
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
        sanction = Sanction()
        editTextYellow.text.clear()
        editTextRed.text.clear()
        editTextSanction.text.clear()
        editTextObservation.text.clear()
        textViewButton.text = getString(R.string.save_button, "Sanción")
        is_update = false
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerSanction.visibility = isVisible
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