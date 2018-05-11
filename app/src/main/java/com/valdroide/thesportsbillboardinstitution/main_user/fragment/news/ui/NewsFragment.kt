package com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui

import android.os.Bundle
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.adapters.NewsFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.NewsFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseFragmentUser
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.ImageHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import kotlinx.android.synthetic.main.content_without_data.*
import kotlinx.android.synthetic.main.frame_recycler_refresh.*
import javax.inject.Inject

class NewsFragment : BaseFragmentUser(), NewsFragmentView {

    companion object {
        fun newInstance(id_menu: Int): NewsFragment {
            val args = Bundle()
            args.putInt(ConstantHelper.USER_FRAGMENT.ID_MENU_FRAGMENT, id_menu)

            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: NewsFragmentPresenter
    @Inject
    lateinit var adapterNews: NewsFragmentAdapter

    var newsList: MutableList<News> = arrayListOf()
    var id_menu = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(arguments != null)
            updateVariablesArgument(arguments.getInt(ConstantHelper.USER_FRAGMENT.ID_MENU_FRAGMENT))

        initRecyclerView()
        initSwipeRefreshLayout()
        getNews(false)
    }

    override fun getLayoutResourceId(): Int = R.layout.frame_recycler_refresh

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    fun updateVariablesArgument(id_menu: Int){
        this.id_menu = id_menu

    }
    override fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        app.getNewsFragmentComponent(this, this).inject(this)
    }

    fun getNews(isUpdate: Boolean) {
        /*
        when refreshLayout is call the variable isUpdate is true
        */
        if (newsList.isEmpty() || isUpdate) {
            showSwipeRefreshLayout()
           // getNews(SharedHelper.getSubmenuId(activity))
            getNews(id_menu) //analisis if more practic create new instance and pass id or save it no shared preference
        } else
            setAdapter(newsList)
    }

    private fun getNews(id_submenu: Int) {
        if (id_submenu <= 0) {
            hideSwipeRefreshLayout()
            showInformationLayout(true)
           // setError(getString(R.string.generic_error_response))
        } else
            presenter.getNews(activity, id_submenu)
    }

    private fun setAdapter(news: MutableList<News>) {
        adapterNews.addAll(news)
    }

    private fun showInformationLayout(isErro: Boolean){
        recyclerVisibility(false)

        /*if(isErro) {
            ImageHelper.setPicasso(activity, "", R.mipmap.ic_error_data, iv_empty_error_data)
            tv_empty_error_data.text = getString(R.string.generic_error_response)
        }else {
            ImageHelper.setPicasso(activity, "", R.mipmap.ic_empty_data, iv_empty_error_data)
        }*/
    }

    private fun recyclerVisibility(isRecycler: Boolean){
        recyclerView.visibility = if(isRecycler) View.VISIBLE else View.INVISIBLE
   //     without_data.visibility = if(isRecycler) View.INVISIBLE else View.VISIBLE
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

    private fun initRecyclerView() {
        with(recyclerView) {
            ViewComponentHelper.initRecyclerView(this, activity)
            adapter = adapterNews
        }
    }

    override fun setNews(news: MutableList<News>) {
        newsList = news
        //verify if empty and show layout info
        if(newsList.isEmpty())
            showInformationLayout(false)
        else
            setAdapter(news)
    }

    override fun setError(error: String) {
         showInformationLayout(true)
        //ViewComponentHelper.showSnackBar(conteiner, error)
    }

    override fun hideSwipeRefreshLayout() {
        verifySwipeRefresh(false)
    }

    override fun showSwipeRefreshLayout() {
        verifySwipeRefresh(true)
    }
}
