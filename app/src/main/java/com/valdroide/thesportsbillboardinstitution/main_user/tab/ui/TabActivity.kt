package com.valdroide.thesportsbillboardinstitution.main_user.tab.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import com.google.gson.Gson
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragment
import com.valdroide.thesportsbillboardinstitution.main_user.tab.TabActivityPresenter
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivityUserTabs
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import kotlinx.android.synthetic.main.activity_user_tab.*
import kotlinx.android.synthetic.main.content_tab.*
import javax.inject.Inject

open class
TabActivity : BaseActivityUserTabs(), TabActivityView {


    inline fun <reified T> Gson.fromJson(json: String): T =
            this.fromJson<T>(json, T::class.java)

    override val tournaments: MutableList<Tournament>
        get() = tournamentsList!!

    override val titles: Array<String>
        get() = arrayOf(getString(R.string.fixture_title_tab),
                getString(R.string.table_title_tab), getString(R.string.news_title_tab), getString(R.string.sanction_title_tab))

    override val fragments: Array<Fragment>
        get() = fragmensList!!.toTypedArray()

    override fun getUpdateFragment(): Fragment = Fragment() // VERRRRRRRRRRRRRRRRRRRRRRRRRR


    @Inject
    lateinit var presenter: TabActivityPresenter
    private var tournamentsList: MutableList<Tournament>? = null
    private var fragmensList: MutableList<Fragment>? = null
    private var menu: MenuDrawer? = null
    private var id_tournament = 0
    var fixtureFragment: FixtureFragment? = null
    var leaderBoardFragment: LeaderBoardFragment? = null
    var newFragment: NewsFragment? = null
    var sanctionFragment: SanctionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = getExtraIntent()
        if (menu != null) {
            initToobar()
            presenter.getTournamentoForIdSubmenu(this, menu!!.ID_MENU_KEY)
        } else
            showErrorAndFinish()
    }

    private fun getExtraIntent(): MenuDrawer? {
        try {
            val entity_string = intent.getStringExtra(ConstantHelper.JSON_ENTITY)

            if (!entity_string.isNullOrEmpty()) {
                val enti: MenuDrawer = Gson().fromJson<MenuDrawer>(entity_string)
                return enti
            }

        } catch (e: Exception) {
            showErrorAndFinish()
        }
        return null
    }

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        // val title: String = SharedHelper.getSubmenuTitle(this)
        getSupportActionBar()!!.setTitle(menu.toString())
    }

    private fun showErrorAndFinish() {
        ViewComponentHelper.showToast(this, getString(R.string.generic_error_response), Toast.LENGTH_LONG)
        finish()
    }

    override fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        app.getTabActivityComponent(this, this).inject(this)
    }

    private fun initSpinner() {
        val spinner = getSpinner()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                positionViewPager = viewPager.currentItem
                id_tournament = tournaments[pos].ID_TOURNAMENT_KEY
                updateFragmentsBySpinnerSelected()
                //setPagerAdapter()
                viewPager.currentItem = positionViewPager
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
    }

    private fun updateFragmentsBySpinnerSelected() {
        // fixtureFragment.updateVariablesArgument(id_tournament)
        //    leaderBoardFragment.updateVariablesArgument(id_tournament)
        newFragment!!.updateVariablesArgument(id_tournament)
        //  value.updateVariablesArgument(id_tournament)
    }

    override fun setTournaments(tournaments: MutableList<Tournament>) {
        if (tournaments != null || tournaments.isEmpty())
            setError("No se encontraron torneos activos.")
        else {
            tournamentsList = tournaments
            initSpinner()
            fillIdTournament()
            fragmensList = initListFragment(menu!!.ID_MENU_KEY)
        }

        setPagerAdapter()
    }

    private fun fillIdTournament() {
        val actual = validateCurrentTournament()
        if (actual != null)
            id_tournament = actual.ID_TOURNAMENT_KEY
        else
            id_tournament = 0
    }

    private fun validateCurrentTournament(): Tournament? = tournamentsList!!.firstOrNull { it.IS_ACTUAL != 0 }

    override fun setError(msg: String) {
        ViewComponentHelper.showToast(this, msg, Toast.LENGTH_LONG)
        finish()
    }

    private fun initListFragment(id_menu: Int): MutableList<Fragment> {
        initFragmentsInstance(id_menu)
        return mutableListOf(fixtureFragment!!,
                leaderBoardFragment!!,
                newFragment!!,
                sanctionFragment!!)
    }

    fun initFragmentsInstance(id_menu: Int) {
        fixtureFragment = FixtureFragment.newInstance(id_menu, id_tournament)
        leaderBoardFragment = LeaderBoardFragment.newInstance(id_menu, id_tournament)
        newFragment = NewsFragment.newInstance(id_menu)
        sanctionFragment = SanctionFragment.newInstance(id_menu, id_tournament)
    }

    override fun hideProgressBar() {
        // try use in the fragments

        /*progressBar.visibility = View.GONE
        linearConteiner.visibility = View.VISIBLE
        fabConteiner.visibility = View.VISIBLE
    */
    }

    override fun showProgressBar() {

        /*      progressBar.visibility = View.VISIBLE
              linearConteiner.visibility = View.INVISIBLE
              fabConteiner.visibility = View.INVISIBLE*/
    }


//spinerrrrr methods


//      SAN ESTEBAN
//    private fun setPagerAdapter() {
//        val titles = arrayOf(getString(R.string.news_title_tab), getString(R.string.fixture_title_tab),
//                getString(R.string.table_title_tab), getString(R.string.sanstion_title_tab), getString(R.string.player_title_tab))

//        val fragments = arrayOf(NewsFragment(), FixtureFragment(), LeaderBoardFragment(), SanctionFragment(), PlayerFragment())
//        adapter = SectionsPagerAdapter(supportFragmentManager, titles, fragments)
//    }

}
