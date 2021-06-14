package com.example.kenshuu.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.kenshuu.model.User
import com.example.kenshuu.ui.login.LoginActivity
import com.example.kenshuu.ui.login.LoginViewModel
import com.example.kenshuu.utils.PrefsManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract  class BaseFragment <T : ViewBinding, A : Any> : Fragment(){
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected open var binding: T? = null

    protected open var handler: A? = null //It's base activity

    protected abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T
    var countDownTimer = object : CountDownTimer(58*60*1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
        }
        override fun onFinish() {
            val pref: PrefsManager by inject()
            val viewModel: LoginViewModel by inject()
            viewModel.login(pref.getUserId().toString(), pref.getPass().toString())
            viewModel.resources.observe(this@BaseFragment, {
                if (it.data?.status.equals("success")) {//成功の場合
                    val user: User? = it.data
                    if (user != null) {
                        //ユーザ情報とJWTを保存する
                        pref.saveUser(user)
                    }
                }
            })
        }
    }
    private val TAG = "__BaseFragmentActivity"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        this.handler = this.activity as? A
        Log.d(TAG, "onAttach: OK")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = this.setBinding(inflater, container)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}