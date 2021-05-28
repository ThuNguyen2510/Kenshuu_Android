package com.example.kenshuu.repository.login

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response

interface LoginRepository {
    fun login(user: DtUser): Single<User>
}