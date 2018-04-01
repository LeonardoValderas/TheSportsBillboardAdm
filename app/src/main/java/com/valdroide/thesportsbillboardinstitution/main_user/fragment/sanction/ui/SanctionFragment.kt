package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui

import android.os.Bundle
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.SanctionFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.adapters.SanctionFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import kotlinx.android.synthetic.main.fragment_sanction.*
import javax.inject.Inject

class SanctionFragment : BaseFragmentUser(), SanctionFragmentView {

    @Inject
    lateinit var presenter: SanctionFragmentPresenter
    @Inject
    lateinit var adapterSanction: SanctionFragmentAdapter
    var sanctionsList: MutableList<Sanction> = arrayListOf()
//    private var id_sub_menu: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initSwipeRefreshLayout()
        getSanctions(false)
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
        app.getSanctionFragmentComponent(this, this).inject(this)
        //presenter = getPresenterInj()
        //adapterPlayer = getAdapter()
    }

    private fun setAdapter(sactions: MutableList<Sanction>) {
        adapterSanction.setSanctions(sactions)
    }

    private fun verifySwipeRefresh(show: Boolean) {
        ViewComponentHelper.verifySwipeRefresh(conteiner, swipeRefreshLayout, show)
    }

    fun getSanctions(isUpdate: Boolean) {
        if (sanctionsList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            getSanctions(SharedHelper.getSubmenuId(activity))
        } else {
            setAdapter(sanctionsList)
        }
    }

    private fun getSanctions(id_submenu: Int) {
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            setError(getString(R.string.generic_error_response))
        } else
            presenter.getSanctions(activity, id_submenu)
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
            ViewComponentHelper.initRecyclerView(this, activity)
            adapter = adapterSanction
        }
    }

    override fun setSanctions(sactions: MutableList<Sanction>) {
        sanctionsList = sactions
        setAdapter(sactions)
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
