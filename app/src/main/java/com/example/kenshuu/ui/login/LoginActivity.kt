package com.example.kenshuu.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.kenshuu.databinding.ActLoginBinding
import com.example.kenshuu.model.User
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.OnSwipeTouchListener
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.act_create_user.*
import kotlinx.android.synthetic.main.act_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActLoginBinding>() {

    private val viewModel: LoginViewModel by inject()
    private val pref: PrefsManager by inject()
    override fun setBinding(inflater: LayoutInflater): ActLoginBinding =
        ActLoginBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setViews()
        setupListener()
        setSwipe()
    }

    private fun setSwipe() {//スワイプを実装
        layout = binding?.loginContent!!
        layout.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                binding?.run {
                    edituserId.text.clear()
                    editpassword.text.clear()
                }
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    fun setViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("ログイン")
        if (pref.getUserName() != null) {//ログインしている場合、ログインを自動的にして、一覧画面にに遷移する
            binding?.edituserId?.setText(pref.getUserId().toString())
            binding?.editpassword?.setText(pref.getPass().toString())
            viewModel.login(pref.getUserId().toString(), pref.getPass().toString())
            binding?.run {
                btnLogin.isEnabled = false //ボタンが無効になっていること
                edituserId.isEnabled = false//入力不可能になる
                editpassword.isEnabled = false//入力不可能になる
            }
        }
    }

    fun setupListener() {
        //loginボタンを押すと
        binding?.btnLogin?.setOnClickListener {
            //入力チェック
            if (binding?.edituserId?.text.toString().length == 0 || binding?.editpassword?.text.toString().length == 0) {
                if (binding?.edituserId?.text.toString().length == 0) {
                    edituserId.setError("ユーザIDが未入力です。")
                    edituserId.requestFocus();
                }
                if (binding?.editpassword?.text.toString().length == 0) {
                    editpassword.setError("パスワードが未入力です。")
                    editpassword.requestFocus();
                }
            } else { //入力が十分の場合
                viewModel.login(
                    binding?.edituserId?.text.toString(),
                    binding?.editpassword?.text.toString()

                )
                binding?.edituserId?.clearFocus()
                binding?.editpassword?.clearFocus()
                showProgress()
            }

        }
        viewModel.resources.observe(this, {
            hiddenProgress()
            if (it.data?.status.equals("success")) {//成功の場合
                val user: User? = it.data
                if (user != null) {
                    //ユーザ情報とJWTを保存する
                    pref.saveUser(user)
                    pref.savePass(binding?.editpassword?.text.toString())
                    pref.saveUserId(binding?.edituserId?.text.toString())
                }
                var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)// 一覧画面に遷移する
            } else {//失敗の場合
                binding?.message?.text = "ログインが失敗しました。"
                btnLogin.isEnabled = true //ボタンが効になっていること
                edituserId.isEnabled = true//入力可能になる
                editpassword.isEnabled = true//入力可能になる
            }

        })

        binding?.editpassword?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick()//完成ボタンのようにする
                hideKeyboard()//キーボードが非表示
                true
            } else {
                false
            }
        }
        binding?.run {
            edituserId.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//ユーザIDをフォーカスアウトする
                    if (edituserId?.text.toString().length == 0) {
                        edituserId.setError("ユーザIDが未入力です。");
                    }
                }
            }
            editpassword.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//パスワードをフォーカスアウトする
                    if (editpassword?.text.toString().length == 0) {
                        editpassword.setError("パスワードが未入力です。")
                    }
                }
            }
        }
    }


}