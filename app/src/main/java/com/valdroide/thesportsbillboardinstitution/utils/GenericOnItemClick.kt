package com.valdroide.thesportsbillboardinstitution.utils

import android.view.View

interface GenericOnItemClick<in T> {
    fun onClick(view: View, position: Int, t: T)
    /*
    fun onClickActive(view: View, position: Int, t: T)
    fun onClickUnActive(view: View, position: Int, t: T)
    fun onClickSave(view: View, position: Int, t: T)
    fun onClickUpdate(view: View, position: Int, t: T)
    fun onClickDelete(view: View, position: Int, t: T)
    fun boxEvent(position: Int, id_submenu: T, id_tournament: Int, isActual: Boolean)
    fun snackBarIsActual()
    */
}