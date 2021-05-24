package com.example.kenshuu.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import com.example.kenshuu.databinding.ActLoginBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import com.example.kenshuu.repository.login.LoginRepository
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.ui.main.MainViewModel
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.xml.sax.DTDHandler
import retrofit2.Response

class LoginViewModel (private val loginRepository: LoginRepository) : BaseViewModel(){
    private val _resources = SingleLiveEvent<Resource<User>>()
    val resources: SingleLiveEvent<Resource<User>>
        get() = _resources

    fun login(userId: String, password: String) {
        launch {
            val user: DtUser= DtUser(userId=userId,password = password)
                loginRepository.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
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
}