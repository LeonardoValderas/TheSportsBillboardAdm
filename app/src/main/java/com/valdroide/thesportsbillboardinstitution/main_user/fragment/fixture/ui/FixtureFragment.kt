package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import kotlinx.android.synthetic.main.fragment_fixture.*
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import javax.inject.Inject

open class FixtureFragment : BaseFragmentUser(), FixtureFragmentView, OnItemClickListener {

    companion object {
       fun newInstance(id_menu: Int, id_tournament: Int, error: String): FixtureFragment {
            val args = Bundle()
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_MENU_FRAGMENT, id_menu)
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_TOURNAMENT_FRAGMENT, id_tournament)
            args.putString(ConstantHelper.USER_FRAGMENT.ERROR_FRAGMENT, error)

            val fragment = FixtureFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: FixtureFragmentPresenter
    @Inject
    lateinit var adapterFixture: FixtureFragmentAdapter
    var fixtures: MutableList<Fixture> = arrayListOf()
    private var isClick: Boolean = false
    //private var id_sub_menu: Int = 0
    inline fun <reified T> Gson.fromJson(json: String): T =
            this.fromJson<T>(json, T::class.java)

    /*companion object Factory {
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
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initSwipeRefreshLayout()
        getFixtures(false)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_fixture

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            ViewComponentHelper.initRecyclerView(recyclerView, activity)
            adapter = adapterFixture
        }
    }

    fun getFixtures(isClick: Boolean) {
        this.isClick = isClick // is click do button to add more items

        if (isClick || (!isClick && fixtures.isEmpty())) {
            showSwipeRefreshLayout()
            getFixtures(getMenuEntity())
        } else {
            if (fixtures.isNotEmpty())
                deleteLastItemButton()

            updateAdapter(false)
        }
    }

    private fun getFixtures(id_submenu: Int) {
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            setError(getString(R.string.generic_error_response))
        } else {
            if (fixtures.isNotEmpty())
                deleteLastItemButton()
            presenter.getFixtures(activity, id_submenu, fixtures.size)
        }
    }

    private fun getMenuEntity(): Int {
        return SharedHelper.getSubmenuId(activity)

        /* val menuJson = SharedHelper.getMenuJson(activity)
         if (menuJson.isEmpty())
             return null
         else {
             return Gson().fromJson<MenuDrawer>(menuJson)
         }
         */
    }

    private fun deleteLastItemButton() {
        fixtures.removeAt(fixtures.size - 1)
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getFixtureFragmentComponent(this, this, this).inject(this)
    }

    override fun setFixture(fixtures: MutableList<Fixture>) {
        if (showMenssageEmpty(fixtures.isEmpty() && this.fixtures.isEmpty()))
            return

        if (this.fixtures.isEmpty()) {
       //     if (isTest)
         //       fixturesTest.addAll(fixtures)
           // else
                this.fixtures.addAll(fixtures)
        } else
            this.fixtures.addAll((this.fixtures.size), fixtures)

        updateAdapter(isClick)
    }

    private fun showMenssageEmpty(isEmpty: Boolean): Boolean {
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            tv_without_data.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tv_without_data.visibility = View.GONE
        }

        return isEmpty
    }

    private fun updateAdapter(isClick: Boolean) {
      //  if (isTest)
        //    adapterFixture.setFixtures(fixturesTest, isClick)
       // else
            adapterFixture.setFixtures(this.fixtures, isClick)
    }

    /* see to test
    open fun getPresenterInj(): FixtureFragmentPresenter {
        if (!isTest)
            return component.getPresenter()
        else
            return presenterTest
    }*/
    /* see to test
    open fun getAdapter(): FixtureFragmentAdapter {
        if (!isTest)
            return component.getAdapter()
        else
            return adapterTest
    }
   */
    override fun setError(error: String) {
        ViewComponentHelper.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }

    override fun onClickFixture(position: Int, fixture: Fixture) {
        //without event
    }

    override fun onClickButtonAddMore() {
        getFixtures(true)
    }

    private fun verifySwipeRefresh(show: Boolean) {
        ViewComponentHelper.verifySwipeRefresh(conteiner, swipeRefreshLayout, show)
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
}
