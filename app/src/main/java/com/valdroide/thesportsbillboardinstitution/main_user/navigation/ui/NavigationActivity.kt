package com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import com.valdroide.thesportsbillboardinstitution.main_user.tab.TabActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import android.widget.ExpandableListView
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.NavigationActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.di.NavigationActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.adapters.CustomExpandableListAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.adapters.ExpandableListDataSource
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_navigation.*
import java.util.*

open class NavigationActivity : AppCompatActivity(), NavigationActivityView {

    private lateinit var component: NavigationActivityComponent
    lateinit var mExpandableListAdapter: CustomExpandableListAdapter
    lateinit var mExpandableListMenu: List<String>
    lateinit var mExpandableListMenuStringSubMenu: Map<String, List<SubMenuDrawer>>
    lateinit var presenter: NavigationActivityPresenter
    //   lateinit var mLayoutInflater: LayoutInflater
    lateinit var navList: ExpandableListView

    private var value: List<SubMenuDrawer>? = null
    private var textTool = ""
    private var position: Int = 0
    private var isRegister: Boolean = false
    var menus: List<MenuDrawer> = arrayListOf()
    var submenus: List<SubMenuDrawer> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setContentView(R.layout.activity_navigation)
        register()
        getMenuSubmenu()
        navList = findViewById(R.id.navList) as ExpandableListView
        setSupportActionBar(toolbar)
        initDrawerToggle()
        initHeaderNav()
        initAdapterDrawer()
        addDrawerItems()
        Utils.setPicasso(this, "", R.drawable.adeful, imageViewLogo)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AccountActivity::class.java))
        }
        //       nav_view.setNavigationItemSelectedListener(this)
    }

    private fun getMenuSubmenu() {
        if (menus.isEmpty() && submenus.isEmpty()) {
            showProgressBar()
            presenter.getMenusAndSubMenus(this)
        }
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getNavigationActivityComponent(this, this)
        presenter = getPresenterComponent()
        mExpandableListMenu = getExpandableListTitle()
    }

    fun getPresenterComponent(): NavigationActivityPresenter =
            component.getPresenter()

    fun getExpandableListTitle(): List<String> =
            component.getListString()

    fun initAdapterDrawer() {
        mExpandableListMenuStringSubMenu = TreeMap()
        mExpandableListAdapter = CustomExpandableListAdapter(this, mExpandableListMenu, mExpandableListMenuStringSubMenu)
    }

    fun initDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun initHeaderNav() {
        try {
            if (navList != null) {
                val inflater = layoutInflater
                val listHeaderView = inflater.inflate(R.layout.nav_header_navigation, null, false)
                navList.addHeaderView(listHeaderView)
            }
        } catch (e: Exception) {
            setError(e.message!!)
        }
    }

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
                            navList = findViewById(R.id.navList) as ExpandableListView
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

    override fun onBackPressed() {
        //       val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setMenuAndSubMenu(menus: List<MenuDrawer>, submenus: List<SubMenuDrawer>) {
        this.menus = menus
        this.submenus = submenus
        mExpandableListMenuStringSubMenu = ExpandableListDataSource.getData(menus, submenus);
        mExpandableListMenu = ArrayList(mExpandableListMenuStringSubMenu.keys);
        mExpandableListAdapter.setList(menus, mExpandableListMenu, mExpandableListMenuStringSubMenu);
    }

    override fun setError(error: String) {
        Utils.showSnackBar(drawer_layout, error)
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


//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        if (id == R.id.nav_gallery) {
//            startActivity(Intent(this, TabActivity::class.java))
//
//        }
//
////        if (id == R.id.nav_camera) {
////        } else
//
//
//        /*
//        else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//*/
//        //   val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
//        drawer_layout.closeDrawer(GravityCompat.START)
//        return true
//    }

    override fun onPause() {
        super.onPause()
        unregister()
//        mAd.pause(this)
//        if (mAdView != null) {
//            mAdView.pause()
//        }
    }

    override fun onResume() {
        super.onResume()
        register()
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

    override fun onStart() {
        super.onStart()
        register()
    }

    override fun onStop() {
        super.onStop()
        unregister()
    }


    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}
