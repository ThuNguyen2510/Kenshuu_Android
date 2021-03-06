package com.example.kenshuu.repository.login

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response

class LoginRepositoryImpl (private val apiServer: ApiServer) : LoginRepository {
    override fun login(user: DtUser): Single<User> =
        apiServer.login(user)

    override fun logout() =
        apiServer.logout()
}