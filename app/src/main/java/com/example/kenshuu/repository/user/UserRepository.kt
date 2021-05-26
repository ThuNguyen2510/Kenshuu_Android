package com.example.kenshuu.repository.user

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun queryAllUser(auth: String): Single<List<DtUser>>
    fun queryAllRole(auth: String): Single<List<Role>>
}