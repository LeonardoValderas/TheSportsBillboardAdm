package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.player.activity.TabPlayerActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.PlayerUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di.PlayerUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter.PlayerUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class PlayerUpdateFragment : Fragment(), PlayerUpdateFragmentView, GenericOnItemClickListener.withActive {

    private lateinit var component: PlayerUpdateFragmentComponent
    lateinit var presenter: PlayerUpdateFragmentPresenter
    lateinit var adapterLogin: PlayerUpdateFragmentAdapter
    var Players: MutableList<Player> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getPlayers(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getPlayerUpdateFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterLogin = getAdapter()
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

    private fun showAlertDialog(titleText: String, msg: String, player: Player) {
        alert(msg) {
            title = titleText
            yesButton {
                presenter.deletePlayer(activity, player)
            }
            noButton{}
        }.show()
    }

    open fun getPresenterInj(): PlayerUpdateFragmentPresenter = component.getPresenter()


    open fun getAdapter(): PlayerUpdateFragmentAdapter = component.getAdapter()

    override fun onClickUpdate(position: Int, any: Any) {
        val i = Intent(activity, TabPlayerActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_player", (any as Player).ID_PLAYER_KEY)
        startActivity(i)
    }

    override fun onClickDelete(position: Int, any: Any) {
        this.position = position
        showAlertDialog( getString(R.string.alert_title), getString(R.string.alert_msg_player), any as Player)
    }

    override fun onClickActive(position: Int, any: Any) {
        presenter.activeUnActivePlayer(activity, any as Player)
    }

    override fun onClickUnActive(position: Int, any: Any) {
        presenter.activeUnActivePlayer(activity, any as Player)
    }

    override fun updatePlayerSuccess() {
        adapterLogin.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Jugador", "o"))
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterLogin
        }
    }

    override fun setAllPlayers(Players: MutableList<Player>) {
        this.Players = Players
        adapterLogin.setPlayers(Players)
    }

    override fun deletePlayerSuccess() {
        adapterLogin.deletePlayer(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Jugador", "o"))
    }

    override fun onClickSave(position: Int, any: Any) {}


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
                presenter.getPlayers(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    fun refreshAdapter() {
        presenter.getPlayers(activity)
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