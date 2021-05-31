package com.example.kenshuu.ui.total

import android.util.Log
import com.example.kenshuu.model.Count
import com.example.kenshuu.repository.total.CountRepository
import com.example.kenshuu.ui.base.BaseViewModel
import com.example.kenshuu.ui.base.Resource
import com.example.kenshuu.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TotalViewModel(private val countRepository: CountRepository): BaseViewModel() {
    private val _resources = SingleLiveEvent<Resource<List<Count>>>()
    val resources: SingleLiveEvent<Resource<List<Count>>>
        get() = _resources

    fun getTotal(token: String){
        launch {
            countRepository.getTotal(token)
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