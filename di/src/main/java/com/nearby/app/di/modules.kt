package com.nearby.app.di

import com.nearby.app.Constants.BASE_RETROFIT_URL
import com.nearby.app.data.AppDataManager
import com.nearby.app.data.DataManager
import com.nearby.app.data.api.retrofit.ApiHelperInterface
import com.nearby.app.data.api.retrofit.ApiKeyInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d("NETWORK: $message")
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    single(named("normal")) {
        OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            // That contains clientSecret - clientId as query params
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(ChuckInterceptor(get()))
            .addNetworkInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .build()
                return@addNetworkInterceptor chain.proceed(request)
            }
            .build()
    }

    single(named("normalRetrofit")) {
        Retrofit.Builder()
            .client(get(named("normal")))
            .baseUrl(BASE_RETROFIT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /*Normal Auth API*/
    single(named("normalApi")) { get<Retrofit>(named("normalRetrofit")).create(ApiHelperInterface::class.java) }


    single<DataManager> {
        AppDataManager(get(named("normalApi")))
    }


}






