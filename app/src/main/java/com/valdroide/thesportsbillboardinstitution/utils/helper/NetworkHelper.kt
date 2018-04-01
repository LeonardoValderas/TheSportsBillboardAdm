package com.valdroide.thesportsbillboardinstitution.utils.helper

import android.content.Context
import android.net.ConnectivityManager
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.*

object NetworkHelper {

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
}