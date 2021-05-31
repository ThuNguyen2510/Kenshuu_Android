package com.example.kenshuu.ui.user.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.kenshuu.databinding.ActCreateUserBinding
import com.example.kenshuu.ui.base.BaseActivity

class CreateUserActivity: BaseActivity<ActCreateUserBinding>() {
    override fun setBinding(inflater: LayoutInflater): ActCreateUserBinding=
        ActCreateUserBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("登録")
    }

}