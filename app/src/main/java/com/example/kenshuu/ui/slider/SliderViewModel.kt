package com.example.kenshuu.ui.slider

import com.example.kenshuu.model.DtUser
import com.example.kenshuu.repository.login.LoginRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SliderViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _flag = SingleLiveEvent<Resource<String>>()
    val flag: SingleLiveEvent<Resource<String>>
        get() = _flag

    fun logout() {
        launch {
            loginRepository.logout()
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