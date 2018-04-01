package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import android.widget.ExpandableListView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.NavigationActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.ui.adapters.CustomExpandableListAdapter
import com.valdroide.thesportsbillboardinstitution.main_user.tab.TabActivity
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.ImageHelper
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_navigation.*
import java.util.*
import javax.inject.Inject

open class NavigationActivity : BaseActivity(), NavigationActivityView {
    lateinit var mExpandableListAdapter: CustomExpandableListAdapter
    lateinit var mExpandableListMenu: List<String>
    lateinit var mExpandableListMenuStringSubMenu: Map<String, List<SubMenuDrawer>>
    lateinit var navList: ExpandableListView
    var submenus: List<SubMenuDrawer> = arrayListOf()
    private var value: List<SubMenuDrawer>? = null
    @Inject
    lateinit var presenter: NavigationActivityPresenter
    private var position: Int = 0
    var menus: List<MenuDrawer> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMenuSubmenu()
        navList = findViewById<ExpandableListView>(R.id.navList)
        setSupportActionBar(toolbar)
        initDrawerToggle()
        initHeaderNav()
        initAdapterDrawer()
        addDrawerItems()
        ImageHelper.setPicasso(this, "", R.drawable.adeful, imageViewLogo)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AccountActivity::class.java))
        }
        //       nav_view.setNavigationItemSelectedListener(this)
    }


    override fun getLayoutResourceId(): Int = R.layout.activity_navigation

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    override  fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        app.getNavigationActivityComponent(this, this).inject(this)
      //  mExpandableListMenu = getExpandableListTitle()
    }

    private fun getMenuSubmenu() {
        if (menus.isEmpty() && submenus.isEmpty()) {
            showProgressBar()
            presenter.getMenusAndSubMenus(this)
        }
    }

    //fun getExpandableListTitle(): List<String> =
    //      component.getListString()

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
                            SharedHelper.setSubmenuIdTitle(applicationContext, value!!.get(position).ID_SUBMENU_KEY, value!!.get(position).SUBMENU)
                            startActivity(Intent(applicationContext, TabActivity::class.java))
                        } catch (e: Exception) {
                            ViewComponentHelper.showSnackBar(drawer_layout, e.message!!)
                        }
                    } else
                        ViewComponentHelper.showSnackBar(drawer_layout, "Problemas para obtener la información solicitada.")
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setMenuAndSubMenu(menus: List<MenuDrawer>, submenus: List<SubMenuDrawer>) {
        this.menus = menus
  //      this.submenus = submenus
    //    mExpandableListMenuStringSubMenu = ExpandableListDataSource.getData(menus, submenus);
    //    mExpandableListMenu = ArrayList(mExpandableListMenuStringSubMenu.keys);
     //   mExpandableListAdapter.setList(menus, mExpandableListMenu, mExpandableListMenuStringSubMenu);
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
