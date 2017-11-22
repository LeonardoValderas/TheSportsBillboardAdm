package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.TeamUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_fixture.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.utils.CustomDialogMenu
import com.valdroide.thesportsbillboardinstitution.utils.OnMenuItemClickListener

class TeamUpdateFragment : Fragment(), TeamUpdateFragmentView, GenericOnItemClick<Team>, OnMenuItemClickListener {

    private lateinit var component: TeamUpdateFragmentComponent
    lateinit var presenter: TeamUpdateFragmentPresenter
    lateinit var adapterTeam: TeamUpdateFragmentAdapter
    var teams: MutableList<Team> = arrayListOf()
    var team = Team()
    private var isRegister: Boolean = false
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_fixture, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getTeams(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getTeamUpdateFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterTeam = getAdapter()
  //      adapterTeam.setClickListener(this)
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

    private fun showAlertDialog(titleText: String, msg: String, team: Team) {
        alert(msg) {
            title = titleText
            yesButton {
                presenter.deleteTeam(activity, team)
            }
            noButton {}
        }.show()
    }

    open fun getPresenterInj(): TeamUpdateFragmentPresenter = component.getPresenter()

    //I dont realice inject because a have error
    open fun getAdapter(): TeamUpdateFragmentAdapter = TeamUpdateFragmentAdapter(activity, this)
    // = component.getAdapter()

   private fun onClickUpdate(team: Team) {
        val i = Intent(activity, TabTeamActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_team", team.ID_TEAM_KEY)
        startActivity(i)
    }

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
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Equipo", "o"))
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterTeam
        }
    }

    override fun setAllTeams(teams: MutableList<Team>) {
        this.teams = teams
        adapterTeam.addAll(teams)
    }

    override fun deleteTeamSuccess() {
        adapterTeam.delete(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Equipo", "o"))
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }

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

    fun refreshAdapter() {
        presenter.getTeams(activity)
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