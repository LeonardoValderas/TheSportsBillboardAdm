package com.valdroide.thesportsbillboardinstitution.utils.helper

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.content.Intent
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.valdroide.thesportsbillboardinstitution.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

object ViewComponentHelper {

    fun showSnackBar(conteiner: View, msg: String) {
        Snackbar.make(conteiner, msg, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }
    fun ImageDialogLogo(activity: Activity?, fragment: Fragment?, GALLERY: Int) {
        val myAlertDialog: AlertDialog.Builder
        if (activity != null)
            myAlertDialog = AlertDialog.Builder(activity)
        else
            myAlertDialog = AlertDialog.Builder(fragment!!.activity)

        myAlertDialog.setTitle("Galeria")
        myAlertDialog.setMessage("Seleccione una foto.")
        myAlertDialog.setPositiveButton("Galeria",
                DialogInterface.OnClickListener { arg0, arg1 ->
                    val pickIntent = Intent(
                            Intent.ACTION_GET_CONTENT, null)
                    pickIntent.type = "image/*"
                    pickIntent.putExtra(
                            "return-data", true)
                    if (activity != null)
                        activity.startActivityForResult(pickIntent, GALLERY)
                    else
                        fragment!!.startActivityForResult(pickIntent, GALLERY)
                })
        myAlertDialog.show()
    }

    fun showAlertInformation(activity: Activity, titleText: String, messageText: String) {
        activity.alert(messageText) {
            title = titleText
            yesButton {
            }
        }.show()
    }

    fun verifySwipeRefresh(conteiner: View, swipeRefreshLayoutLeader: SwipeRefreshLayout?, show: Boolean) {
        try {
            if (swipeRefreshLayoutLeader != null) {
                if (show) {
                    if (!swipeRefreshLayoutLeader.isRefreshing()) {
                        swipeRefreshLayoutLeader.setRefreshing(true)
                    }
                } else {
                    if (swipeRefreshLayoutLeader.isRefreshing()) {
                        swipeRefreshLayoutLeader.setRefreshing(false)
                    }
                }
            }
        } catch (e: Exception) {
            showSnackBar(conteiner, e.message!!)
        }
    }

    fun initRecyclerView(recyclerView: RecyclerView, context: Context) {
        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}