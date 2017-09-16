package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.PlayerFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di.PlayerFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.adapters.PlayerFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment(), PlayerFragmentView {

    private lateinit var component: PlayerFragmentComponent
    lateinit var presenter: PlayerFragmentPresenter
    lateinit var adapterPlayer: PlayerFragmentAdapter
    var playerList: MutableList<Player> = arrayListOf()
    private var isRegister: Boolean = false
    private var id_sub_menu: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        getPlayer(false)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        //app.getFixtureFragmentComponent(this, this, this)
        component = app.getPlayerFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterPlayer = getAdapter()
    }

    fun getPlayer(isUpdate: Boolean) {
        if (playerList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            id_sub_menu = Utils.getSubmenuId(activity)
            presenter.getPlayers(activity, id_sub_menu)
        } else {
            setAdapter(playerList)
        }
    }

    private fun setAdapter(Player: MutableList<Player>) {
        adapterPlayer.setPlayers(Player)
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
                getPlayer(true)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterPlayer
        }
    }

    open fun getPresenterInj(): PlayerFragmentPresenter {
        //  if (!isTest)
        return component.getPresenter()
//        else
//            return presenterTest
    }

    open fun getAdapter(): PlayerFragmentAdapter {
        //if (!isTest)
        return component.getAdapter()
//        else
//            return adapterTest
    }

    override fun setPlayers(players: MutableList<Player>) {
        playerList = players
        setAdapter(players)
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