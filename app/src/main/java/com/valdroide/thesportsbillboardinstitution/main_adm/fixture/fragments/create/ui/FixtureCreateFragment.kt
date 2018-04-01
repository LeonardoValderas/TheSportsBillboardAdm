package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.FixtureCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import kotlinx.android.synthetic.main.fragment_create_fixture.*
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragment
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import javax.inject.Inject

class FixtureCreateFragment : BaseFragment(),
        FixtureCreateFragmentView,
        View.OnClickListener,
        CalendarDatePickerDialogFragment.OnDateSetListener,
        TimePickerDialogFragment.TimePickerDialogHandler {

    @Inject
    lateinit var presenter: FixtureCreateFragmentPresenter
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
    private var fixture = Fixture()
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
        app.getFixtureCreateFragmentComponent(this, this).inject(this)
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
        setOnClick(btnSubMenu)
        setOnClick(btnTournament)
        setOnClick(btnField)
        setOnClick(btnTeamLocal)
        setOnClick(btnTeamVisite)
        setOnClick(btnTimeMatch)
        setOnClick(btnSave)
        setOnClick(buttonDay)
        setOnClick(buttonHour)
        setOnClick(imageViewInformationFixture)
        setOnClick(fabMenuSubMenu)
        setOnClick(fabTournament)
        setOnClick(fabTeam)
        setOnClick(fabField)
        setOnClick(fabTimes)
    }

    private fun setOnClick(view: View) {
        view.setOnClickListener(this)
    }

    private fun dialogSpinnerSubMenu() {
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.SUBMENU))
            return

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

    private fun showMessageListEmpty(entity: String) {
        setError(getString(R.string.spinner_empty, entity))
    }

    private fun dialogSpinnerField() {
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.FIELD))
            return

        val spinnerDialog = SpinnerDialog(activity, fieldMatchs, "Seleccione un lugar de juego", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                fieldMatch = fieldMatchs[position]
                //presenter.getSubMenuForId(applicationContext, tournament)
                btnField.text = item
                //setDividerLine(tournament.IS_ACTIVE)
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerTournament() {
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.TOURNAMENT))
            return

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
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.TEAM))
            return

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
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.TEAM))
            return
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
        if (validateLists(ConstantHelper.ENTITY.ENTITIES.TIME))
            return

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
        if (minute.length() == 1)
            minute.plus(0)
        hour = "$hourOfDay:$minute"
        buttonHour.text = hour
    }

    fun Int.length() = when (this) {
        0 -> 1
        else -> Math.log10(Math.abs(toDouble())).toInt() + 1
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
        if (is_update) {
            presenter.getFixture(activity, id_fixture)
        } else {
            hideProgressDialog()
            setVisibilityViews(View.VISIBLE)
        }
    }

    private fun isFixtureUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_fixture = activity.intent.getIntExtra("id_fixture", 0)
            btnSave.text = getString(R.string.update_button, "Fixture")
        }
    }

    override fun onClickButtonSave() {
        if (validateListsEmptySave())
            return
        else if (teamLocal.ID_TEAM_KEY == teamVisite.ID_TEAM_KEY)
            setError(getString(R.string.same_team_error))
        else if (day.isEmpty())
            setError(getString(R.string.day_empty))
        else if (hour.isEmpty())
            setError(getString(R.string.hour_empty))
        else
            fillFixtureEntity()
    }

    private fun validateListsEmptySave(): Boolean {
        var isEmpty = false
        run breaker@ {
            enumValues<ConstantHelper.ENTITY.ENTITIES>().takeWhile {
                !isEmpty
            }.forEach {
                        if (validateLists(it)) {
                            isEmpty = true
                            return@breaker
                        }
                    }
        }
        return isEmpty
    }

    private fun validateLists(entity: ConstantHelper.ENTITY.ENTITIES): Boolean {
        when (entity) {
            ConstantHelper.ENTITY.ENTITIES.SUBMENU -> {
                if (validateListsEntities(subMenuDrawers)) {
                    showMessageListEmpty("un sub menu")
                    return true
                } else
                    return false
            }
            ConstantHelper.ENTITY.ENTITIES.TOURNAMENT -> {
                if (validateListsEntities(tournaments)) {
                    showMessageListEmpty("un torneo")
                    return true
                } else
                    return false
            }
            ConstantHelper.ENTITY.ENTITIES.TEAM -> {
                if (validateListsEntities(teamsLocal)) {
                    showMessageListEmpty("dos equipos")
                    return true
                } else
                    return false
            }
            ConstantHelper.ENTITY.ENTITIES.TIME -> {
                if (validateListsEntities(timeMatchs)) {
                    showMessageListEmpty("un tiempo de juego")
                    return true
                } else
                    return false
            }
            ConstantHelper.ENTITY.ENTITIES.FIELD -> {
                if (validateListsEntities(fieldMatchs)) {
                    showMessageListEmpty("un campo de juego")
                    return true
                } else
                    return false
            }
        }
        return false
    }

    private fun validateListsEntities(list: MutableList<*>) = list.isEmpty()

    private fun fillFixtureEntity() {
        with(fixture) {
            ID_SUBMENU_KEY = subMenuDrawer.ID_SUBMENU_KEY
            ID_LOCAL_TEAM = localTeam.ID_TEAM_KEY
            ID_VISITE_TEAM = visiteTeam.ID_TEAM_KEY
            ID_FIELD_MATCH = fieldMatch.ID_FIELD_MATCH_KEY
            ID_TIMES_MATCH = timeMatch.ID_TIME_MATCH_KEY
            ID_TOURNAMENT = tournament.ID_TOURNAMENT_KEY
            ID_LOCAL_TEAM = teamLocal.ID_TEAM_KEY
            ID_VISITE_TEAM = teamVisite.ID_TEAM_KEY
            DATE_MATCH = day
            HOUR_MATCH = hour
            OBSERVATION = editTextObservation.text.toString()
            RESULT_LOCAL = editTextResultLocal.text.toString()
            RESULT_VISITE = editTextResultVisite.text.toString()
            if (is_update) {
                ID_FIXTURE_KEY = id_fixture
                editFixture(fixture)
            } else {
                saveFixture(fixture)
            }
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
            setEntityForId(this)
            day = DATE_MATCH
            hour = HOUR_MATCH
            buttonDay.text = day
            buttonHour.text = hour
            editTextResultLocal.text = Editable.Factory.getInstance().newEditable(RESULT_LOCAL)
            editTextResultVisite.text = Editable.Factory.getInstance().newEditable(RESULT_VISITE)
            editTextObservation.text = Editable.Factory.getInstance().newEditable(OBSERVATION)
        }
    }

    private fun setEntityForId(fixture: Fixture) {

        with(fixture) {
            for (i in 0 until subMenuDrawers.size) {
                if (subMenuDrawers[i].ID_SUBMENU_KEY == ID_SUBMENU_KEY) {
                    subMenuDrawer = subMenuDrawers[i]
                    btnSubMenu.text = subMenuDrawer.toString()
                    break
                }
            }
            for (i in 0 until fieldMatchs.size) {
                if (fieldMatchs[i].ID_FIELD_MATCH_KEY == ID_FIELD_MATCH) {
                    fieldMatch = fieldMatchs[i]
                    btnField.text = fieldMatch.FIELD_MATCH
                    break
                }
            }
            for (i in 0 until timeMatchs.size) {
                if (timeMatchs[i].ID_TIME_MATCH_KEY == ID_TIMES_MATCH) {
                    timeMatch = timeMatchs[i]
                    btnTimeMatch.text = timeMatch.TIME_MATCH
                    break
                }
            }

            for (i in 0 until tournaments.size) {
                if (tournaments[i].ID_TOURNAMENT_KEY == ID_TOURNAMENT) {
                    tournament = tournaments[i]
                    btnTournament.text = tournament.TOURNAMENT
                    break
                }
            }
            for (i in 0 until teamsLocal.size) {
                if (teamsLocal[i].ID_TEAM_KEY == ID_LOCAL_TEAM) {
                    teamLocal = teamsLocal[i]
                    btnTeamLocal.text = teamLocal.NAME
                    break
                }
            }

            for (i in 0 until teamsVisite.size) {
                if (teamsVisite[i].ID_TEAM_KEY == ID_VISITE_TEAM) {
                    teamVisite = teamsVisite[i]
                    btnTeamVisite.text = teamVisite.NAME
                    break
                }
            }

        }
    }

    override fun saveSuccess() {
        communication.refreshAdapter()
        showShackbar(getString(R.string.save_success, "Fixture"))
    }

    override fun editSuccess() {
        communication.refreshAdapter()
        showShackbar(getString(R.string.update_success, "Fixture"))
    }

    override fun setError(error: String) {
        showShackbar(error)
    }

    private fun showShackbar(msg: String) = ViewComponentHelper.showSnackBar(conteiner, msg)

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