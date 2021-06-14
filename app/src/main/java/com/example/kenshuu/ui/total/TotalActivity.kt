package com.example.kenshuu.ui.total

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.kenshuu.adapters.CountAdapter
import com.example.kenshuu.adapters.UserAdapter
import com.example.kenshuu.databinding.ActLoginBinding
import com.example.kenshuu.databinding.ActTotalBinding
import com.example.kenshuu.model.Count
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.User
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.OnSwipeTouchListener
import com.example.kenshuu.ui.login.LoginViewModel
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.act_total.*
import org.koin.android.ext.android.inject
import java.util.ArrayList

class TotalActivity : BaseActivity<ActTotalBinding>() {
    private val viewModel: TotalViewModel by inject()
    private val pref: PrefsManager by inject()
    override fun setBinding(inflater: LayoutInflater): ActTotalBinding =
        ActTotalBinding.inflate(inflater)

    var counts: ArrayList<Count> = ArrayList()

    override fun onViewReady(savedInstanceState: Bundle?) {
        setViews()
        setUpdata()
        setupListener()
        setSwipe()
        countDownTimer.start()
    }

    private fun setSwipe() {
        layout = binding?.totalContent!!
        layout.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                tvmessage.text = "最新のデータが集計されています。"
                counts.clear()
                viewModel.getTotal(pref.getToken().toString())//全てのデータを取る
                Toast.makeText(this@TotalActivity, "最新のデータが更新されています。", Toast.LENGTH_LONG)
                    .show()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent: Intent = Intent(this@TotalActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    fun setViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("集計")
    }

    fun setUpdata() {
        viewModel.getTotal(pref.getToken().toString())
        viewModel.resources.observe(this, {
            if (it.data != null) {
                for (i in 0 until it.data.size) {
                    counts.add(it.data.get(i))
                }
                binding?.lvtotal?.adapter =
                    CountAdapter(this@TotalActivity, counts)//データをListViewに表示する
            }
        }
        )

    }

    fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding?.btnCount?.setOnClickListener {
            counts.clear()
            viewModel.getTotal(pref.getToken().toString())
            tvmessage.text = "最新のデータが集計されています。"
        }
    }
}