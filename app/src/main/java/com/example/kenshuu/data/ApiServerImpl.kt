package com.example.kenshuu.data

import com.example.kenshuu.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response

class ApiServerImpl(private val apiServer: ApiServer) : ApiServer {
    override fun queryAllUser(auth: String): Single<List<DtUser>> =
        apiServer.queryAllUser(auth)

    override fun search(auth: String, user: DtUser): Single<List<DtUser>> =
        apiServer.search(auth, user)

    override fun queryAllRole(auth: String): Single<List<Role>> =
        apiServer.queryAllRole(auth)

    override fun queryAllGender(auth: String): Single<List<Gender>> =
        apiServer.queryAllGender(auth)

    override fun login(user: DtUser): Single<User> =
        apiServer.login(user)

    override fun logout() : Single<String> =
        apiServer.logout()

    override fun createUser(auth: String,user: DtUser): Single<Flag> =
        apiServer.createUser(auth,user)

    override fun getTotal(auth: String): Single<List<Count>> =
        apiServer.getTotal(auth)

}