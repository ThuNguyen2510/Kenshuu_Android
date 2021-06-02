package com.example.kenshuu.repository.user

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.model.*
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl (private val apiServer: ApiServer) : UserRepository{
    override fun queryAllUser(auth: String): Single<List<DtUser>> =
         apiServer.queryAllUser(auth)

    override fun queryAllRole(auth: String): Single<List<Role>> =
        apiServer.queryAllRole(auth)

    override fun queryAllGender(auth: String): Single<List<Gender>> =
        apiServer.queryAllGender(auth)

    override fun search(auth: String, user: DtUser): Single<List<DtUser>> =
        apiServer.search(auth,user)

    override fun createUser(auth: String,user: DtUser): Single<Flag> =
        apiServer.createUser(auth,user)

    override fun deleteUser(auth: String, user: DtUser): Single<Flag> =
        apiServer.deleteUser(auth, user)

    override fun updateUser(auth: String, user: DtUser): Single<Flag> =
        apiServer.updateUser(auth, user)
}