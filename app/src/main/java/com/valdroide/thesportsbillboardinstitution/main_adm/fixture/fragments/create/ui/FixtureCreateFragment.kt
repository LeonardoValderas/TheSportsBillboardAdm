package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui

import android.app.Activity
import android.content.Intent
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
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.FixtureCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di.FixtureCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import com.valdroide.thesportsbillboardinstitution.utils.Communicator
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_create_fixture.*
import com.github.clans.fab.FloatingActionButton
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class FixtureCreateFragment : Fragment(),
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
    private lateinit var adapterSpinnerSubMenus: GenericSpinnerAdapter
    private lateinit var adapterSpinnerTimeMatch: GenericSpinnerAdapter
    private lateinit var adapterSpinnerFieldMatch: GenericSpinnerAdapter
    private lateinit var adapterSpinnerTournament: GenericSpinnerAdapter
    private lateinit var adapterSpinnerTeamLocal: GenericSpinnerAdapter
    private lateinit var adapterSpinnerTeamVisite: GenericSpinnerAdapter
    private var is_update: Boolean = false
    private var id_fixture: Int = 0
    private var day: String = ""
    private var hour: String = ""
    private val FRAG_TAG_DATE_PICKER = "fragment_date_picker_name"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_fixture, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        textViewButton.text = getString(R.string.save_button, "Fixture")
        isFixtureUpdate()
        initSpinnerAdapter()
        getSpinnerData()
        setOnClick()
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getFixtureCreateFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterSpinnerSubMenus = getAdapterSubMenus()
        adapterSpinnerFieldMatch = getAdapterFieldMatch()
        adapterSpinnerTimeMatch = getAdapterTimeMatch()
        adapterSpinnerTournament = getAdapterTournament()
        adapterSpinnerTeamLocal = getAdapterTeamLocal()
        adapterSpinnerTeamVisite = getAdapterTeamVisite()
    }

    override fun onClick(onclick: View?) {
        when (onclick) {
            buttonSave -> onClickButtonSave()
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
        buttonSave.setOnClickListener(this)
        buttonDay.setOnClickListener(this)
        buttonHour.setOnClickListener(this)
        imageViewInformationFixture.setOnClickListener(this)
        fabMenuSubMenu.setOnClickListener(this)
        fabTournament.setOnClickListener(this)
        fabTeam.setOnClickListener(this)
        fabField.setOnClickListener(this)
        fabTimes.setOnClickListener(this)
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
        day = "$dayOfMonth-$monthOfYear-$year" //"$year-$monthOfYear-$dayOfMonth"
        //textViewDay.text = "$dayOfMonth-$monthOfYear-$year"
        textViewDay.text = day
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
        textViewHour.text = hour
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
        alert("En esta opción usted podrá realizar acciones sobre los fixtures.\n" +
                "Para crear un fixture debe seleccionar el submenu, el torneo(ver información sobre torneo actual en la opción 'Torneos'), dia y hora del partido, lugar donde se disputara el encuentro, el equipo local y el visitante(no pueden ser iguales). Ingresar el resultado(esta opción puede quedar vacia en el caso que el encuentro todavia no se haya jugado), seleccionar estado de partido e ingresar una observación(optional).\n" +
                "En la sopala Editar usted podrá actualizar los datos de los fixture, modificar los resultados como también eliminarlos.") {
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
        adapterSpinnerSubMenus.refresh(submenus, 1)
        adapterSpinnerFieldMatch.refresh(fieldMatchs, 4)
        adapterSpinnerTimeMatch.refresh(timeMatchs, 5)
        adapterSpinnerTournament.refresh(tournaments, 6)
        adapterSpinnerTeamLocal.refresh(teams, 7)
        adapterSpinnerTeamVisite.refresh(teams, 7)
        if (is_update) {
            presenter.getFixture(activity, id_fixture)
        } else {
            hideProgressDialog()
            setVisibilityViews(View.VISIBLE)
        }
    }

    private fun getPresenterInj(): FixtureCreateFragmentPresenter =
            component.getPresenter()

    private fun getAdapterSubMenus(): GenericSpinnerAdapter =
            component.getAdapterSubMenus()

    private fun getAdapterFieldMatch(): GenericSpinnerAdapter =
            component.getAdapterFieldMatch()

    private fun getAdapterTimeMatch(): GenericSpinnerAdapter =
            component.getAdapterTimeMatch()

    private fun getAdapterTournament(): GenericSpinnerAdapter =
            component.getAdapterTournament()

    private fun getAdapterTeamLocal(): GenericSpinnerAdapter =
            component.getAdapterTeamLocal()

    private fun getAdapterTeamVisite(): GenericSpinnerAdapter =
            component.getAdapterTeamVisite()

    private fun isFixtureUpdate() {
        is_update = activity.intent.getBooleanExtra("is_update", false)
        if (is_update) {
            id_fixture = activity.intent.getIntExtra("id_fixture", 0)
            textViewButton.text = getString(R.string.update_button, "Fixture")
        }
    }

    private fun initSpinnerAdapter() {
        spinnerSubMenu.adapter = adapterSpinnerSubMenus
        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                subMenuDrawer = subMenuDrawers[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerFieldMatch.adapter = adapterSpinnerFieldMatch
        spinnerFieldMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                fieldMatch = fieldMatchs[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerTimeMatch.adapter = adapterSpinnerTimeMatch
        spinnerTimeMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                timeMatch = timeMatchs[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerTournament.adapter = adapterSpinnerTournament
        spinnerTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                tournament = tournaments[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerTeamLocal.adapter = adapterSpinnerTeamLocal
        spinnerTeamLocal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                teamLocal = teamsLocal[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
        spinnerTeamVisite.adapter = adapterSpinnerTeamVisite
        spinnerTeamVisite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                teamVisite = teamsVisite[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
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

    override fun setFixtureUpdate(fixture: Fixture) {
        this.fixture = fixture
    }

    override fun fillViewUpdate() {
        with(fixture) {
            id_fixture = ID_FIXTURE_KEY
            spinnerSubMenu.setSelection(getPositionSpinners(ID_SUBMENU_KEY, 1))
            spinnerFieldMatch.setSelection(getPositionSpinners(ID_FIELD_MATCH, 2))
            spinnerTimeMatch.setSelection(getPositionSpinners(ID_TIMES_MATCH, 3))
            spinnerTournament.setSelection(getPositionSpinners(ID_TOURNAMENT, 4))
            spinnerTeamLocal.setSelection(getPositionSpinners(ID_LOCAL_TEAM, 5))
            spinnerTeamVisite.setSelection(getPositionSpinners(ID_VISITE_TEAM, 6))
            day = DATE_MATCH
            hour = HOUR_MATCH
            textViewDay.text = day
            textViewHour.text = hour
            editTextResultLocal.text = Editable.Factory.getInstance().newEditable(RESULT_LOCAL)
            editTextResultVisite.text = Editable.Factory.getInstance().newEditable(RESULT_VISITE)
            editTextObservation.text = Editable.Factory.getInstance().newEditable(OBSERVATION)
        }
    }

    private fun getPositionSpinners(id: Int, type: Int): Int {
        var index = 0
        when (type) {

            1 -> {
                for (i in 0 until subMenuDrawers.size) {
                    if (subMenuDrawers[i].ID_SUBMENU_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
            2 -> {
                for (i in 0 until fieldMatchs.size) {
                    if (fieldMatchs[i].ID_FIELD_MATCH_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
            3 -> {
                for (i in 0 until timeMatchs.size) {
                    if (timeMatchs[i].ID_TIME_MATCH_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
            4 -> {
                for (i in 0 until tournaments.size) {
                    if (tournaments[i].ID_TOURNAMENT_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
            5 -> {
                for (i in 0 until teamsLocal.size) {
                    if (teamsLocal[i].ID_TEAM_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
            6 -> {
                for (i in 0 until teamsVisite.size) {
                    if (teamsVisite[i].ID_TEAM_KEY == id) {
                        index = i
                        return index
                    }
                }
            }
        }
        return index
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
        textViewDay.text = getString(R.string.day)
        textViewHour.text = getString(R.string.hour)
        textViewButton.text = getString(R.string.save_button, "Fixture")
        is_update = false
    }

    override fun setVisibilityViews(isVisible: Int) {
        linearConteinerFixture.visibility = isVisible
        buttonSave.visibility = isVisible
    }

    private fun calendarOnResume(){
        val calendarDatePickerDialogFragment = fragmentManager
                .findFragmentByTag(FRAG_TAG_DATE_PICKER) as CalendarDatePickerDialogFragment?
        calendarDatePickerDialogFragment?.setOnDateSetListener(this)
    }

    override fun onPause() {
        unregister()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        register()
        calendarOnResume()
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