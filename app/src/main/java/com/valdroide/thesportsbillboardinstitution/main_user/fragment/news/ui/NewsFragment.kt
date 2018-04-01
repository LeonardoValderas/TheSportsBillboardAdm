package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui

import android.os.Bundle
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters.NewsFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import kotlinx.android.synthetic.main.fragment_news.*
import com.ramotion.foldingcell.FoldingCell
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.NewsFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import javax.inject.Inject

class NewsFragment : BaseFragmentUser(), NewsFragmentView {

    @Inject
    lateinit var presenter: NewsFragmentPresenter
    @Inject
    lateinit var adapterNews: NewsFragmentAdapter
    var newsList: MutableList<News> = arrayListOf()
 //   private var id_sub_menu: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListViewAdapter()
        initSwipeRefreshLayout()
        getNews(false)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_news

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getNewsFragmentComponent(this, this).inject(this)
    }

    fun getNews(isUpdate: Boolean) {
        if (newsList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            getNews(SharedHelper.getSubmenuId(activity))
        } else
            setAdapter(newsList)

    }

    private fun getNews(id_submenu: Int){
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            setError(getString(R.string.generic_error_response))
        } else
            presenter.getNews(activity, id_submenu)
    }

    private fun setAdapter(news: MutableList<News>) {
        adapterNews.setNews(news)
    }

    private fun verifySwipeRefresh(show: Boolean) {
        ViewComponentHelper.verifySwipeRefresh(conteiner, swipeRefreshLayout, show)
    }

    private fun initSwipeRefreshLayout() {
        try {
            swipeRefreshLayout.setOnRefreshListener({
                getNews(true)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    private fun initListViewAdapter() {
        with(mainListView) {
            adapter = adapterNews
            setOnItemClickListener({ adapterView, view, pos, l ->
                // toggle clicked cell state
                (view as FoldingCell).toggle(false)
                // register in adapter that state for selected cell is toggled
                adapterNews.registerToggle(pos)
            })
        }
    }

    override fun setNews(news: MutableList<News>) {
        newsList = news
        setAdapter(news)
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
