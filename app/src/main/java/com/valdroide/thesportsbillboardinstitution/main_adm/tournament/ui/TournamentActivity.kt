package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.TournamentActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di.TournamentActivityComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.activity_tournament_content.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import org.jetbrains.anko.backgroundColor

open class TournamentActivity : BaseActivity(),
        TournamentActivityView,
        View.OnClickListener {

    //region VARIABLES
    private lateinit var presenter: TournamentActivityPresenter
    private lateinit var component: TournamentActivityComponent
    private var isUpdate: Boolean = false
    private var tournament = Tournament()
    private var tournament_actual: Tournament? = null
    private lateinit var tournaments: MutableList<Tournament>
    private lateinit var subMenus: MutableList<SubMenuDrawer>
    var submenu = SubMenuDrawer()
    //endregion

    //region LIFECYCLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSubMenusTournaments()
        setOnclickButtons()
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_tournament

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }
    //endregion

    //region PRESENTER
    private fun getSubMenusTournaments() {
        presenter.getSubMenuTournaments(this)
    }
    //endregion

    override fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getTournamentActivityComponent(this, this)
        presenter = getPresenter()
    }

    private fun getPresenter(): TournamentActivityPresenter =
            component.getPresenter()

    override fun setVisibilityViews(isVisible: Int) {
        conteinerContent.visibility = isVisible
    }

    private fun setOnclickButtons() {
        fabCreateTournament.setOnClickListener(this)
        fabUpdateTournament.setOnClickListener(this)
        fabActiveTournament.setOnClickListener(this)
        fabDeleteTournament.setOnClickListener(this)
        imageViewInformationTournament.setOnClickListener(this)
        buttonTournament.setOnClickListener(this)
        btnSubMenu.setOnClickListener(this)
        btnTournamentSubmenu.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabCreateTournament -> saveTournament()
            fabUpdateTournament -> updateTournament()
            fabActiveTournament -> activeTournament()
            fabDeleteTournament -> deleteTournament()
            imageViewInformationTournament -> showAlertInformation()
            buttonTournament -> dialogSpinnerTournament()
            btnSubMenu -> dialogSpinnerSubMenu()
            btnTournamentSubmenu -> dialogSpinnerTournamentSubMenu()
        }
    }

    private fun showAlertInformation() {
        Utils.showAlertInformation(this, "TORNEOS", getString(R.string.alert_info_tournament))
    }

    private fun updateTournament() {
        if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "torneo"))
        else {
            editTextTournament.text = Editable.Factory.getInstance().newEditable(tournament.TOURNAMENT)
            isUpdate = true
        }
    }

    private fun deleteTournament() {
        if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "torneo"))
        else
            showAlert()
    }

    private fun showAlert() {
        alert(getString(R.string.delete_alerte_msg, "el torneo")) {
            title = "Atención"
            yesButton {
                presenter.deleteTournament(applicationContext, tournament)
            }
            noButton { }
        }.show()
    }

    private fun activeTournament() {
        if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "torneo"))
        else {
            if (tournament.IS_ACTIVE == 0) tournament.IS_ACTIVE = 1 else tournament.IS_ACTIVE = 0
            presenter.activeOrUnActiveTournament(this, tournament)
        }
    }

    override fun cleanViews() {
        editTextTournament.text.clear()
        isUpdate = false
    }

    private fun saveTournament() {
        if (editTextTournament.text.isEmpty())
            editTextTournament.error = getString(R.string.editText_empty, "torneo")
        else {
            if (isUpdate) {
                tournament.TOURNAMENT = editTextTournament.text.toString()
                presenter.updateTournament(this, tournament)
            } else
                presenter.saveTournament(this, Tournament(0, editTextTournament.text.toString()))
        }
    }

    override fun setSubMenusTournaments(tournaments: MutableList<Tournament>, submenus: MutableList<SubMenuDrawer>) {
        this.tournaments = tournaments
        this.subMenus = submenus

        validateTournament()
        validateSubMenus()
    }

    private fun validateTournament() {
        if (tournaments.any()) {
            tournament = tournaments.first()
            buttonTournament.text = tournament.TOURNAMENT
            setDividerLine(tournament.IS_ACTIVE)
        } else {
            btnSubMenu.isEnabled = false
            btnTournamentSubmenu.isEnabled = false
            btnTournamentSubmenu.text = "Submenu vacio"
            btnSubMenu.text = "Torneo"
            tvSubMenu.text = "Debe crear un submenu desde la opción Menus y Submenus"
        }
    }

    private fun validateSubMenus() {
        if (subMenus.any()) {
            submenu = subMenus.first()
            btnSubMenu.text = submenu.toString()
            getTournamentForSubMenu(submenu)
        } else {
            btnSubMenu.isEnabled = false
            btnTournamentSubmenu.isEnabled = false
            btnTournamentSubmenu.text = "Submenu vacio"
            btnSubMenu.text = "Torneo"
            tvSubMenu.text = "Debe crear un submenu desde la opción Menus y Submenus"
        }
    }

    override fun refreshData() {
        presenter.getSubMenuTournaments(this)
    }

    override fun assignationSuccess() {
        Utils.showSnackBar(conteiner, getString(R.string.assignament_success))
    }

    private fun dialogSpinnerTournament() {
        val spinnerDialog = SpinnerDialog(this@TournamentActivity, tournaments, "Seleccione un torneo", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                tournament = tournaments[position]
                buttonTournament.text = item
                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerSubMenu() {
        val spinnerDialog = SpinnerDialog(this@TournamentActivity, subMenus, "Seleccione un menu - sub menu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                submenu = subMenus[position]
                btnSubMenu.text = submenu.toString()
                getTournamentForSubMenu(submenu)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun getTournamentForSubMenu(submenu: SubMenuDrawer) {
        presenter.getTournamentForSubMenu(this@TournamentActivity, submenu.ID_SUBMENU_KEY)
    }

    override fun setTournamentForSubMenu(id: Int) {
        if (id != 0) {
            tournament_actual = tournaments.singleOrNull { it.ID_TOURNAMENT_KEY == id }
            validateTournamentAssigned(tournament_actual)
        } else
            validateTournamentAssigned(null)
    }

    private fun validateTournamentAssigned(tournament: Tournament?) {
        if (tournament != null)
            btnTournamentSubmenu.text = tournament.TOURNAMENT
        else
            btnTournamentSubmenu.text = "Submenu sin torneo asignado"
    }

    private fun dialogSpinnerTournamentSubMenu() {
        val spinnerDialog = SpinnerDialog(this@TournamentActivity, tournaments, "Asigne un torneo al menu - submenu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                tournament_actual = tournaments[position]
                btnTournamentSubmenu.text = tournament_actual!!.TOURNAMENT
                presenter.assignationTournament(this@TournamentActivity, submenu, tournament_actual!!.ID_TOURNAMENT_KEY)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun setDividerLine(is_active: Int) {
        if (is_active == 0)
            diviverLine.backgroundColor = ContextCompat.getColor(this@TournamentActivity, R.color.redColor)
        else
            diviverLine.backgroundColor = ContextCompat.getColor(this@TournamentActivity, R.color.greenColor)
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun eventSuccess(msg: String) {
        Utils.showSnackBar(conteiner, msg)
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        conteinerContent.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        conteinerContent.visibility = View.INVISIBLE
    }
}