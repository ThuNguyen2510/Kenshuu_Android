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

    private val _resources = SingleLiveEvent<Resource<List<DtUser>>>()
    val resources: SingleLiveEvent<Resource<List<DtUser>>>
        get() = _resources
    private val _roleresources = SingleLiveEvent<Resource<List<Role>>>()
    val roleresources: SingleLiveEvent<Resource<List<Role>>>
        get() = _roleresources

    fun queryAllUser(token: String){
        launch{
            userRepository.queryAllUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error->
                    with(this) {
                        if (error != null) {
                            _resources.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _resources.value = Resource.success(data)
                    }
                }
        }
    }
    fun queryAllRole(auth:String){
        launch {
            userRepository.queryAllRole(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error->
                    with(this) {
                        if (error != null) {
                            _roleresources.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _roleresources.value = Resource.success(data)
                    }
                }
        }
    }

}