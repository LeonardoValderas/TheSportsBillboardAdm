package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
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
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.TabActivity
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.helper.ViewComponentHelper
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.helper.SharedHelper
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import com.valdroide.thesportsbillboardinstitution.utils.helper.ConstantHelper
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_navigation.*
import javax.inject.Inject
import android.R.attr.fragment
import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.utils.SectionsPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer


open class NavigationActivity : BaseActivity(), NavigationActivityView, GenericOnItemClick<MenuDrawer> {

    @Inject
    lateinit var presenter: NavigationActivityPresenter
    lateinit var adapterMenu: NavigationActivityAdapter
    private var id_menu = 1
    private var id_tournament = 1
    private lateinit var adapter: SectionsPagerAdapter

    //important get firt to database, is null get to network is null and the varible hasMenu is tru. show erro

    var menus: MutableList<MenuDrawer> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        fabOnclick()
        presenter.getMenus(this)
    }

    private fun initComponents(){
        initToolbar()
        initNavigationBottom()
        initViewPager()
        initDrawerToggle()
        initRecyclerView()
    }

    private fun initNavigationBottom() {
        navigation_bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation_bottom.enableItemShiftingMode(false)
        navigation_bottom.enableShiftingMode(false)
        navigation_bottom.enableAnimation(false)
    }

    private fun initViewPager() {
        adapter = SectionsPagerAdapter(supportFragmentManager, arrayOf(), initListFragment(id_menu))
        view_pager.setAdapter(adapter)
        view_pager.setPageTransformer(true, RotateUpTransformer())

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                var current_position = position
                if (current_position >= 2)// 2 is center fab
                    current_position++// if page is 2, need set bottom item to 3, and the same to 3 -> 4
                Toast.makeText(applicationContext, position.toString().plus(" page"), Toast.LENGTH_SHORT).show()
                 navigation_bottom.setCurrentItem(current_position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun initListFragment(id_menu: Int): Array<Fragment> {
        initFragmentsInstance(id_menu)
        return arrayOf(FixtureFragment.newInstance(id_menu, id_tournament),
                LeaderBoardFragment.newInstance(id_menu, id_tournament),
                NewsFragment.newInstance(id_menu),
                SanctionFragment.newInstance(id_menu, id_tournament))
        /*return mutableListOf(fixtureFragment!!,
                leaderBoardFragment!!,
                newFragment!!,
                sanctionFragment!!)*/
    }

    fun initFragmentsInstance(id_menu: Int) {
        /*  fixtureFragment = FixtureFragment.newInstance(id_menu, id_tournament)
          leaderBoardFragment = LeaderBoardFragment.newInstance(id_menu, id_tournament)
          newFragment = NewsFragment.newInstance(id_menu)
          sanctionFragment = SanctionFragment.newInstance(id_menu, id_tournament)*/
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var previousPosition: Int = -1
        var position = 0

        when (item.itemId) {
            R.id.navigation_fixture -> {
               // managerFragmentsTransaction(FixtureFragment.newInstance(id_menu, id_tournament))
                position = 0

                //    message.setText(R.string.title_home)
             //   return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                //managerFragmentsTransaction(LeaderBoardFragment.newInstance(id_menu, id_tournament))
                position = 1
                //  message.setText(R.string.title_dashboard)
               // return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_news -> {
                //managerFragmentsTransaction(NewsFragment.newInstance(id_menu))
                position = 2
                //message.setText(R.string.title_notifications)
                //return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sanction -> {
               // managerFragmentsTransaction(SanctionFragment.newInstance(id_menu, id_tournament))
                position = 3
                //message.setText(R.string.title_notifications)
                //return@OnNavigationItemSelectedListener true
            }
            R.id.is_menu -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener false
            }
        }

        if(previousPosition != position) {
            view_pager.setCurrentItem(position, false)
            previousPosition = position
            Toast.makeText(this, position.toString().plus(" onclick"), Toast.LENGTH_SHORT).show()
        }
            true
    }

    private fun managerFragmentsTransaction(fragment: Fragment) {

/*        val fragmentManager = getSupportFragmentManager()
        val transaction = fragmentManager.beginTransaction()
                .replace(R.id.frame_conteiner, fragment)

        transaction.addToBackStack(null)

        //      if (allowStateLoss || !BuildConfig.DEBUG) {
        //        ft.commitAllowingStateLoss()
        //  } else {
        transaction.commit()
        // }
        fragmentManager.executePendingTransactions()*/
    }

    private fun fabOnclick() {
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AccountActivity::class.java))
        }
        center_fab.setOnClickListener { view ->

            showSnackBar("Hola")
        }

    }

    //I dont realice inject because it has a error
    private fun getAdapter(): NavigationActivityAdapter = NavigationActivityAdapter(this, this)

    override fun onClick(view: View, position: Int, t: MenuDrawer) {
        try {
            val menuJson = SharedHelper.convertJsonParceable(t)
            if (menuJson.isEmpty())
                showSnackBar(getString(R.string.generic_error_response))
            else {
                //SharedHelper.setMenuJson(this, menuJson)
                goToTabActivity(menuJson)
            }
        } catch (e: Exception) {
            showSnackBar(e.message!!)
        }
    }

    private fun goToTabActivity(json: String) {
        val intent = Intent(applicationContext, TabActivity::class.java)
        intent.putExtra(ConstantHelper.JSON_ENTITY, json)
        startActivity(intent)
    }

    private fun showSnackBar(msg: String) = ViewComponentHelper.showSnackBar(drawer_layout, msg)

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initRecyclerView() {
        with(rv_menu) {
            layoutManager = LinearLayoutManager(context)
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
