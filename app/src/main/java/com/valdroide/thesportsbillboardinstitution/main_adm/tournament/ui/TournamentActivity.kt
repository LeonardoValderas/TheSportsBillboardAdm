package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.TournamentActivityPresenter
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.activity_tournament_content.*
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import org.jetbrains.anko.*
import javax.inject.Inject

open class TournamentActivity : BaseActivity(),
        TournamentActivityView,
        View.OnClickListener {

    //region VARIABLES
    @Inject
    lateinit var presenter: TournamentActivityPresenter
    private var isUpdate = false
    private var isDelete = false
    private var tournament: Tournament? = null
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
        app.getTournamentActivityComponent(this, this).inject(this)
    }

    override fun setVisibilityViews(isVisible: Int) {
        conteinerContent.visibility = isVisible
    }

    private fun setOnclickButtons() {
        setOnclinListenterView(fabCreateTournament)
        setOnclinListenterView(fabUpdateTournament)
        setOnclinListenterView(fabActiveTournament)
        setOnclinListenterView(fabDeleteTournament)
        setOnclinListenterView(imageViewInformationTournament)
        setOnclinListenterView(buttonTournament)
        setOnclinListenterView(btnSubMenu)
        setOnclinListenterView(btnTournamentSubmenu)
    }

    private fun setOnclinListenterView(view: View) {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == fabCreateTournament) {
            saveTournament()
            return
        }

        if (isTournamentEmpty()) {
            tournamentEmptyError()
            return
        }

        when (v) {
            fabUpdateTournament -> updateTournament()
            fabActiveTournament -> activeTournament()
            fabDeleteTournament -> deleteTournament()
            imageViewInformationTournament -> showAlertInformation()
            buttonTournament -> dialogSpinnerTournament()
            btnSubMenu -> btnSubMenuOnClick()
            btnTournamentSubmenu -> btnTournamentSubMenuOnClick()
        }
    }

    private fun isTournamentEmpty() = tournaments.isEmpty()

    private fun isSubMenuEmpty() = subMenus.isEmpty()

    private fun tournamentEmptyError() = setError(getString(R.string.spinner_empty, "un torneo"))

    private fun subMenuEmptyError() = setError(getString(R.string.spinner_empty, "un menu - submenu"))

    private fun btnSubMenuOnClick() {
        if (isSubMenuEmpty()) subMenuEmptyError() else dialogSpinnerSubMenu()
    }

    private fun btnTournamentSubMenuOnClick() {
        if (isSubMenuEmpty()) subMenuEmptyError() else dialogSpinnerTournamentSubMenu()
    }

    private fun showAlertInformation() {
        ViewComponentHelper.showAlertInformation(this, "TORNEOS", getString(R.string.alert_info_tournament))
    }

    private fun updateTournament() {
        if (tournament != null) {
            editTextTournament.text = Editable.Factory.getInstance().newEditable(tournament!!.TOURNAMENT)
            isUpdate = true
        } else
            selectTournamentError()
    }

    private fun selectTournamentError() {
        setError(getString(R.string.select_tournament))
    }

    private fun deleteTournament() {
        showAlert()
    }

    private fun showAlert() {
        if (tournament != null) {
            alert(getString(R.string.delete_alerte_msg, "el torneo")) {
                title = "Atención"
                yesButton {
                    presenter.deleteTournament(applicationContext, tournament!!)
                    isDelete = true
                }
                noButton { }
            }.show()
        } else
            selectTournamentError()
    }

    private fun activeTournament() {
        if (tournament != null) {
            with(tournament!!) {
                if (IS_ACTIVE == 0) IS_ACTIVE = 1 else IS_ACTIVE = 0
            }
            presenter.activeOrUnActiveTournament(this, tournament!!)
        } else
            selectTournamentError()
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
                tournament!!.TOURNAMENT = editTextTournament.text.toString()
                presenter.updateTournament(this, tournament!!)
            } else
                presenter.saveTournament(this, Tournament(0, editTextTournament.text.toString()))
        }
    }

    override fun setSubMenusTournaments(tournaments: MutableList<Tournament>, submenus: MutableList<SubMenuDrawer>) {
        this.tournaments = tournaments
        this.subMenus = submenus

        assignLastItemWorked()
        //validateTournament()
        validateSubMenus()
    }

    private fun assignLastItemWorked() {
        if (tournament != null) {
            val tournamentAux = tournaments.singleOrNull { it.ID_TOURNAMENT_KEY == tournament!!.ID_TOURNAMENT_KEY }
            if (tournamentAux != null) {
                assignTournament(tournamentAux)
                tournament = tournamentAux
            }
        } else
            buttonTournament.text = validateTournament()
    }

    private fun assignTournament(tournament: Tournament) {
        buttonTournament.text = tournament.TOURNAMENT
        setDividerLine(diviverLine, tournament.IS_ACTIVE)
    }

    private fun validateTournament(): String {
        if (!isTournamentEmpty()) {
            if (tournament == null) {
                diviverLine.visibility = View.GONE
                return getString(R.string.create_select_tournament)
            } else
                return tournament!!.TOURNAMENT
        } else {
            diviverLine.visibility = View.GONE
            return getString(R.string.create_tournament)
        }
    }

    private fun validateSubMenus() {
        if (!isSubMenuEmpty()) {
            submenu = subMenus.first()
            btnSubMenu.text = submenu.toString()
            setDividerLine(diviverLineSubmenu, submenu.IS_ACTIVE)
            getTournamentForSubMenu(submenu)
            tvSubMenu.visibility = View.GONE
        } else {
            btnSubMenu.text = getString(R.string.menu_submenu)
            tvSubMenu.visibility = View.VISIBLE
            tvSubMenu.text = getString(R.string.menu_submenu_empty)
            diviverLineSubmenu.visibility = View.GONE
        }
    }

    override fun refreshData() {
        presenter.getSubMenuTournaments(this)
    }

    override fun assignationSuccess() {
        showSnackBar(getString(R.string.assignament_success))
    }

    private fun dialogSpinnerTournament() {
        val spinnerDialog = SpinnerDialog(this@TournamentActivity, tournaments, getString(R.string.select_tournament), R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                tournament = tournaments[position]
                buttonTournament.text = item
                setDividerLine(diviverLine, tournament!!.IS_ACTIVE)
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
                setDividerLine(diviverLineSubmenu, submenu.IS_ACTIVE)
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

    private fun setDividerLine(view: View, is_active: Int) {
        with(view) {
            visibility = View.VISIBLE
            if (is_active == 0)
                backgroundDrawable = ContextCompat.getDrawable(this@TournamentActivity, R.drawable.line_state_unactivo_item)
            else
                backgroundDrawable = ContextCompat.getDrawable(this@TournamentActivity, R.drawable.line_state_activo_item)
        }
    }

    override fun setError(error: String) {
        showSnackBar(error)
    }

    override fun eventSuccess(msg: String) {
        showSnackBar(msg)
        validateDelete()
    }

    private fun showSnackBar(msg: String) = ViewComponentHelper.showSnackBar(conteiner, msg)

    private fun validateDelete() {
        if (isDelete) {
            tournament = null
            isDelete = false
        }
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