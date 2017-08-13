package com.valdroide.thesportsbillboardinstitution.utils

import android.support.design.widget.Snackbar
import android.view.View


object Utils {
    fun showSnackBar(conteiner: View, msg: String) {
        Snackbar.make(conteiner, msg, Snackbar.LENGTH_LONG).show()
    }
}