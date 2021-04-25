package com.nearby.app.utils.inteceptor

import android.content.Context
import com.nearby.app.inteceptor.ConnectivityStatus
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor(private val context: Context) : Interceptor {
    private val networkEvent: NetworkEvent = NetworkEvent

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!ConnectivityStatus.isConnected(context)) {
            networkEvent.publish(NetworkState.NO_INTERNET)

        } else {
            try {
                val response = chain.proceed(request)
                when (response.code) {
                    401 -> networkEvent.publish(NetworkState.UNAUTHORISED)

                    503 -> networkEvent.publish(NetworkState.NO_RESPONSE)
                }
                return response

            } catch (e: IOException) {
                networkEvent.publish(NetworkState.NO_RESPONSE)
            }
        }

        return Response.Builder().request(request).build()
    }
}