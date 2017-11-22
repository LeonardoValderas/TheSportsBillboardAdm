package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.activity.TabFixtureActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.FixtureUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di.FixtureUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter.FixtureUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.dialog.CustomDialog
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch
import com.valdroide.thesportsbillboardinstitution.utils.*
import kotlinx.android.synthetic.main.frame_recycler_refresh.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert

class FixtureUpdateFragment : Fragment(), FixtureUpdateFragmentView, GenericOnItemClick<Fixture>, OnMenuItemClickListener, OnItemClickListenerFixture {

    private lateinit var component: FixtureUpdateFragmentComponent
    lateinit var presenter: FixtureUpdateFragmentPresenter
    lateinit var adapterFixture: FixtureUpdateFragmentAdapter
    var fixtures: MutableList<Fixture> = arrayListOf()
    var times: MutableList<TimeMatch> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0
    private var fixture = Fixture()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getFixture(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getFixtureUpdateFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterFixture = getAdapter()
        adapterFixture.setClickListener(this)
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

    private fun showAlertDialog(titleText: String, msg: String, fixture: Fixture) {
        alert(msg) {
            title = titleText
            yesButton {
                presenter.deleteFixture(activity, fixture)
            }
            noButton {}
        }.show()
    }

    open fun getPresenterInj(): FixtureUpdateFragmentPresenter = component.getPresenter()

    open fun getAdapter(): FixtureUpdateFragmentAdapter = component.getAdapter()

    override fun onClick(view: View, position: Int, t: Fixture) {
        this.position = position
        fixture = t
        CustomDialogMenu.Builder(activity).setOnClick(this).withTitles(resources.getStringArray(R.array.menu_title_3_fixture)).show()
    }

    override fun onClick(type: Int) {
        when (type) {
            1 -> showAlertResult(fixture, activity)
            2 -> onClickUpdate(fixture)
            3 -> showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_simple_alerte_msg, "el fixture"), fixture)
        }
    }

    override fun onClickSaveResult(any: Any) {
        presenter.setResultFixture(activity, any as Fixture)
    }

    private fun showAlertResult(fixture: Fixture, activity: Activity) {
        CustomDialog.Builder(activity).setOnClick(this).withFixture(fixture).withTimes(times).show()
    }

    private fun onClickUpdate(fixture: Fixture) {
        val i = Intent(activity, TabFixtureActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_fixture", fixture.ID_FIXTURE_KEY)
        startActivity(i)
    }

    override fun updateFixtureSuccess(fixture: Fixture) {
        adapterFixture.update(position, fixture)
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Fixture"))
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterFixture
        }
    }

    override fun setAllFixture(fixtures: MutableList<Fixture>, times: MutableList<TimeMatch>) {
        this.fixtures = fixtures
        this.times = times
        adapterFixture.addAll(fixtures)
    }

    override fun deleteFixtureSuccess() {
        adapterFixture.delete(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Sanción"))
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

    private fun verifySwipeRefresh(show: Boolean) {
        try {
            if (swipeRefreshLayout != null) {
                if (show) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true)
                        recyclerView.visibility = View.INVISIBLE
                    }
                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false)
                        recyclerView.visibility = View.VISIBLE
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
                presenter.getFixture(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    fun refreshAdapter() {
        presenter.getFixture(activity)
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