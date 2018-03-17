package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Button
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.FixtureCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di.FixtureCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_fixture.*
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters.*
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragment
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class FixtureCreateFragment : BaseFragment(),
        FixtureCreateFragmentView,
        View.OnClickListener,
        CalendarDatePickerDialogFragment.OnDateSetListener,
        TimePickerDialogFragment.TimePickerDialogHandler {

    private lateinit var component: FixtureCreateFragmentComponent
    private lateinit var presenter: FixtureCreateFragmentPresenter
    private var localTeam = Team()
    private var visiteTeam = Team()
    private var subMenuDrawer = SubMenuDrawer()
    private var fieldMatch = FieldMatch()
    private var timeMatch = TimeMatch()
    private var tournament = Tournament()
    private var teamLocal = Team()
    private var teamVisite = Team()
    private lateinit var subMenuDrawers: MutableList<SubMenuDrawer>
    private lateinit var fieldMatchs: MutableList<FieldMatch>
    private lateinit var timeMatchs: MutableList<TimeMatch>
    private lateinit var tournaments: MutableList<Tournament>
    private lateinit var teamsLocal: MutableList<Team>
    private lateinit var teamsVisite: MutableList<Team>
    private lateinit var communication: Communicator
    private var isRegister: Boolean = false
    private var fixture = Fixture()
    private lateinit var adapterSpinnerSubMenus: FixtureCreateFragmentSubMenuSpinnerAdapter
    private lateinit var adapterSpinnerTimeMatch: FixtureCreateFragmentTimesSpinnerAdapter
    private lateinit var adapterSpinnerFieldMatch: FixtureCreateFragmentFieldSpinnerAdapter
    private lateinit var adapterSpinnerTournament: FixtureCreateFragmentTournamentSpinnerAdapter
    private lateinit var adapterSpinnerTeamLocal: FixtureCreateFragmentTeamSpinnerAdapter
    private lateinit var adapterSpinnerTeamVisite: FixtureCreateFragmentTeamSpinnerAdapter
    private var is_update: Boolean = false
    private var id_fixture: Int = 0
    private var day: String = ""
    private var hour: String = ""
    private val FRAG_TAG_DATE_PICKER = "fragment_date_picker_name"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        register()
        btnSave.text = getString(R.string.save_button, "Fixture")
        isFixtureUpdate()
        initSpinnerAdapter()
        getSpinnerData()
        setOnClick()
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_create_fixture

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setCommunicator(communicator: Communicator) {
        communication = communicator
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getFixtureCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
//        adapterSpinnerSubMenus = getAdapterSubMenus()
//        adapterSpinnerFieldMatch = getAdapterFieldMatch()
//        adapterSpinnerTimeMatch = getAdapterTimeMatch()
//        adapterSpinnerTournament = getAdapterTournament()
//        adapterSpinnerTeamLocal = getAdapterTeamLocal()
//        adapterSpinnerTeamVisite = getAdapterTeamVisite()
    }

    override fun onClick(onclick: View?) {
        when (onclick) {
            btnSubMenu -> dialogSpinnerSubMenu()
            btnTournament -> dialogSpinnerTournament()
            btnField -> dialogSpinnerField()
            btnTeamLocal -> dialogSpinnerTeamLocal()
            btnTeamVisite -> dialogSpinnerTeamVisite()
            btnTimeMatch -> dialogSpinnerTimeMatch()
            btnSave -> onClickButtonSave()
            buttonDay -> openCalendar()
            buttonHour -> openTimer()
            imageViewInformationFixture -> showAlertInformation()
            fabMenuSubMenu -> goToActivity(MenuSubMenuActivity())
            fabTournament -> goToActivity(TournamentActivity())
            fabTeam -> goToActivity(TabTeamActivity())
        //   fabField -> goToActivity(Fie)
        //  fabTimes ->
        }
    }

    private fun setOnClick() {
        btnSubMenu.setOnClickListener(this)
        btnTournament.setOnClickListener(this)
        btnField.setOnClickListener(this)
        btnTeamLocal.setOnClickListener(this)
        btnTeamVisite.setOnClickListener(this)
        btnTimeMatch.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        buttonDay.setOnClickListener(this)
        buttonHour.setOnClickListener(this)
        imageViewInformationFixture.setOnClickListener(this)
        fabMenuSubMenu.setOnClickListener(this)
        fabTournament.setOnClickListener(this)
        fabTeam.setOnClickListener(this)
        fabField.setOnClickListener(this)
        fabTimes.setOnClickListener(this)
    }

    private fun dialogSpinnerSubMenu() {
        val spinnerDialog = SpinnerDialog(activity, subMenuDrawers, "Seleccione un submenu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                subMenuDrawer = subMenuDrawers[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnSubMenu.text = item
                validateActive(btnSubMenu, subMenuDrawer.IS_ACTIVE, R.drawable.fixture_icon)
//                tvTournament.text = item
//                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerField() {
        val spinnerDialog = SpinnerDialog(activity, fieldMatchs, "Seleccione un lugar de juego", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                fieldMatch = fieldMatchs[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnTournament.text = item
   //               setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerTournament() {
        val spinnerDialog = SpinnerDialog(activity, tournaments, "Seleccione un torneo", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                tournament = tournaments[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnTournament.text = item
                validateActive(btnField, tournament.IS_ACTIVE, R.drawable.tournament_icon)
//
//                tvTournament.text = item
//                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerTeamLocal() {
        val spinnerDialog = SpinnerDialog(activity, teamsLocal, "Seleccione un equipo", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                teamLocal = teamsLocal[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnTeamLocal.text = item
                validateActive(btnTeamLocal, teamLocal.IS_ACTIVE, 0)
//
//                tvTournament.text = item
//                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerTeamVisite() {
        val spinnerDialog = SpinnerDialog(activity, teamsVisite, "Seleccione un equipo", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                teamVisite = teamsVisite[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnTeamVisite.text = item
                validateActive(btnTeamVisite, teamVisite.IS_ACTIVE, 0)
//
//                tvTournament.text = item
//                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerTimeMatch() {
        val spinnerDialog = SpinnerDialog(activity, timeMatchs, "Seleccione un tiempo de juego", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                timeMatch = timeMatchs[position]
//                presenter.getSubMenuForId(applicationContext, tournament)
                btnTimeMatch.text = item
                validateActive(btnTimeMatch, teamVisite.IS_ACTIVE, R.drawable.time_icon)
//
//                tvTournament.text = item
//                setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun validateActive(button: Button, is_active: Int, icone: Int) {
        if (is_active == 0)
            button.setCompoundDrawablesWithIntrinsicBounds(icone, 0, android.R.drawable.presence_busy, 0)
        else
            button.setCompoundDrawablesWithIntrinsicBounds(icone, 0, 0, 0)
    }

    private fun goToActivity(activity: Activity) {
        startActivity(Intent(getActivity(), activity::class.java))
    }

    private fun openCalendar() {
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this)
        cdp.show(fragmentManager, FRAG_TAG_DATE_PICKER)
    }

    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear + 1
        day = "$dayOfMonth-$month-$year" //"$year-$monthOfYear-$dayOfMonth"
        //textViewDay.text = "$dayOfMonth-$monthOfYear-$year"
        buttonDay.text = day
    }

    private fun openTimer() {
        val tpb = TimePickerBuilder()
                .setFragmentManager(fragmentManager)
                .setTargetFragment(this)
                .setStyleResId(R.style.BetterPickersDialogFragment)
        tpb.show()
    }

    override fun onDialogTimeSet(reference: Int, hourOfDay: Int, minute: Int) {
        hour = "$hourOfDay:$minute"
        buttonHour.text = hour
    }

//    private fun setVisivilityFab(fab: FloatingActionButton, isOpened: Boolean) {
//        if (isOpened) {
//            fab.visibility = View.VISIBLE
//            fab.show(true)
//        } else {
//            fab.visibility = View.GONE
//            fab.hide(true)
//        }
//    }

    private fun showAlertInformation() {
        alert(getString(R.string.alert_info_fixture)) {
            title = "FIXTURE"
            yesButton {
            }
        }.show()
    }

    private fun getSpinnerData() {
        presenter.getSpinnerData(activity)
    }

    override fun setSpinnersData(submenus: MutableList<SubMenuDrawer>,
                                 fieldMatchs: MutableList<FieldMatch>,
                                 timeMatchs: MutableList<TimeMatch>,
                                 tournaments: MutableList<Tournament>, teams: MutableList<Team>) {
        this.subMenuDrawers = submenus
        this.fieldMatchs = fieldMatchs
        this.timeMatchs = timeMatchs
        this.tournaments = tournaments
        this.teamsLocal = teams
        this.teamsVisite = teams
//        adapterSpinnerSubMenus.refresh(submenus)
//        adapterSpinnerFieldMatch.refresh(fieldMatchs)
//        adapterSpinnerTimeMatch.refresh(timeMatchs)
//        adapterSpinnerTournament.refresh(tournaments)
//        adapterSpinnerTeamLocal.refresh(teams)
//        adapterSpinnerTeamVisite.refresh(teams)
        if (is_update) {
            presenter.getFixture(activity, id_fixture)
        } else {
            hideProgressDialog()
            setVisibilityViews(View.VISIBLE)
        }
    }

    private fun getPresenterInj(): FixtureCreateFragmentPresenter =
            component.getPresenter()

//    private fun getAdapterSubMenus(): FixtureCreateFragmentSubMenuSpinnerAdapter =
//            component.getAdapterSubMenus()
//
//    private fun getAdapterFieldMatch(): FixtureCreateFragmentFieldSpinnerAdapter =
//            component.getAdapterFieldMatch()
//
//    private fun getAdapterTimeMatch(): FixtureCreateFragmentTimesSpinnerAdapter =
//            component.getAdapterTimeMatch()
//
//    private fun getAdapterTournament(): FixtureCreateFragmentTournamentSpinnerAdapter =
//            component.getAdapterTournament()
//
//    private fun getAdapterTeamLocal(): FixtureCreateFragmentTeamSpinnerAdapter =
//            component.getAdapterTeamLocal()
//
//    private fun getAdapterTeamVisite(): FixtureCreateFragmentTeamSpinnerAdapter =
//            component.getAdapterTeamVisite()

    private fun isFixtureUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_fixture = activity.intent.getIntExtra("id_fixture", 0)
            btnSave.text = getString(R.string.update_button, "Fixture")
        }
    }

    private fun initSpinnerAdapter() {
//        spinnerSubMenu.adapter = adapterSpinnerSubMenus
//        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                subMenuDrawer = subMenuDrawers[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }
//        spinnerFieldMatch.adapter = adapterSpinnerFieldMatch
//        spinnerFieldMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                fieldMatch = fieldMatchs[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }
//        spinnerTimeMatch.adapter = adapterSpinnerTimeMatch
//        spinnerTimeMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                timeMatch = timeMatchs[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }

//        spinnerTournament.adapter = adapterSpinnerTournament
//        spinnerTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                tournament = tournaments[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }
//        spinnerTeamLocal.adapter = adapterSpinnerTeamLocal
//        spinnerTeamLocal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                teamLocal = teamsLocal[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }
//        spinnerTeamVisite.adapter = adapterSpinnerTeamVisite
//        spinnerTeamVisite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                teamVisite = teamsVisite[pos]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//            }
//        }
    }

    override fun onClickButtonSave() {
        if (subMenuDrawers.isEmpty())
            setError(getString(R.string.spinner_empty, "un submenu."))
        else if (fieldMatchs.isEmpty())
            setError(getString(R.string.spinner_empty, "un lugar."))
        else if (timeMatchs.isEmpty())
            setError(getString(R.string.spinner_empty, "un tiempo."))
        else if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "un torneo."))
        else if (teamsLocal.isEmpty() || teamsVisite.isEmpty())
            setError(getString(R.string.spinner_empty, "un equipo."))
        else if (teamLocal.ID_TEAM_KEY == teamVisite.ID_TEAM_KEY)
            setError(getString(R.string.same_team_error))
        else if (day.isEmpty())
            setError(getString(R.string.day_empty))
        else if (hour.isEmpty())
            setError(getString(R.string.hour_empty))
        else
            fillFixtureEntity()
    }

    private fun fillFixtureEntity() {
        fixture.ID_SUBMENU_KEY = subMenuDrawer.ID_SUBMENU_KEY
        fixture.ID_LOCAL_TEAM = localTeam.ID_TEAM_KEY
        fixture.ID_VISITE_TEAM = visiteTeam.ID_TEAM_KEY
        fixture.ID_FIELD_MATCH = fieldMatch.ID_FIELD_MATCH_KEY
        fixture.ID_TIMES_MATCH = timeMatch.ID_TIME_MATCH_KEY
        fixture.ID_TOURNAMENT = tournament.ID_TOURNAMENT_KEY
        fixture.ID_LOCAL_TEAM = teamLocal.ID_TEAM_KEY
        fixture.ID_VISITE_TEAM = teamVisite.ID_TEAM_KEY
        fixture.DATE_MATCH = day
        fixture.HOUR_MATCH = hour
        fixture.OBSERVATION = editTextObservation.text.toString()
        fixture.RESULT_LOCAL = editTextResultLocal.text.toString()
        fixture.RESULT_VISITE = editTextResultVisite.text.toString()
        if (is_update) {
            fixture.ID_FIXTURE_KEY = id_fixture
            editFixture(fixture)
        } else {
            saveFixture(fixture)
        }
    }

    private fun saveFixture(fixture: Fixture) {
        presenter.saveFixture(activity, fixture)
    }

    private fun editFixture(fixture: Fixture) {
        presenter.updateFixture(activity, fixture)
    }

    override fun setFixtureUpdate(fixture: Fixture) {
        this.fixture = fixture
    }

    override fun fillViewUpdate() {
        with(fixture) {
            id_fixture = ID_FIXTURE_KEY
            
           // spinnerSubMenu.setSelection(getPositionSpinners(ID_SUBMENU_KEY, 1))
           // spinnerFieldMatch.setSelection(getPositionSpinners(ID_FIELD_MATCH, 2))
           // spinnerTimeMatch.setSelection(getPositionSpinners(ID_TIMES_MATCH, 3))
           // spinnerTournament.setSelection(getPositionSpinners(ID_TOURNAMENT, 4))
            //spinnerTeamLocal.setSelection(getPositionSpinners(ID_LOCAL_TEAM, 5))
            //spinnerTeamVisite.setSelection(getPositionSpinners(ID_VISITE_TEAM, 6))
            day = DATE_MATCH
            hour = HOUR_MATCH
            buttonDay.text = day
            buttonHour.text = hour
            editTextResultLocal.text = Editable.Factory.getInstance().newEditable(RESULT_LOCAL)
            editTextResultVisite.text = Editable.Factory.getInstance().newEditable(RESULT_VISITE)
            editTextObservation.text = Editable.Factory.getInstance().newEditable(OBSERVATION)
        }
    }

    private fun fillUpdateButton(button: Button){

    }

    private fun getPositionLists(id: Int, type: Int) {
        //var index = 0
        when (type) {

            1 -> {
                for (i in 0 until subMenuDrawers.size) {
                    if (subMenuDrawers[i].ID_SUBMENU_KEY == id) {
                        subMenuDrawer = subMenuDrawers[i]
                        btnSubMenu.text = subMenuDrawer.toString()
                        break
                    }
                }
            }
            2 -> {
                for (i in 0 until fieldMatchs.size) {
                    if (fieldMatchs[i].ID_FIELD_MATCH_KEY == id) {
                        fieldMatch = fieldMatchs[i]
                        btnField.text = fieldMatch.FIELD_MATCH
                        break
                    }
                }
            }
            3 -> {
                for (i in 0 until timeMatchs.size) {
                    if (timeMatchs[i].ID_TIME_MATCH_KEY == id) {
                        timeMatch = timeMatchs[i]
                        btnTimeMatch.text = timeMatch.TIME_MATCH
                        break
                    }
                }
            }
            4 -> {
                for (i in 0 until tournaments.size) {
                    if (tournaments[i].ID_TOURNAMENT_KEY == id) {
                        tournament = tournaments[i]
                        btnTournament.text = tournament.TOURNAMENT
                        break
                    }
                }
            }
            5 -> {
                for (i in 0 until teamsLocal.size) {
                    if (teamsLocal[i].ID_TEAM_KEY == id) {
                        teamLocal = teamsLocal[i]
                        btnTeamLocal.text = teamLocal.NAME
                        break
                    }
                }
            }
            6 -> {
                for (i in 0 until teamsVisite.size) {
                    if (teamsVisite[i].ID_TEAM_KEY == id) {
                        teamVisite = teamsVisite[i]
                        btnTeamVisite.text = teamVisite.NAME
                        break
                    }
                }
            }
        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.save_success, "Fixture"))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Fixture"))
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
        fixture = Fixture()
        editTextResultLocal.text.clear()
        editTextResultVisite.text.clear()
        editTextObservation.text.clear()
        buttonDay.text = getString(R.string.day)
        buttonHour.text = getString(R.string.hour)
        btnSave.text = getString(R.string.save_button, "Fixture")
        is_update = false
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerFixture.visibility = isVisible
        btnSave.visibility = isVisible
    }

    private fun calendarOnResume() {
        val calendarDatePickerDialogFragment = fragmentManager
                .findFragmentByTag(FRAG_TAG_DATE_PICKER) as CalendarDatePickerDialogFragment?
        calendarDatePickerDialogFragment?.setOnDateSetListener(this)
    }
}