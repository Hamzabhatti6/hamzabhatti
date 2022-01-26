package com.xisys.hamzabhatti.networks

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkConnectionInterceptor(var mContext: Context?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean = isInternetConnected(mContext!!)

}

fun isInternetConnected(context: Context): Boolean {
    var result = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> isNetworkOnline()
                }
            }
        }
    } else {
        cm.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_VPN) {
                    result = true
                }else{
                    isNetworkOnline()
                }
            }
        }
    }
    return result
}


fun isNetworkOnline(): Boolean {
    var isOnline = false
    try {
        val url = URL("http://www.google.com") // or your server address
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestProperty("Connection", "close")
        conn.connectTimeout = 3000
        isOnline = conn.responseCode == 200
        conn.disconnect()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return isOnline
}