package com.nearby.app.data.api.retrofit

import com.nearby.app.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor : Interceptor {

    private val clientId = "client_id"
    private val clientSecret = "client_secret"
    private val versionParams = "v"


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(clientId, Constants.NEARBY_CLIENT_ID)
            .addQueryParameter(clientSecret, Constants.NearBy_Client_Secret)

//                that represent data make last change with form (YYYYMMDD) 2021/4/24
//                can send dynimacally from presenter  with current date

            .addQueryParameter(versionParams, "20210424")

            .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}