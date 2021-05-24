package com.example.kenshuu.data

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServer {
    @GET("api-user")
    fun queryAllUser(): Single<User>

    @Headers("Content-Type: application/json")
    @POST("auth")
    fun login(
        @Body userId: DtUser
    ): Single<User>

}