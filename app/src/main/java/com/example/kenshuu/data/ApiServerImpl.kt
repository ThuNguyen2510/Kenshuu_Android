package com.example.kenshuu.data

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response

class ApiServerImpl(private val apiServer: ApiServer) : ApiServer {
    override fun queryAllUser(auth: String): Single<List<DtUser>> = apiServer.queryAllUser(auth)
    override fun search(user: DtUser): Single<List<DtUser>> {
        TODO("Not yet implemented")
    }

    override fun queryAllRole(auth: String): Single<List<Role>> =
        apiServer.queryAllRole(auth)

    override fun login(username: DtUser): Single<User> = apiServer.login(username)

}