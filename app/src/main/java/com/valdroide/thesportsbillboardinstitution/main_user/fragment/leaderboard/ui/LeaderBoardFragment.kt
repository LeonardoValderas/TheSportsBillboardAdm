package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.LeaderBoardFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di.LeaderBoardFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.adapters.LeaderBoardFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_leader_board.*


class LeaderBoardFragment : Fragment(), LeaderBoardFragmentView {


    private lateinit var component: LeaderBoardFragmentComponent
    lateinit var presenter: LeaderBoardFragmentPresenter
    lateinit var adapterLeader: LeaderBoardFragmentAdapter
    var ledearBoards: MutableList<LeaderBoard> = arrayListOf()
    private var isRegister: Boolean = false
    private var isUpdate: Boolean = false

    companion object Factory {
        lateinit var presenterTest: LeaderBoardFragmentPresenter
        lateinit var adapterTest: LeaderBoardFragmentAdapter
        lateinit var leaderTest: MutableList<LeaderBoard>
        var isTest: Boolean = false
        fun create(presenter: LeaderBoardFragmentPresenter, adapter: LeaderBoardFragmentAdapter, leaderBoards: MutableList<LeaderBoard>): LeaderBoardFragment {
            presenterTest = presenter
            adapterTest = adapter
            leaderTest = leaderBoards
            isTest = true

            return LeaderBoardFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_leader_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initGridView()
        initSwipeRefreshLayout()
        getLeaderBoards(false)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getLeaderBoardFragmentComponent(this, this)

        component = app.getLeaderBoardFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterLeader = getAdapter()
    }

    private fun getLeaderBoards(isUpdate: Boolean) {
        if ((!isUpdate && ledearBoards.isEmpty()) || isUpdate) {
            if (!isUpdate)
                showSwipeRefreshLayout()
            val id_menu = Utils.getSubmenuId(activity)
            presenter.getLeaderBoards(activity, id_menu)
        } else
            adapterLeader.setLeaderBoarders(ledearBoards)

    }

    override fun setLeaderBoards(lidearBoards: MutableList<LeaderBoard>) {
        this.ledearBoards = lidearBoards
        adapterLeader.setLeaderBoarders(lidearBoards)
    }

    open fun getPresenterInj(): LeaderBoardFragmentPresenter {
        if (!isTest)
            return component.getPresenter()
        else
            return presenterTest
    }

    open fun getAdapter(): LeaderBoardFragmentAdapter {
        if (!isTest)
            return component.getAdapter()
        else
            return adapterTest
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteinerLeader, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }

    private fun initGridView() {
        gridLeaderBoard.adapter = adapterLeader
    }


    private fun verifySwipeRefresh(show: Boolean) {
        try {
            if (swipeRefreshLayoutLeader != null) {
                if (show) {
                    if (!swipeRefreshLayoutLeader.isRefreshing()) {
                        swipeRefreshLayoutLeader.setRefreshing(true)
                    }
                } else {
                    if (swipeRefreshLayoutLeader.isRefreshing()) {
                        swipeRefreshLayoutLeader.setRefreshing(false)
                    }
                }
            }
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }


    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayoutLeader.setOnRefreshListener({
                getLeaderBoards(true)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
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
