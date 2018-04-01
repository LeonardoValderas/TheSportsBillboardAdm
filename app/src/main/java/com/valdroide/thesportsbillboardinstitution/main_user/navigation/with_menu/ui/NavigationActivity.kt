package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui.adapters.NavigationActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.NavigationActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.tab.TabActivity
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_navigation.*
import javax.inject.Inject

open class NavigationActivity : BaseActivity(), NavigationActivityView, GenericOnItemClick<MenuDrawer> {

    @Inject
    lateinit var presenter: NavigationActivityPresenter
    lateinit var adapterMenu: NavigationActivityAdapter
    private var position: Int = 0
    var menus: List<MenuDrawer> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initDrawerToggle()
        initRecyclerView()
        fabOnclick()
        presenter.getMenus(this)
    }

    private fun fabOnclick() {
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_navigation

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        app.getNavigationActivityMenuComponent(this, this).inject(this)
        adapterMenu = getAdapter()
    }
    //I dont realice inject because a have error
    private fun getAdapter(): NavigationActivityAdapter = NavigationActivityAdapter(this, this)

    override fun onClick(view: View, position: Int, t: MenuDrawer) {
        try {
            val menuJson = SharedHelper.convertJsonParceable(t)
            if(menuJson.isEmpty())
                showSnackBar(getString(R.string.generic_error_response))
            else {
                SharedHelper.setMenuJson(this, menuJson)
                startActivity(Intent(applicationContext, TabActivity::class.java))
            }
        } catch (e: Exception) {
            showSnackBar(e.message!!)
        }
    }

    private fun showSnackBar(msg: String) = ViewComponentHelper.showSnackBar(drawer_layout, msg)

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initRecyclerView() {
        with(rv_menu) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            adapter = adapterMenu
        }
    }

    private fun initDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    /*

    private fun addDrawerItems() {
        try {
            navList.setAdapter(mExpandableListAdapter)
            navList.setOnGroupExpandListener({ })

            navList.setOnGroupCollapseListener({
                //                    setClickMenu();
            })

            navList.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
                override fun onChildClick(parent: ExpandableListView, v: View?,
                                          groupPosition: Int, childPosition: Int, id: Long): Boolean {
//                    var v = v
//                    if (v == null) {
//                        v = mLayoutInflater.inflate(R.layout.list_sub_menu, null)
//                    }
                    //  imageViewItemList(v)
                    value = mExpandableListMenuStringSubMenu[mExpandableListMenu.get(groupPosition)]
                    if (value != null) {

                        try {
                            Utils.setSubmenuIdTitle(applicationContext, value!!.get(position).ID_SUBMENU_KEY, value!!.get(position).SUBMENU)
                            startActivity(Intent(applicationContext, TabActivity::class.java))
                        } catch (e: Exception) {
                            Utils.showSnackBar(drawer_layout, e.message!!)
                        }
                    } else
                        Utils.showSnackBar(drawer_layout, "Problemas para obtener la información solicitada.")
                    // textTool = value!!.get(childPosition).toString()
                    // position = childPosition
                    //  presenter.setUpdateSubCategory(NavigationActivity.this, value.get(childPosition));
                    // mNavigationManager.showFragmentAction(value.get(childPosition), false);
                    //setVisibilityAds(false);
                    // setClickMenu();
                    onBackPressed()

                    return false
                }
            })
            navList.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener {
                internal var previousItem = -1

                override fun onGroupExpand(groupPosition: Int) {
                    if (groupPosition != previousItem) {
                        //presenter.setUpdateCategory(this@NavigationActivity, mExpandableListAdapter!!.getGroup(groupPosition).toString())

                        if (navList == null) {
                            navList = findViewById<ExpandableListView>(R.id.navList)
                        }
                        navList.collapseGroup(previousItem) //null
                    }
                    previousItem = groupPosition
                }
            })

        } catch (e: Exception) {
            // setError(e.message)
            //  Utils.writelogFile(this, "catch error " + e.getMessage() + "(Navigation, Repository)");
        }

    }
*/
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setMenuDrawers(menus: MutableList<MenuDrawer>) {
        this.menus = menus
        adapterMenu.addAll(menus)
    }

    override fun setError(error: String) {
        ViewComponentHelper.showSnackBar(drawer_layout, error)
    }

    override fun hideProgressBar() {
        if (progressBar != null)
            if (progressBar.visibility == View.VISIBLE)
                progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        if (progressBar != null)
            if (progressBar.visibility == View.GONE)
                progressBar.visibility = View.VISIBLE
    }


    override fun onCreateOptionsMenu(menuI: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menuI)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
//        if (id == R.id.navigation_home) {
//            startActivity(Intent(this, AccountActivity::class.java))
//        } else
        super.onOptionsItemSelected(item)
        return true
    }
/*
    override fun onResume() {
        super.onResume()
        if (mExpandableListAdapter != null)
            if (menus.isNotEmpty() && submenus.isNotEmpty())
                setMenuAndSubMenu(menus, submenus)
            else
                getMenuSubmenu()
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }
*/
}
