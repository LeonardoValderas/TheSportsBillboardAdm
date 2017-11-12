package com.valdroide.thesportsbillboardinstitution.utils

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
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.content_tab.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

        const val PERMISSION_GALERY: Int = 101
        const val URL_PLAYER: String = "http://10.0.3.2:8080/the_sports_billboard_institution/adm/player/image_player/"

        fun setupViewpagerTabs(viewPager: ViewPager, tabs: TabLayout, adapter: SectionsPagerAdapter?): ViewPager {
        viewPager.setAdapter(adapter)
        viewPager.setPageTransformer(true, RotateUpTransformer())
        tabs.setupWithViewPager(viewPager)
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //              hideKeyBoard()
            }

            override fun onPageSelected(position: Int) {
                //            hideKeyBoard()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        return viewPager
    }

    fun showSnackBar(conteiner: View, msg: String) {
        Snackbar.make(conteiner, msg, Snackbar.LENGTH_LONG).show()
    }

    fun oldPhones(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.M


    fun checkForPermission(activity: Activity, permissionCheck: Int, PERMISSION: Int, manifestPermission: String) {
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(manifestPermission), PERMISSION)
        }
    }

    fun hasPermission(activity: Activity, manifestPermission: String): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(activity, manifestPermission)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    fun setUserWork(context: Context, id: Int) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_user_work), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putInt(context.getString(R.string.shared_id_user), id)
        editor.apply()
    }

    fun getIdUserWork(context: Context): Int {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_user_work), Context.MODE_PRIVATE)
        return shared.getInt(context.getString(R.string.shared_id_user), 0)
    }

    fun setSubmenuIdTitle(context: Context, id: Int, title: String) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putInt(context.getString(R.string.shared_id), id)
        editor.putString(context.getString(R.string.shared_title), title)
        editor.apply()
    }

    fun getSubmenuId(context: Context): Int {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        return shared.getInt(context.getString(R.string.shared_id), 0)
    }

    fun getSubmenuTitle(context: Context): String {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        return shared.getString(context.getString(R.string.shared_title), "")
    }

    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnectedOrConnecting) {
                for (i in 0..3) {
                    if (internetConnectionAvailable(5000)) {
                        return true
                    }
                }
                return false
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

    private fun internetConnectionAvailable(timeOut: Long): Boolean {
        val inetAddress: InetAddress?
        try {
            val future = Executors.newSingleThreadExecutor().submit(object : Callable<InetAddress> {
                override fun call(): InetAddress? {
                    try {
                        return InetAddress.getByName("google.com")
                    } catch (e: UnknownHostException) {
                        return null
                    }

                }
            })
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS)
            future.cancel(true)
        } catch (e: InterruptedException) {
            return false
        } catch (e: ExecutionException) {
            return false
        } catch (e: TimeoutException) {
            return false
        }
        return inetAddress != null && !inetAddress.equals("")
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
        activity.alert(titleText) {
            title = messageText
            yesButton {
            }
        }.show()
    }

    fun startCropImageActivity(activity: Activity?, fragment: Fragment?, imageUri: Uri) {
        if (activity != null) {
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .start(activity)
        } else {
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .start(fragment!!.context, fragment)
        }
    }

    fun getFechaOficial(): String {
        val dateOficial = Date()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        return sdf.format(dateOficial)
    }

    fun getFechaOficialSeparate(): String {
        val dateOficial = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(dateOficial)
    }

    @Throws(IOException::class)
    fun readBytes(uri: Uri?, context: Context): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteBuffer = ByteArrayOutputStream()

        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)

        var len = 0
        while (inputStream.read(buffer).let { len = it; it != -1 }) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    fun setPicasso(context: Context, url: String, resource: Int, imageView: ImageView) {
        var urlStrin = url
        if (urlStrin.isEmpty())
            urlStrin = "vacio"

        Picasso.with(context)
                .load(urlStrin).fit()
                .placeholder(resource)
                .centerCrop()
                .transform(RoundedCornersTransformation(8, 0))//radius-margin
                .into(imageView, object : Callback {
                    override fun onSuccess() {}

                    override fun onError() {
                        imageView.setImageResource(resource)
                    }
                })
    }

    fun setPicassoWithOutRoundedConrners(context: Context, url: String, resource: Int, imageView: ImageView) {
        var urlStrin = url
        if (urlStrin.isEmpty())
            urlStrin = "vacio"

        Picasso.with(context)
                .load(urlStrin).fit()
                .placeholder(resource)
                .into(imageView, object : Callback {
                    override fun onSuccess() {}

                    override fun onError() {
                        imageView.setImageResource(resource)
                    }
                })
    }

    class RoundedCornersTransformation @JvmOverloads constructor(private val mRadius: Int, private val mMargin: Int, private val mCornerType: CornerType = CornerType.ALL) : Transformation {

        enum class CornerType {
            ALL,
            TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
            TOP, BOTTOM, LEFT, RIGHT,
            OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
            DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
        }

        private val mDiameter: Int

        init {
            mDiameter = mRadius * 2
        }

        override fun transform(source: Bitmap): Bitmap {

            val width = source.width
            val height = source.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.setAntiAlias(true)
            paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
            drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
            source.recycle()

            return bitmap
        }

        private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
            val right = width - mMargin
            val bottom = height - mMargin

            when (mCornerType) {
                CornerType.ALL -> canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, bottom), mRadius.toFloat(), mRadius.toFloat(), paint)
                CornerType.TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM_RIGHT -> drawBottomRightRoundRect(canvas, paint, right, bottom)
                CornerType.TOP -> drawTopRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM -> drawBottomRoundRect(canvas, paint, right, bottom)
                CornerType.LEFT -> drawLeftRoundRect(canvas, paint, right, bottom)
                CornerType.RIGHT -> drawRightRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(canvas, paint, right, bottom)
                CornerType.DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom)
                else -> canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, bottom), mRadius.toFloat(), mRadius.toFloat(), paint)
            }
        }

        private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), (mMargin + mDiameter).toFloat()),
                    mRadius.toFloat(), mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), (mMargin + mRadius).toFloat(), (mMargin + mRadius).toFloat(), bottom), paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), mMargin.toFloat(), right, bottom), paint)
        }

        private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - mDiameter, mMargin.toFloat(), right, (mMargin + mDiameter).toFloat()), mRadius.toFloat(),
                    mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
            canvas.drawRect(RectF(right - mRadius, (mMargin + mRadius).toFloat(), right, bottom), paint)
        }

        private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), bottom - mDiameter, (mMargin + mDiameter).toFloat(), bottom),
                    mRadius.toFloat(), mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), bottom - mRadius), paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), mMargin.toFloat(), right, bottom), paint)
        }

        private fun drawBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius.toFloat(),
                    mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
            canvas.drawRect(RectF(right - mRadius, mMargin.toFloat(), right, bottom - mRadius), paint)
        }

        private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, (mMargin + mDiameter).toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF(mMargin.toFloat(), (mMargin + mRadius).toFloat(), right, bottom), paint)
        }

        private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, bottom - mRadius), paint)
        }

        private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), mMargin.toFloat(), right, bottom), paint)
        }

        private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
        }

        private fun drawOtherTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRoundRect(RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom - mRadius), paint)
        }

        private fun drawOtherTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRoundRect(RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), mMargin.toFloat(), right, bottom - mRadius), paint)
        }

        private fun drawOtherBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, (mMargin + mDiameter).toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRoundRect(RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF(mMargin.toFloat(), (mMargin + mRadius).toFloat(), right - mRadius, bottom), paint)
        }

        private fun drawOtherBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                                  bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, (mMargin + mDiameter).toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                    paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), (mMargin + mRadius).toFloat(), right, bottom), paint)
        }

        private fun drawDiagonalFromTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                                     bottom: Float) {
            canvas.drawRoundRect(RectF(mMargin.toFloat(), mMargin.toFloat(), (mMargin + mDiameter).toFloat(), (mMargin + mDiameter).toFloat()),
                    mRadius.toFloat(), mRadius.toFloat(), paint)
            canvas.drawRoundRect(RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius.toFloat(),
                    mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), (mMargin + mRadius).toFloat(), right - mDiameter, bottom), paint)
            canvas.drawRect(RectF((mMargin + mDiameter).toFloat(), mMargin.toFloat(), right, bottom - mRadius), paint)
        }

        private fun drawDiagonalFromTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                                      bottom: Float) {
            canvas.drawRoundRect(RectF(right - mDiameter, mMargin.toFloat(), right, (mMargin + mDiameter).toFloat()), mRadius.toFloat(),
                    mRadius.toFloat(), paint)
            canvas.drawRoundRect(RectF(mMargin.toFloat(), bottom - mDiameter, (mMargin + mDiameter).toFloat(), bottom),
                    mRadius.toFloat(), mRadius.toFloat(), paint)
            canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom - mRadius), paint)
            canvas.drawRect(RectF((mMargin + mRadius).toFloat(), (mMargin + mRadius).toFloat(), right, bottom), paint)
        }

        override fun key(): String {
            return "RoundedTransformation(radius=$mRadius, margin=$mMargin, diameter="
            (+mDiameter).toString() + ", cornerType=" + mCornerType.name + ")"
        }
    }
}