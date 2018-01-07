package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.valdroide.thesportsbillboardinstitution.utils.*
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class PlayerUpdateFragment : Fragment(), PlayerUpdateFragmentView, GenericOnItemClick<Player>, OnMenuItemClickListener {

    //region INIT VARIABLE
    private lateinit var component: PlayerUpdateFragmentComponent
    lateinit var presenter: PlayerUpdateFragmentPresenter
    lateinit var adapterPlayer: PlayerUpdateFragmentAdapter
    var Players: MutableList<Player> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0
    private var player = Player()
    //endregion

    //region LIFECYCLE
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
    //endregion

    //region INJECTION
    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getPlayerUpdateFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterPlayer = getAdapter()
    }

    open fun getPresenterInj(): PlayerUpdateFragmentPresenter = component.getPresenter()
    open fun getAdapter(): PlayerUpdateFragmentAdapter = PlayerUpdateFragmentAdapter(activity, this)
    //endregion

    //region PRESENTER
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

    fun refreshAdapter() {
        presenter.getPlayers(activity)
    }
    //endregion

    //region ALERT DIALOG
    private fun showAlertDialog(titleText: String, msg: String, player: Player) {
        alert(msg) {
            title = titleText
            yesButton {
                presenter.deletePlayer(activity, player)
            }
            noButton{}
        }.show()
    }
    //endregion

    //region GETEXTRA
    private fun onClickUpdate(player: Player) {
        val i = Intent(activity, TabPlayerActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_player", player.ID_PLAYER_KEY)
        startActivity(i)
    }
    //endregion

    //region ONCLICK INTERFACES
    override fun onClick(type: Int) {
        when (type) {
            1 -> onClickUpdate(player)
            2 -> {
                player.IS_ACTIVE = if(player.IS_ACTIVE == 0) 1 else 0
                presenter.activeUnActivePlayer(activity, player)
            }
            3 -> showAlertDialog(getString(R.string.alert_title), getString(R.string.alert_msg_player), player)
        }
    }

    override fun onClick(view: View, position: Int, t: Player) {
        this.position = position
        player = t
        CustomDialogMenu.Builder(activity)
                .setOnClick(this)
                .withTitles(resources.getStringArray(R.array.menu_title_3))
                .show()
    }
    //endregion

    //region VIEW
    override fun updatePlayerSuccess() {
        adapterPlayer.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Jugador", "o"))
    }

    override fun setAllPlayers(Players: MutableList<Player>) {
        this.Players = Players
        adapterPlayer.addAll(Players)
    }

    override fun deletePlayerSuccess() {
        adapterPlayer.delete(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Jugador", "o"))
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
    //endregion

    //region RECYCLER
    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterPlayer
        }
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
                presenter.getPlayers(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }
    //endregion
}