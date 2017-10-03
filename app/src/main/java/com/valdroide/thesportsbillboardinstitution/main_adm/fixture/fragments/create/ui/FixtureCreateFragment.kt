package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui

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
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity


class FixtureCreateFragment : Fragment(), FixtureCreateFragmentView, View.OnClickListener {

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


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_create_fixture, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        communication = activity as Communicator
        register()
        isFixtureUpdate()
        initSpinnerAdapter()
        getSubMenuAndPlayers()
        if (is_update) {
            setVisibilityViews(View.INVISIBLE)
            presenter.getFixture(activity, id_fixture)
        } else
            setVisibilityViews(View.VISIBLE)
        setOnclik()

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
        when(onclick){
            buttonSave -> onClickButtonSave()
            fabGoToSubMenu -> startActivity(Intent(activity, MenuSubMenuActivity::class.java))
            fabGoToTeam -> startActivity(Intent(activity, TabTeamActivity::class.java))
        }

    }

    private fun setOnclik() {
        buttonSave.setOnClickListener(this)
        fabGoToSubMenu.setOnClickListener(this)
        fabGoToTeam.setOnClickListener(this)
//        fabConteiner.setOnMenuToggleListener(FloatingActionMenu.OnMenuToggleListener { opened ->
//            setVisivilityFab(fabCreateTournament, opened)
//            setVisivilityFab(fabUpdateTournament, opened)
//            setVisivilityFab(fabDeleteTournament, opened)
//        })
    }

    private fun setVisivilityFab(fab: FloatingActionButton, isOpened: Boolean) {
        if (isOpened) {
            fab.visibility = View.VISIBLE
            fab.show(true)
        } else {
            fab.visibility = View.GONE
            fab.hide(true)
        }
    }


    private fun getSubMenuAndPlayers() {
        showProgressDialog()
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
            setError(getString(R.string.spinner_empty, "un menu"))
        else if (fieldMatchs.isEmpty())
            setError(getString(R.string.spinner_empty, "un lugar"))
        else if (timeMatchs.isEmpty())
            setError(getString(R.string.spinner_empty, "un tiempo"))
        else if (tournaments.isEmpty())
            setError(getString(R.string.spinner_empty, "un torneo"))
        else
            fillFixtureEntity()
    }

    private fun fillFixtureEntity() {
        showProgressDialog()
        setVisibilityViews(View.INVISIBLE)
        fixture.ID_SUBMENU_KEY = subMenuDrawer.ID_SUBMENU_KEY
        fixture.ID_LOCAL_TEAM = localTeam.ID_TEAM_KEY
        fixture.ID_VISITE_TEAM = visiteTeam.ID_TEAM_KEY
        fixture.ID_FIELD_MATCH = fieldMatch.ID_FIELD_MATCH_KEY
        fixture.ID_TIMES_MATCH = timeMatch.ID_TIME_MATCH_KEY
        fixture.ID_TOURNAMENT = tournament.ID_TOURNAMENT_KEY
        // fixture.DATE_MATCH =
        //fixture.HOUR_MATCH =
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
            textViewDay.text = DATE_MATCH
            textViewHour.text = HOUR_MATCH
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