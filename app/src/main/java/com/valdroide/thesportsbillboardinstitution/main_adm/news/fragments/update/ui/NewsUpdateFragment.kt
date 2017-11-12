package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.news.activity.TabNewsActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.NewsUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di.NewsUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.NewsUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.frame_recycler_refresh.*

class NewsUpdateFragment : Fragment(), NewsUpdateFragmentView, OnItemClickListener {

    private lateinit var component: NewsUpdateFragmentComponent
    lateinit var presenter: NewsUpdateFragmentPresenter
    lateinit var adapterNews: NewsUpdateFragmentAdapter
    var Newss: MutableList<News> = arrayListOf()
    private var isRegister: Boolean = false
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.frame_recycler_refresh, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInjection()
        register()
        initRecyclerView()
        initSwipeRefreshLayout()
        presenter.getNews(activity)
    }

    private fun setupInjection() {
        val app = activity.application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(activity, javaClass.simpleName, null)
        component = app.getNewsUpdateFragmentComponent(this, this, this)
        presenter = getPresenterInj()
        adapterNews = getAdapter()
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

    fun showAlertDialog(title: String, msg: String, News: News) {
        val alertDilog = AlertDialog.Builder(activity).create()
        alertDilog.setTitle(title)
        alertDilog.setMessage(msg)

        alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialogInterface, i ->
            presenter.deleteNews(activity, News)
        })

        alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", { dialogInterface, j ->
            alertDilog.dismiss()
        })
        alertDilog.show()
    }

    open fun getPresenterInj(): NewsUpdateFragmentPresenter = component.getPresenter()


    open fun getAdapter(): NewsUpdateFragmentAdapter = component.getAdapter()

    override fun onClickUpdateNews(position: Int, news: News) {
        val i = Intent(activity, TabNewsActivity::class.java)
        i.putExtra("is_update", true)
        i.putExtra("id_news", news.ID_NEWS_KEY)
        startActivity(i)
    }

    override fun onClickDeleteNews(position: Int, News: News) {
        this.position = position
        showAlertDialog( getString(R.string.alert_title), getString(R.string.delete_simple_alerte_msg, "la noticia"), News)
    }

    override fun onClickActiveNews(position: Int, News: News) {
        presenter.activeUnActiveNews(activity, News)
    }

    override fun onClickUnActiveNews(position: Int, News: News) {
        presenter.activeUnActiveNews(activity, News)
    }

    override fun updateNewsSuccess() {
        adapterNews.notifyDataSetChanged()
        Utils.showSnackBar(conteiner, getString(R.string.update_success, "Noticia"))
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterNews
        }
    }

    override fun setAllNews(Newss: MutableList<News>) {
        this.Newss = Newss
        adapterNews.setNews(Newss)
    }

    override fun deleteNewsSuccess() {
        adapterNews.deleteNews(position)
        Utils.showSnackBar(conteiner, activity.getString(R.string.delete_success, "Noticia"))
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
                presenter.getNews(activity)
            })
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

    fun refreshAdapter() {
        presenter.getNews(activity)
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