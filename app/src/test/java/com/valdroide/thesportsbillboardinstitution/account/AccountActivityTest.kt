package com.valdroide.thesportsbillboardinstitution.account

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.nhaarman.mockito_kotlin.*
import com.squareup.picasso.Picasso
import com.valdroide.thesportsbillboardinstitution.BaseTest
import com.valdroide.thesportsbillboardinstitution.BuildConfig
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_user.account.AccountActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.account.events.AccountActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivity
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import org.junit.Assert
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity
import kotlin.test.*


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TheSportsBillboardInstitutionApp::class, sdk = intArrayOf(23))
class AccountActivityTest : BaseTest() {

    @Mock
    lateinit var event: AccountActivityEvent
    @Mock
    lateinit var presenter: AccountActivityPresenter
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var account: Account
    @Mock
    lateinit var picasso: Picasso

    lateinit var view: AccountActivityView
    lateinit var activity: AccountActivity
    lateinit var controller: ActivityController<AccountActivity>
    lateinit var shadowActivity: ShadowActivity

    @Before
    override fun setUp() {
        super.setUp()

        val accountActivity = object : AccountActivity() {
//            override fun setTheme(resid: Int) {
//                super.setTheme(R.style.AppTheme_NoActionBar)
//            }

            override fun getPresenter(): AccountActivityPresenter =
                    presenter
        }

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), accountActivity as AccountActivity).create().visible()
        activity = controller.get()
        view = activity
        context = activity
        shadowActivity = Shadows.shadowOf(activity)

    }

    @Test
    fun onCreateTest() {
        verify(presenter).onCreate()
        val progressBar: ProgressBar = activity.progressBar
        val linearConteiner: LinearLayout = activity.linearConteiner
        val fabConteiner: FloatingActionMenu = activity.fabConteiner
        Assert.assertEquals(View.VISIBLE, progressBar.visibility)
        Assert.assertEquals(View.INVISIBLE, linearConteiner.visibility)
        Assert.assertEquals(View.INVISIBLE, fabConteiner.visibility)
        verify(presenter).getAccount(context)
    }

    @Test
    fun shouldViewSetAccountAndFillViews_setAccount() {
        val url = "http://www.caratulasylogos.com/sites/default/files/almirante_brown.jpg"
        val descrition = "Description"
        val phone = "123456"
        val email = "email"
        val web = "web"
        val face = "face"
        val insta = "insta"

        whenever(account.URL_IMAGE).thenReturn(url)
        whenever(account.DESCRIPTION).thenReturn(descrition)
        whenever(account.PHONE).thenReturn(phone)
        whenever(account.EMAIL).thenReturn(email)
        whenever(account.WEB).thenReturn(web)
        whenever(account.FACEBOOK).thenReturn(face)
        whenever(account.INSTAGRAM).thenReturn(insta)

        view.setAccount(account)

        val imageView: ImageView = activity.imageView
        val textViewDescription: TextView = activity.textViewDescription
        val textViewPhone: TextView = activity.textViewPhone
        val textViewEmail: TextView = activity.textViewEmail
        val textViewWeb: TextView = activity.textViewWeb
        val textViewFace: TextView = activity.textViewFace
        val textViewInsta: TextView = activity.textViewInsta

        /**
         * Investigate how to vefify a static class(Utils).
         * I have gradle problem when implement powermockito
         */

        assertEquals(textViewDescription.text, descrition)
        assertEquals(textViewPhone.text, phone)
        assertEquals(textViewEmail.text, email)
        assertEquals(textViewWeb.text, web)
        assertEquals(textViewFace.text, face)
        assertEquals(textViewInsta.text, insta)
    }

    @Test
    fun shouldGoToSnackBarEmptyData_onClick_fabPhone() {
        val textViewPhone: TextView = activity.textViewPhone
        textViewPhone.text = ""
        val fabPhone: FloatingActionButton = activity.fabPhone
        fabPhone.performClick()
        assertNotNull(textViewPhone.text)
        assertEquals(textViewPhone.text, "")
       // verify(utils).showSnackBar(activity.conteiner, context.getString(R.string.datp_empty_account))
        //static class
    }

    @Test
    fun shouldGoToCallPhoneAndThowIntent_onClick_fabPhone() {
        val textViewPhone: TextView = activity.textViewPhone
        val phone = "123"
        textViewPhone.text = phone
        val fabPhone: FloatingActionButton = activity.fabPhone
        fabPhone.performClick()
        assertNotNull(textViewPhone.text)
        assertEquals(textViewPhone.text, phone)
        //if (!Utils.oldPhones()) static class
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        //Utils.checkForPermission(this, permissionCheck, PERMISSION_CALL) static class
        // if (Utils.hasPermission(this)) static class
        //callPhone(number_phone) method
        val implicitIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
        activity.startActivity(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(implicitIntent, intent)
        assertTrue(intent.filterEquals(implicitIntent));
    }

    @Test
    fun shouldGoToSnackBarEmptyData_onclick_fabEmail() {
        val textViewEmail: TextView = activity.textViewEmail
        textViewEmail.text = ""

        val fabEmail: FloatingActionButton = activity.fabEmail
        fabEmail.performClick()
        assertNotNull(textViewEmail.text)
        assertEquals(textViewEmail.text, "")
    }

    @Test
    fun shouldGoToEmailIntentAndThowIntent_onClick_fabEmail() {
        val textViewEmail: TextView = activity.textViewEmail
        val email = "l.v.bass@hotmail.com"
        textViewEmail.text = email

        val fabEmail: FloatingActionButton = activity.fabEmail
        fabEmail.performClick()
        assertNotNull(textViewEmail.text)
        assertEquals(textViewEmail.text, email)
        val implicitIntent = Intent(Intent.ACTION_SENDTO)
        implicitIntent.data = Uri.parse("mailto:" + email)
        activity.startActivity(implicitIntent)

        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertEquals(intent.action, implicitIntent.action)
        assertEquals(intent.data, Uri.parse("mailto:" + email))
        assertTrue(intent.filterEquals(implicitIntent));
    }


    @Test
    fun shouldGoToOpenWebURLAndThowIntent_onClick_fabWeb() {
        val textViewWeb: TextView = activity.textViewWeb
        val web = "valdroide.com"
        val HTTP: String = "http://" + web
        textViewWeb.text = web

        val fabWeb: FloatingActionButton = activity.fabWeb
        fabWeb.performClick()
        assertNotNull(textViewWeb.text)
        assertEquals(textViewWeb.text, web)
        assertEquals(URLUtil.isValidUrl(HTTP), true)
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(HTTP))
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertEquals(intent.action, implicitIntent.action)
        assertEquals(intent.data, Uri.parse(HTTP))
        assertTrue(intent.filterEquals(implicitIntent))
    }

    @Test
    fun shouldThowSnackUrlInvalid_onClick_fabWeb() {
        val textViewWeb: TextView = activity.textViewWeb
        val web = "valq11qqqq.com"
        val HTTP: String = "htt" + web
        textViewWeb.text = web

        val fabWeb: FloatingActionButton = activity.fabWeb
        fabWeb.performClick()
        assertNotNull(textViewWeb.text)
        assertEquals(textViewWeb.text, web)
        assertEquals(URLUtil.isValidUrl(HTTP), false)
        // verify(utils).showSnackBar(activity.conteiner, context.getString(R.string.datp_empty_account))
        //static class
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(HTTP))
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertFalse(intent.filterEquals(implicitIntent))
    }
/*
    @Test
    fun shouldThowException_onClick_fabFace() {

//        val textViewFace: TextView = activity.textViewFace
        val face = "leonardo.valderas9"
        val FACEBOOK_PACK = "https://www.facebook.com/" + face
  //      textViewFace.text = face
/*
        val fabFace: FloatingActionButton = activity.fabFace
        fabFace.performClick()
        assertNotNull(textViewFace.text)
        assertEquals(textViewFace.text, face)
        assertEquals(URLUtil.isValidUrl(FACEBOOK_PACK), true)
        // val packageManager = activity.getPackageManager()
        // assertNull(packageManager.getLaunchIntentForPackage("com.facebook.katana"))

    */
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_PACK))
        activity.startActivity(implicitIntent)
        val intentShadow = Shadows.shadowOf(implicitIntent)

        whenever(intentShadow).thenThrow(Exception())

       // activity.startActivity(intentShadow)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()

        assertNotNull(intent)
        // verify(utils).showSnackBar(activity.conteiner, context.getString(R.string.datp_empty_account))
        //static class


//        assertNotNull(intent)
//        assertTrue(intent.filterEquals(implicitIntent))
    }
*/
    @Test
    fun shouldGoToGetRedSocialAndThowIntent_onClick_fabFace() {
        val textViewFace: TextView = activity.textViewFace
        val face = "leonardo.valderas9"
        val FACEBOOK_PACK = "https://www.facebook.com/" + face
        textViewFace.text = face

        val fabFace: FloatingActionButton = activity.fabFace
        fabFace.performClick()
        assertNotNull(textViewFace.text)
        assertEquals(textViewFace.text, face)
        assertEquals(URLUtil.isValidUrl(FACEBOOK_PACK), true)
        // val packageManager = activity.getPackageManager()
        // assertNull(packageManager.getLaunchIntentForPackage("com.facebook.katana"))
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_PACK))
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertTrue(intent.filterEquals(implicitIntent))
    }

    @Test
    fun shouldGoToGetRedSocialAndThowIntent_onClick_fabInsta() {
        val textViewInsta: TextView = activity.textViewInsta
        val insta = "leonardo.valderas9"
        val INSTAGRAM_PACK = "https://www.instagram.com/_u/" + insta
        textViewInsta.text = insta

        val fabInsta: FloatingActionButton = activity.fabInsta
        fabInsta.performClick()
        assertNotNull(textViewInsta.text)
        assertEquals(textViewInsta.text, insta)
        assertEquals(URLUtil.isValidUrl(INSTAGRAM_PACK), true)
        // val packageManager = activity.getPackageManager()
        // assertNull(packageManager.getLaunchIntentForPackage("com.facebook.katana"))
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_PACK))
        activity.startActivity(implicitIntent)
        assertNotNull(implicitIntent)
        val intent = shadowActivity.peekNextStartedActivity()
        assertNotNull(intent)
        assertTrue(intent.filterEquals(implicitIntent))
    }

    @Test
    fun onDestroyTest() {
        controller.destroy()
        verify(presenter).onDestroy()
    }

    @Test
    fun onPauseTest() {
        controller.pause()
        verify(presenter).onDestroy()
    }

    @Test
    fun onStartTest() {
        controller.start()
        verify(presenter).onCreate()
    }

    @Test
    fun onStopTest() {
        controller.stop()
        verify(presenter).onDestroy()
    }
}