package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters.NewsFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import kotlinx.android.synthetic.main.fragment_news.*
import com.ramotion.foldingcell.FoldingCell
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.NewsFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di.NewsFragmentComponent
import com.valdroide.thesportsbillboardinstitution.utils.Utils


class NewsFragment : Fragment(), NewsFragmentView {

    private lateinit var component: NewsFragmentComponent
    lateinit var presenter: NewsFragmentPresenter
    lateinit var adapterNews: NewsFragmentAdapter
    var newsList: MutableList<News> = arrayListOf()
    private var isRegister: Boolean = false
    private var id_sub_menu: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_news, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initListViewAdapter()
        initSwipeRefreshLayout()
        getNews(false)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        //  app.getFixtureFragmentComponent(this, this, this)
        component = app.getNewsFragmentComponent(this, this)
        presenter = getPresenterInj()
        adapterNews = getAdapter()
    }

    fun getNews(isUpdate: Boolean) {
        if (newsList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
            id_sub_menu = Utils.getSubmenuId(activity)
            presenter.getNews(activity, id_sub_menu)
        } else {
            setAdapter(newsList)
        }
    }

    private fun setAdapter(news: MutableList<News>) {
        adapterNews.setNews(news)
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

    open fun getPresenterInj(): NewsFragmentPresenter {
        //  if (!isTest)
        return component.getPresenter()
//        else
//            return presenterTest
    }

    open fun getAdapter(): NewsFragmentAdapter {
        //if (!isTest)
        return component.getAdapter()
//        else
//            return adapterTest
    }

    override fun setNews(news: MutableList<News>) {
        newsList = news
        setAdapter(news)
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
