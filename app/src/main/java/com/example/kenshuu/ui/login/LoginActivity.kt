package com.example.kenshuu.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.example.kenshuu.databinding.ActLoginBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.main.MainViewModel
import com.example.kenshuu.utils.PrefsManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.act_login.*
import org.koin.android.ext.android.inject
import org.koin.ext.quoted

class LoginActivity  : BaseActivity<ActLoginBinding>() {

    private val viewModel: LoginViewModel by inject()
    private val pref : PrefsManager by inject()
    override fun setBinding(inflater: LayoutInflater): ActLoginBinding =
        ActLoginBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setViews()
        setupListener()
    }
    fun setViews(){
    // ユーザIDとパスワードが必須項目
        binding?.edituserId!!
        binding?.editpassword!!
    }
    fun setupListener(){
        //loginボタンを押すと
        binding?.btnLogin?.setOnClickListener {
            viewModel.login(binding?.edituserId?.text.toString(),binding?.editpassword?.text.toString())
            showProgress()
        }
        viewModel.resources.observe(this, {
            hiddenProgress()
                if(it.data?.status.equals("success")){//成功の場合
                    //ユーザ情報とJWTを保存する
                    val user : User? = it.data
                    if (user != null) {
                        pref.saveUser(user)
                    }
                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)// 一覧画面に遷移する
                }
                else {//失敗の場合
                    binding?.message?.text="ログインが失敗しました。"
                }

        })
    }
}