package com.example.kenshuu.ui.main

import android.util.Log
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import com.example.kenshuu.model.User
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _users = SingleLiveEvent<Resource<List<DtUser>>>()
    val users: SingleLiveEvent<Resource<List<DtUser>>>
        get() = _users

    private val _roles = SingleLiveEvent<Resource<List<Role>>>()
    val roles: SingleLiveEvent<Resource<List<Role>>>
        get() = _roles

    private val _user = SingleLiveEvent<Resource<DtUser>>()
    val user: SingleLiveEvent<Resource<DtUser>>
        get() = _user


    fun queryAllUser(token: String) {
        launch {
            userRepository.queryAllUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    with(this) {
                        if (error != null) {
                            _users.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _users.value = Resource.success(data)
                    }
                }
        }
    }

    fun queryAllRole(auth: String) {
        launch {
            userRepository.queryAllRole(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    with(this) {
                        if (error != null) {
                            _roles.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _roles.value = Resource.success(data)
                    }
                }
        }
    }

    fun search(token: String, user: DtUser) {
        launch {
            userRepository.search(token, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    with(this) {
                        if (error != null) {
                            _users.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _users.value = Resource.success(data)
                    }
                }
        }
    }


}