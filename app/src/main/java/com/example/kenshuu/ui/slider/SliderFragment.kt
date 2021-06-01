package com.example.kenshuu.ui.slider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenshuu.R
import com.example.kenshuu.databinding.FragSliderBinding
import com.example.kenshuu.ui.base.BaseFragment
import com.example.kenshuu.ui.login.LoginActivity
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.total.TotalActivity
import com.example.kenshuu.ui.user.create.CreateUserActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.frag_slider.*
import org.koin.android.ext.android.inject

class SliderFragment : BaseFragment<FragSliderBinding, MainActivity>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragSliderBinding = FragSliderBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }
    private val pref : PrefsManager by inject()
    private val viewModel : SliderViewModel by inject()

    fun setupViews() {
        binding?.run {
            drawerHeader.tvUserName.text = pref.getUserName()
            lnTotal.setOnClickListener{
                val intent: Intent = Intent(this@SliderFragment.context,TotalActivity::class.java)
                startActivity(intent)//集計画面に遷移する
            }
            lnLogout.setOnClickListener{
                pref.clearUser()//ログインしているユーザを削除する
                viewModel.logout()//ログアウト
                viewModel.flag.observe(this@SliderFragment,{
                    if(it.data.equals("logout_success")){
                        val intent: Intent = Intent(this@SliderFragment.context,LoginActivity::class.java)
                        startActivity(intent)//ログイン画面に遷移する
                    }
                })

            }
            lnCreate.setOnClickListener{
                val intent: Intent=Intent(this@SliderFragment.context,CreateUserActivity::class.java)
                startActivity(intent)//登録画面に遷移する

            }
        }

    }
}