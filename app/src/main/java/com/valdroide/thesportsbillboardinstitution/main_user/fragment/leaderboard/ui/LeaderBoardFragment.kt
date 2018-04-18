package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui

import android.os.Bundle
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.LeaderBoardFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.adapters.LeaderBoardFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import kotlinx.android.synthetic.main.fragment_leader_board.*
import javax.inject.Inject

class LeaderBoardFragment: BaseFragmentUser(), LeaderBoardFragmentView {

    companion object {
        fun newInstance(id_menu: Int, id_tournament: Int, error: String): LeaderBoardFragment {
            val args = Bundle()
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_MENU_FRAGMENT, id_menu)
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_TOURNAMENT_FRAGMENT, id_tournament)
            args.putString(ConstantHelper.USER_FRAGMENT.ERROR_FRAGMENT, error)

            val fragment = LeaderBoardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: LeaderBoardFragmentPresenter
    @Inject
    lateinit var adapterLeader: LeaderBoardFragmentAdapter

    var ledearBoards: MutableList<LeaderBoard> = arrayListOf()
    private var isUpdate: Boolean = false

   /* companion object Factory {
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
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initGridView()
        initSwipeRefreshLayout()
        getLeaderBoards(false)
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getLeaderBoardFragmentComponent(this, this).inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_leader_board

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    private fun getLeaderBoards(isUpdate: Boolean) {
        if ((!isUpdate && ledearBoards.isEmpty()) || isUpdate) {
            if (!isUpdate)
                showSwipeRefreshLayout()
            getLeaderBoards(SharedHelper.getSubmenuId(activity))
        } else
            adapterLeader.setLeaderBoarders(ledearBoards)
    }

    private fun getLeaderBoards(id_submenu: Int) {
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            setError(getString(R.string.generic_error_response))
        } else
            presenter.getLeaderBoards(activity, id_submenu)
    }

    override fun setLeaderBoards(lidearBoards: MutableList<LeaderBoard>) {
        this.ledearBoards = lidearBoards
        if (showMessageEmpty(lidearBoards.isEmpty()))
            return
        adapterLeader.setLeaderBoarders(lidearBoards)
    }

    private fun showMessageEmpty(isEmpty: Boolean): Boolean {
        if (isEmpty) {
            ll_title_conteiner.visibility = View.GONE
            gridLeaderBoard.visibility = View.GONE
            tv_without_data.visibility = View.VISIBLE
        } else {
            ll_title_conteiner.visibility = View.VISIBLE
            gridLeaderBoard.visibility = View.VISIBLE
            tv_without_data.visibility = View.GONE
        }
        return isEmpty
    }

    /* open fun getPresenterInj(): LeaderBoardFragmentPresenter {
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
 */
    override fun setError(error: String) {
        ViewComponentHelper.showSnackBar(conteinerLeader, error)
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
        ViewComponentHelper.verifySwipeRefresh(conteinerLeader, swipeRefreshLayoutLeader, show)
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
}
