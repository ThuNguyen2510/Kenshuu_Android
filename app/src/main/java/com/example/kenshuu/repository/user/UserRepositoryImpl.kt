package com.example.kenshuu.repository.user

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl (private val apiServer: ApiServer) : UserRepository{
    override fun queryAllUser(auth: String): Single<List<DtUser>> =
         apiServer.queryAllUser(auth)

    override fun queryAllRole(auth: String): Single<List<Role>> =
        apiServer.queryAllRole(auth)

    override fun search(auth: String, user: DtUser): Single<List<DtUser>> =
        apiServer.search(auth,user)

}