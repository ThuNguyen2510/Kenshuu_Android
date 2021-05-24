package com.example.kenshuu.ui.main

import android.util.Log
import com.example.kenshuu.model.User
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val TAG = "__MainViewModel"

    private val _resources = SingleLiveEvent<Resource<List<User>>>()
    val resources: SingleLiveEvent<Resource<List<User>>>
        get() = _resources

}