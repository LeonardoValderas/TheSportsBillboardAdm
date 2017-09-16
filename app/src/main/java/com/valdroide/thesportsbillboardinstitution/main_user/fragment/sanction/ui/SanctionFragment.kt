package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.SanctionFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di.SanctionFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.adapters.SanctionFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fragment_sanction.*

class SanctionFragment : Fragment(), SanctionFragmentView {


    private lateinit var component: SanctionFragmentComponent
    lateinit var presenter: SanctionFragmentPresenter
    lateinit var adapterPlayer: SanctionFragmentAdapter
    var sanctionsList: MutableList<Sanction> = arrayListOf()
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
        getSanctions(false)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getSanctionFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterPlayer = getAdapter()
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

    private fun setAdapter(sactions: MutableList<Sanction>) {
        adapterPlayer.setSanctions(sactions)
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

    fun getSanctions(isUpdate: Boolean) {
        if (sanctionsList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            id_sub_menu = Utils.getSubmenuId(activity)
            presenter.getSanctions(activity, id_sub_menu)
        } else {
            setAdapter(sanctionsList)
        }
    }

    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayout.setOnRefreshListener({
                getSanctions(true)
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

    open fun getPresenterInj(): SanctionFragmentPresenter {
        //  if (!isTest)
        return component.getPresenter()
//        else
//            return presenterTest
    }

    open fun getAdapter(): SanctionFragmentAdapter {
        //if (!isTest)
        return component.getAdapter()
//        else
//            return adapterTest
    }

    override fun setSanctions(sactions: MutableList<Sanction>) {
        sanctionsList = sactions
        setAdapter(sactions)
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
