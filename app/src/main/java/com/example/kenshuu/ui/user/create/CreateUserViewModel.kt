package com.example.kenshuu.ui.user.create

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Flag
import com.example.kenshuu.model.Gender
import com.example.kenshuu.model.Role
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CreateUserViewModel (private val userRepository: UserRepository) : BaseViewModel()  {
    private val _roles = SingleLiveEvent<Resource<List<Role>>>()
    val roles: SingleLiveEvent<Resource<List<Role>>>
        get() = _roles

    private val _genders = SingleLiveEvent<Resource<List<Gender>>>()
    val genders: SingleLiveEvent<Resource<List<Gender>>>
        get() = _genders

    private val _flag = SingleLiveEvent<Resource<Flag>>()
    val flag: SingleLiveEvent<Resource<Flag>>
        get() = _flag
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

    fun queryAllGender(auth: String) {
        launch {
            userRepository.queryAllGender(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    with(this) {
                        if (error != null) {
                            _genders.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _genders.value = Resource.success(data)
                    }
                }
        }
    }
    fun createUser(token: String, user: DtUser){
        launch {
            userRepository.createUser(token,user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    with(this) {
                        if (error != null) {
                            _flag.value =
                                Resource.error(error.localizedMessage.orEmpty(), null)
                            return@with
                        }
                        _flag.value = Resource.success(data)
                    }
                }

        }
    }
}