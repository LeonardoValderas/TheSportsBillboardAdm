package com.valdroide.thesportsbillboardinstitution.main_adm.navigation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.account.ui.AccountAdmActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.activity.TabFixtureActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.login.activity.TabLoginActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.news.activity.TabNewsActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.player.activity.TabPlayerActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.activity.TabSanctionActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation_adm.*
import kotlinx.android.synthetic.main.app_bar_navigation_adm.*

class NavigationAdmActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //      val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
        }
        initDrawerLayout()

        //   val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_navigation_adm

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
    }

    override fun setupInjection() {
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun goToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        val user = Utils.getIdUserWork(this)
        intent.putExtra("id_user", user)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_adm, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_team -> goToActivity(TabTeamActivity())
            R.id.nav_tournament -> goToActivity(TournamentActivity())
            R.id.nav_fixture -> goToActivity(TabFixtureActivity())
            R.id.nav_player -> goToActivity(TabPlayerActivity())
            R.id.nav_sanctions -> goToActivity(TabSanctionActivity())
            R.id.nav_notification -> {
            }
            R.id.nav_account -> goToActivity(AccountAdmActivity())
            R.id.nav_menu_submenu -> goToActivity(MenuSubMenuActivity())
            R.id.nav_news -> goToActivity(TabNewsActivity())
            R.id.nav_login -> goToActivity(TabLoginActivity())
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
