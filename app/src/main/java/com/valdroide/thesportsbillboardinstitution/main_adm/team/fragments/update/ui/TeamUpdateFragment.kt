package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
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
import butterknife.internal.ListenerClass.NONE
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnCreateContextMenuListener
import android.widget.AdapterView
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.dialog.CustomDialog
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.utils.CustomDialogMenu
import com.valdroide.thesportsbillboardinstitution.utils.OnMenuItemClickListener
import kotlinx.android.synthetic.main.gridview_item_leaderboard.*


class TeamUpdateFragment : Fragment(), TeamUpdateFragmentView, GenericOnItemClick<Team>, OnMenuItemClickListener {

    private lateinit var component: TeamUpdateFragmentComponent
    lateinit var presenter: TeamUpdateFragmentPresenter
    lateinit var adapterTeam: TeamUpdateFragmentAdapter
    var teams: MutableList<Team> = arrayListOf()
    var team: Team = Team()
    private var isRegister: Boolean = false
    private var position: Int = 0
    lateinit var onClickItem: GenericOnItemClick<Team>
    private var alert: CustomDialogMenu? = null

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
        component = app.getTeamUpdateFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterTeam = getAdapter()
        adapterTeam.setClickListener(this)
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


    //open fun getAdapter(): TeamUpdateFragmentAdapter = component.getAdapter()
    open fun getAdapter(): TeamUpdateFragmentAdapter = component.getAdapter()


    private fun onClickUpdate(position: Int, any: Any) {
        val i = Intent(activity, TabTeamActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_team", (any as Team).ID_TEAM_KEY)
        startActivity(i)
    }

//    override fun onClickDelete(position: Int, any: Any) {
//        this.position = position
//        showAlertDialog(getString(R.string.alert_title), getString(R.string.alert_msg_team), any as Team)
//    }
//
//    override fun onClickSave(position: Int, any: Any) {}
//
//    override fun onClickActive(position: Int, any: Any) {
//        presenter.activeUnActiveTeam(activity, any as Team)
//    }
//
//    override fun onClickUnActive(position: Int, any: Any) {
//        presenter.activeUnActiveTeam(activity, any as Team)
//    }

    override fun onClick(view: View, position: Int, t: Team) {
        this.position = position
        team = t
        alert = CustomDialogMenu.Builder(activity).setOnClick(this).withTitles(resources.getStringArray(R.array.menu_title_3)).getDialog()
        alert!!.show()
    }

    override fun onClick(type: Int) {
        when (type) {
            1 -> onClickUpdate(position, team)
            2 -> {
                team.IS_ACTIVE = if(team.IS_ACTIVE == 0) 1 else 0
                presenter.activeUnActiveTeam(activity, team)
            }
            3 -> showAlertDialog(getString(R.string.alert_title), getString(R.string.alert_msg_team), team)
        }
    }

    override fun updateTeamSuccess() {
        adapterTeam.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, getString(R.string.team_update_success))
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
        Utils.showSnackBar(conteiner, activity.getString(R.string.team_delete_success))
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