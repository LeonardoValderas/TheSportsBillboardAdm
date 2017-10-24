package com.valdroide.thesportsbillboardinstitution.main_adm.navigation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.activity.TabFixtureActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.login.activity.TabLoginActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.news.activity.TabNewsActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.player.activity.TabPlayerActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.activity.TabSanctionActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.team.activity.TabTeamActivity
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class NavigationAdmActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_adm)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun goToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.putExtra("id_user", Utils.getIdUserWork(this))
        startActivity(intent)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_adm, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_team) {
            goToActivity(TabTeamActivity())
        } else if (id == R.id.nav_tournament) {
            goToActivity(TournamentActivity())
        } else if (id == R.id.nav_fixture) {
            goToActivity(TabFixtureActivity())
        } else if (id == R.id.nav_player) {
            goToActivity(TabPlayerActivity())
        } else if (id == R.id.nav_sanctions) {
            goToActivity(TabSanctionActivity())
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_account) {
            goToActivity(AccountActivity())
        } else if (id == R.id.nav_menu_submenu) {
            goToActivity(MenuSubMenuActivity())
        } else if (id == R.id.nav_news) {
            goToActivity(TabNewsActivity())
        } else if (id == R.id.nav_login) {
            goToActivity(TabLoginActivity())
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
