package com.example.kenshuu.ui.success

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.kenshuu.databinding.ActCreateUserBinding
import com.example.kenshuu.databinding.ActSuccessBinding
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.main.MainActivity

class SuccessActivity : BaseActivity<ActSuccessBinding>() {
    override fun setBinding(inflater: LayoutInflater): ActSuccessBinding =
        ActSuccessBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupListener()
        countDownTimer.start()
    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        val message: String = intent.getStringExtra("message").toString()
        setTitle(message)
        binding?.tvmessage?.text = message + "しました。"
    }

    private fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}