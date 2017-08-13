package com.valdroide.thesportsbillboardinstitution.main.splash.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main.MainActivity
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), SplashActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun hideDialogProgress() {
        progressBar.visibility = View.INVISIBLE;
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun goToNav() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun goToLog() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
