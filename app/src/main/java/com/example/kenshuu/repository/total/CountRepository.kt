package com.example.kenshuu.repository.total

import com.example.kenshuu.model.Count
import io.reactivex.rxjava3.core.Single

interface CountRepository {
    fun getTotal(token: String):Single<List<Count>>
}