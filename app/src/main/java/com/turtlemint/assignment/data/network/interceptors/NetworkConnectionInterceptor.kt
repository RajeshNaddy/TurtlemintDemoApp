package com.turtlemint.assignment.data.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.turtlemint.assignment.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!isInternetAvailable()) {
                throw NoInternetException("Make sure you have an active data connection")
            }
        } catch (e: Exception) {
            throw NoInternetException("Connection Closed")
        }

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }

        }
        return result
    }
}