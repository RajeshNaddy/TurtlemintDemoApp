package com.turtlemint.assignment.data.network.interceptors

import com.turtlemint.assignment.util.ConnectionTimedOutException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

class TimeoutConnectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw ConnectionTimedOutException("Connection Timed Out")
        }
    }
}