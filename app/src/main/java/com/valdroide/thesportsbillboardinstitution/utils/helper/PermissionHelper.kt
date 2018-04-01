package com.valdroide.thesportsbillboardinstitution.utils.helper

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.net.ConnectivityManager
import android.os.Build
import android.widget.ImageView
import com.squareup.picasso.Callback
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.valdroide.thesportsbillboardinstitution.R
import android.content.Intent
import android.content.DialogInterface
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.util.Base64
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.google.gson.Gson
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object PermissionHelper {

    fun checkForPermission(activity: Activity, permissionCheck: Int, PERMISSION: Int, manifestPermission: String) {
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(manifestPermission), PERMISSION)
        }
    }

    fun hasPermission(activity: Activity, manifestPermission: String): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(activity, manifestPermission)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    fun oldPhones(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.M

}