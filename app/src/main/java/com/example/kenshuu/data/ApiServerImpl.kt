package com.example.kenshuu.data

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response

class ApiServerImpl(private val apiServer: ApiServer) : ApiServer {
    override fun queryAllUser(): Single<User> = apiServer.queryAllUser()
    override fun login(username: DtUser): Single<User> = apiServer.login(username)

}