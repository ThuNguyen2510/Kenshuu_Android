package com.example.kenshuu.repository.user

import com.example.kenshuu.model.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUser(): Single<User>
}