package com.example.kenshuu.ui.user.delete

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Flag
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DeleteUserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _flag = SingleLiveEvent<Resource<Flag>>()
    val flag: SingleLiveEvent<Resource<Flag>>
        get() = _flag

    fun deleteUser(token: String, user: DtUser) {
        launch {
            userRepository.deleteUser(token, user)
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