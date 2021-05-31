package com.example.kenshuu.repository.total

import com.example.kenshuu.data.ApiServer
import com.example.kenshuu.model.Count
import io.reactivex.rxjava3.core.Single

class CountRepositoryImpl (private val apiServer: ApiServer) :CountRepository {
    override fun getTotal(token: String): Single<List<Count>> =
        apiServer.getTotal(token)
}