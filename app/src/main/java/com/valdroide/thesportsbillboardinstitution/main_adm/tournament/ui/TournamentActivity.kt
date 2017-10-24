package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog.CustomDialog
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.TournamentActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di.TournamentActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.adapter.TournamentActivityAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlinx.android.synthetic.main.activity_tournament_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

open class TournamentActivity : AppCompatActivity(), TournamentActivityView, View.OnClickListener, GenericOnItemClickListener.actualUnActual {

    private lateinit var presenter: TournamentActivityPresenter
    private lateinit var component: TournamentActivityComponent
    private var isRegister: Boolean = false
    private var isUpdate: Boolean = false
    private var tournament = Tournament()
    private lateinit var tournaments: MutableList<Tournament>
    private lateinit var subMenusForTournament: MutableList<SubMenuDrawer>
    private lateinit var adapterSpinnerTournaments: GenericSpinnerAdapter
    private lateinit var adapterSubMenuForTournament: TournamentActivityAdapter
    private var position: Int = 0
    var isActual = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)
        setupInjection()
        initToobar()
        register()
        initSpinnerAdapter()
        initRecyclerView()
        getSubMenusTournaments()
        setOnclickButtons()
    }

    private fun getSubMenusTournaments() {
        showProgressBarHideLayout()
        presenter.getSubMenuTournaments(this)
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getTournamentActivityComponent(this, this, this)
        presenter = getPresenter()
        adapterSpinnerTournaments = getAdapterTournaments()
        adapterSubMenuForTournament = getAdapterTournament()
    }

    private fun getAdapterTournament(): TournamentActivityAdapter =
            component.getAdapterSubMenusTournament()

    private fun getAdapterTournaments(): GenericSpinnerAdapter =
            component.getAdapterTournaments()

    private fun getPresenter(): TournamentActivityPresenter =
            component.getPresenter()

    private fun showProgressBarHideLayout() {
        showProgressBar()
        setVisibilityViews(View.INVISIBLE)
    }

    override fun setVisibilityViews(isVisible: Int) {
        conteinerContent.visibility = isVisible
    }

    override fun setSubMenusForTournament(subMenusForTournament: MutableList<SubMenuDrawer>) {
        this.isActual = false
        this.subMenusForTournament = subMenusForTournament
        adapterSubMenuForTournament.setSubMenusForTournament(subMenusForTournament, tournament)
    }

    private fun setOnclickButtons() {
        fabCreateTournament.setOnClickListener(this)
        fabUpdateTournament.setOnClickListener(this)
        fabActiveTournament.setOnClickListener(this)
        fabDeleteTournament.setOnClickListener(this)
        imageViewInformationTournament.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabCreateTournament -> saveTournament()
            fabUpdateTournament -> updateTournament()
            fabActiveTournament -> activeTournament()
            fabDeleteTournament -> deleteTournament()
            imageViewInformationTournament -> showAlertInformation()
        }
    }

    private fun showAlertInformation() {
        alert("En esta opción usted podrá realizar acciones sobre los torneos deportivos.\n" +
                "Para crear un torneo debe ingresar el nombre del mismo y presionar el botón 'mas'.\n" +
                "Usted podrá actualizar un torneos presionando el botón 'lapiz' y una vez realizada la modificación presionar el botón 'mas'.\n" +
                "Presionando el botón 'flecha circular' usted activará o desactivará un torneo en el caso de no querer eliminarlo. Un torneo inactivo no será visible para el usuario.\n" +
                "La eliminación se realiza con el botón 'cesto'.\n" +
                "Por otro lado, es importante asignar un torneo a un submenu ya que el sistema mostrará la información del torneo actual del submenu por default. Si el usuario quiere consultar torneos anteriores debe realizar una acción de busqueda.\n" +
                "Un submenu puede tener solo un torneo actual, si desea cambiar de torneo debe destildar el actual y tildar el nuevo torneo.") {
            title = "TORNEOS"
            yesButton {
            }
        }.show()
    }
    override fun snackBarIsActual() {
        Utils.showSnackBar(conteiner, getString(R.string.isactual_error))
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
                showProgressBarHideLayout()
                presenter.deleteTournament(applicationContext, tournament)
            }
            noButton { }
        }.show()
    }

    private fun activeTournament() {
        if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "torneo"))
        else {
            showProgressBarHideLayout()
            if (tournament.IS_ACTIVE == 0) tournament.IS_ACTIVE = 1 else tournament.IS_ACTIVE = 0
            presenter.activeOrUnActiveTournament(this, tournament)
        }
    }

    override fun boxEvent(position: Int, submenu: SubMenuDrawer, id_tournament: Int, isActual: Boolean) {
        this.position = position
        showProgressBarHideLayout()
        presenter.assignationUnassignation(this, submenu, id_tournament, isActual)
    }

    override fun cleanViews() {
        editTextTournament.text.clear()
        isUpdate = false
    }

    private fun saveTournament() {
        if (editTextTournament.text.isEmpty())
            editTextTournament.error = getString(R.string.editText_empty, "torneo")
        else {
            showProgressBarHideLayout()
            if (isUpdate) {
                tournament.TOURNAMENT = editTextTournament.text.toString()
                presenter.updateTournament(this, tournament)
            } else
                presenter.saveTournament(this, Tournament(0, editTextTournament.text.toString()))
        }
    }

    override fun setSubMenusTournaments(tournaments: MutableList<Tournament>) {
        this.tournaments = tournaments
        adapterSpinnerTournaments.refresh(tournaments, 6)
    }

    override fun refreshRecyclerAndSpinner() {
        presenter.getSubMenuTournaments(this)
        presenter.getSubMenuForId(this, tournament)
    }

    override fun assignationSuccess(menuDrawer: SubMenuDrawer, isActual: Boolean) {
        this.isActual = isActual
        adapterSubMenuForTournament.updateTournament(position, menuDrawer)
    }

    private fun initSpinnerAdapter() {
        spinnerTournament.adapter = adapterSpinnerTournaments
        spinnerTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                tournament = tournaments[pos]
                presenter.getSubMenuForId(applicationContext, tournament)
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterSubMenuForTournament
        }
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