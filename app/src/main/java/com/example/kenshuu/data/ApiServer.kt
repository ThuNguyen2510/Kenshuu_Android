package com.example.kenshuu.data

import com.example.kenshuu.model.Count
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServer {
    @Headers("Content-Type: application/json")
    @GET("api-user")
    fun queryAllUser(@Header("Authorization") auth: String): Single<List<DtUser>>

    @Headers("Content-Type: application/json")
    @POST("api-search")
    fun search(@Header("Authorization") auth: String, @Body user: DtUser): Single<List<DtUser>>

    @Headers("Content-Type: application/json")
    @GET("api-authority")
    fun queryAllRole(@Header("Authorization") auth: String): Single<List<Role>>

    @Headers("Content-Type: application/json")
    @POST("auth")
    fun login(
        @Body user: DtUser
    ): Single<User>

    @Headers("Content-Type: application/json")
    @GET("auth")
    fun logout(): Single<String>

    @Headers("Content-Type: application/json")
    @GET("api-count")
    fun getTotal(@Header("Authorization") auth: String): Single<List<Count>>

}