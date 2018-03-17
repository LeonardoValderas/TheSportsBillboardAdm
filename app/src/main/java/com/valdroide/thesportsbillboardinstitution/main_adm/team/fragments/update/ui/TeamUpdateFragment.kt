package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.TeamUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import kotlinx.android.synthetic.main.fragment_fixture.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.utils.*
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragment
import javax.inject.Inject

class TeamUpdateFragment : BaseFragment(), TeamUpdateFragmentView, GenericOnItemClick<Team>, OnMenuItemClickListener {

    //region INIT VARIABLE
    @Inject
    lateinit var presenter: TeamUpdateFragmentPresenter
    lateinit var adapterTeam: TeamUpdateFragmentAdapter
    var teams: MutableList<Team> = arrayListOf()
    var team = Team()
    private var position: Int = 0
    //endregion

    //region LIFECYCLE
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getTeams(activity)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_fixture

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setCommunicator(communicator: Communicator) {}
    //endregion

    //region INJECTION
    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getTeamUpdateFragmentComponent(this, this, this).inject(this)
        adapterTeam = getAdapter()
    }

    //I dont realice inject because a have error
    open fun getAdapter(): TeamUpdateFragmentAdapter = TeamUpdateFragmentAdapter(activity, this)
    //endregion

    //region PRESENTER
    fun refreshAdapter() {
        presenter.getTeams(activity)
    }
   //endregion

    //region ALERT DIALOG
    private fun showAlertDialog(titleText: String, msg: String, team: Team) {
        alert(msg) {
            title = titleText
            yesButton {
                presenter.deleteTeam(activity, team)
            }
            noButton {}
        }.show()
    }
    //endregion

    //region GETEXTRA
   private fun onClickUpdate(team: Team) {
        val i = Intent(activity, TabTeamActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_team", team.ID_TEAM_KEY)
        startActivity(i)
    }
   //endregion

    //region ONCLICK
    override fun onClick(view: View, position: Int, t: Team) {
        this.position = position
        team = t
        CustomDialogMenu.Builder(activity)
                .setOnClick(this)
                .withTitles(resources.getStringArray(R.array.menu_title_3))
                .show()
    }

    override fun onClick(type: Int) {
        when (type) {
            1 -> onClickUpdate(team)
            2 -> {
                team.IS_ACTIVE = if(team.IS_ACTIVE == 0) 1 else 0
                presenter.activeUnActiveTeam(activity, team)
            }
            3 -> showAlertDialog(getString(R.string.alert_title), getString(R.string.alert_msg_team), team)
        }
    }

    override fun updateTeamSuccess() {
        adapterTeam.notifyDataSetChanged()
        showSnackBar( getString(R.string.update_success, "Equipo", "o"))
    }
    //endregion

    //region RECYCLER
    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterTeam
        }
    }
    //endregion

    //region VIEW
    override fun setAllTeams(teams: MutableList<Team>) {
        this.teams = teams
        adapterTeam.addAll(teams)
    }

    override fun deleteTeamSuccess() {
        adapterTeam.delete(position)
        showSnackBar( activity.getString(R.string.delete_success, "Equipo", "o"))
    }

    override fun setError(error: String) {
        showSnackBar(error)
    }

    private fun showSnackBar(msg: String){
        Utils.showSnackBar(conteiner, msg)
    }
    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }
    //endregion

    //region SWIPE
    private fun verifySwipeRefresh(show: Boolean) {
        try {
            if (swipeRefreshLayout != null) {
                if (show) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true)
                    }
                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false)
                    }
                }
            }
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayout.setOnRefreshListener({
                presenter.getTeams(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }
    //endregion
}