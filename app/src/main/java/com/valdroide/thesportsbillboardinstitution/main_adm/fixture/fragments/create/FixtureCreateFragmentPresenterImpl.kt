package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.events.FixtureCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import org.greenrobot.eventbus.Subscribe

class FixtureCreateFragmentPresenterImpl(var view: FixtureCreateFragmentView, val event: EventBus, val interactor: FixtureCreateFragmentInteractor) : FixtureCreateFragmentPresenter {

    override fun getViewPresenter(): FixtureCreateFragmentView = view

    override fun setViewPresenter(view: FixtureCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getFixture(context: Context, id_fixture: Int) {
        interactor.getFixture(context, id_fixture)
    }

    override fun saveFixture(context: Context, fixture: Fixture) {
        showProgressAndSetVisibility()
        interactor.saveFixture(context, fixture)
    }

    override fun updateFixture(context: Context, fixture: Fixture) {
        showProgressAndSetVisibility()
        interactor.updateFixture(context, fixture)
    }

    override fun getSpinnerData(context: Context) {
        showProgressAndSetVisibility()
        interactor.getSpinnerData(context)
    }

    private fun showProgressAndSetVisibility(){
        view.showProgressDialog()
        view.setVisibilityViews(View.INVISIBLE)
    }

    @Subscribe
    override fun onEventMainThread(event: FixtureCreateFragmentEvent) {
        when (event.type) {
            FixtureCreateFragmentEvent.GETFIXTURE-> {
                view.setFixtureUpdate(event.fixture!!)
                view.fillViewUpdate()
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
            }
            FixtureCreateFragmentEvent.SAVEFIXTURE-> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.saveSuccess()
                view.cleanViews()
            }
            FixtureCreateFragmentEvent.UPDATEFIXTURE-> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.editSuccess()
                view.cleanViews()
            }
            FixtureCreateFragmentEvent.GETSPINNERSDATA-> {
                view.setSpinnersData(event.subMenuDrawers!!, event.fieldMatchs!!, event.timeMatchs!!, event.tournaments!!, event.teams!!)
            }
            FixtureCreateFragmentEvent.ERROR -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.setError(event.error!!)
            }
        }
    }
}