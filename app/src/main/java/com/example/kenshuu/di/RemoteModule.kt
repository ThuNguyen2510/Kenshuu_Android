package com.example.kenshuu.di

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.repository.login.LoginRepository
import com.example.kenshuu.repository.login.LoginRepositoryImpl
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.repository.user.UserRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val remoteDataSourceModule = module {
    single { createOkHttpClient() }
    single { createWebService<ApiServer>(get(), "http://192.168.1.6:8080/kenshuu/") }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single <UserRepository> {UserRepositoryImpl(get())}
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val TIME_OUT = 60L

    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()

    return retrofit.create(T::class.java)
}