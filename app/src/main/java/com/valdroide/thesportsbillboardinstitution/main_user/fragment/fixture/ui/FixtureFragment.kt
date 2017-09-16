package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di.FixtureFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import kotlinx.android.synthetic.main.fragment_fixture.*
import com.valdroide.thesportsbillboardinstitution.utils.Utils


open class FixtureFragment : Fragment(), FixtureFragmentView, OnItemClickListener {


    private lateinit var component: FixtureFragmentComponent
    lateinit var presenter: FixtureFragmentPresenter
    lateinit var adapterFixture: FixtureFragmentAdapter
    var fixtures: MutableList<Fixture> = arrayListOf()
    private var isRegister: Boolean = false
    private var isClick: Boolean = false
    private var id_sub_menu: Int = 0

    companion object Factory {
        lateinit var presenterTest: FixtureFragmentPresenter
        lateinit var adapterTest: FixtureFragmentAdapter
        lateinit var fixturesTest: MutableList<Fixture>
        var isTest: Boolean = false
        fun create(presenter: FixtureFragmentPresenter, adapter: FixtureFragmentAdapter, fixtures: MutableList<Fixture>): FixtureFragment {
            presenterTest = presenter
            adapterTest = adapter
            fixturesTest = fixtures
            isTest = true

            return com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        getFixtures(false)
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

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterFixture
        }
    }

    fun getFixtures(isClick: Boolean) {
        this.isClick = isClick
        if (isClick || (!isClick && fixtures.isEmpty())) {
            showSwipeRefreshLayout()
            id_sub_menu = Utils.getSubmenuId(activity)
            if (!fixtures.isEmpty())
                deleteLastItemButton()
            presenter.getFixtures(activity, id_sub_menu, fixtures.size)
        } else {
            if (fixtures.isNotEmpty())
                deleteLastItemButton()
            setAdapter(false)
        }
    }

    private fun deleteLastItemButton() {
        fixtures.removeAt(fixtures.size - 1)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        //app.getFixtureFragmentComponent(this, this, this)
        component = app.getFixtureFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterFixture = getAdapter()
    }

    override fun setFixture(fixtures: MutableList<Fixture>) {
        if (this.fixtures.isEmpty()) {
            if (isTest)
                fixturesTest.addAll(fixtures)
            else
                this.fixtures.addAll(fixtures)
        } else
            this.fixtures.addAll((this.fixtures.size), fixtures)
        setAdapter(isClick)
    }

    private fun setAdapter(isClick: Boolean) {
        if (isTest)
            adapterFixture.setFixtures(fixturesTest, isClick)
        else
            adapterFixture.setFixtures(this.fixtures, isClick)
    }

    open fun getPresenterInj(): FixtureFragmentPresenter {
        if (!isTest)
            return component.getPresenter()
        else
            return presenterTest
    }

    open fun getAdapter(): FixtureFragmentAdapter {
        if (!isTest)
            return component.getAdapter()
        else
            return adapterTest
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

    override fun onClickFixture(position: Int, fixture: Fixture) {
    }
    override fun onClickButtonAddMore() {
        getFixtures(true)
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
                fixtures.clear()
                getFixtures(true)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    //    override fun onPause() {
//        super.onPause()
//        unregister()
////        mAd.pause(this)
////        if (mAdView != null) {
////            mAdView.pause()
////        }
//    }
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
