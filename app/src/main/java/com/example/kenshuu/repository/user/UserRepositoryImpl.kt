package com.example.kenshuu.repository.user

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl (private val apiServer: ApiServer) : UserRepository{
    override fun getUser(): Single<User> =
         apiServer.queryAllUser()

}