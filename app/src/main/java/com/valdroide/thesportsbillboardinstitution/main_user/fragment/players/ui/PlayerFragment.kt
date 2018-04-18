package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui

import android.os.Bundle
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.PlayerFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.adapters.PlayerFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import kotlinx.android.synthetic.main.frame_recycler_refresh.*
import javax.inject.Inject

class PlayerFragment : BaseFragmentUser(), PlayerFragmentView {

    companion object {
        fun newInstance(id_menu: Int, error: String): PlayerFragment {
            val args = Bundle()
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_MENU_FRAGMENT, id_menu)
            args.putString(ConstantHelper.USER_FRAGMENT.ERROR_FRAGMENT, error)

            val fragment = PlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: PlayerFragmentPresenter
    @Inject
    lateinit var adapterPlayer: PlayerFragmentAdapter
    var playerList: MutableList<Player> = arrayListOf()
    private var id_sub_menu: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initSwipeRefreshLayout()
        getPlayer(false)
    }

    override fun getLayoutResourceId(): Int = R.layout.frame_recycler_refresh

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getPlayerFragmentComponent(this, this).inject(this)
    }

    fun getPlayer(isUpdate: Boolean) {
        if (playerList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            getPlayer(SharedHelper.getSubmenuId(activity))
        } else
            setAdapter(playerList)
    }

    private fun getPlayer(id_submenu: Int) {
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            setError(getString(R.string.generic_error_response))
        } else
            presenter.getPlayers(activity, id_submenu)
    }

    private fun setAdapter(Player: MutableList<Player>) {
        if (showMessageEmpty(Player.isEmpty()))
            return
        adapterPlayer.setPlayers(Player)
    }

    private fun showMessageEmpty(isEmpty: Boolean): Boolean {
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            tv_without_data.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tv_without_data.visibility = View.GONE
        }
        return isEmpty
    }

    private fun verifySwipeRefresh(show: Boolean) {
        ViewComponentHelper.verifySwipeRefresh(conteiner, swipeRefreshLayout, show)
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
            ViewComponentHelper.initRecyclerView(this, activity)
            adapter = adapterPlayer
        }
    }

    override fun setPlayers(players: MutableList<Player>) {
        playerList = players
        setAdapter(players)
    }

    override fun setError(error: String) {
        ViewComponentHelper.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }
}