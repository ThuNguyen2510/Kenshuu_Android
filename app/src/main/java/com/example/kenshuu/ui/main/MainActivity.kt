package com.example.kenshuu.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.kenshuu.R
import com.example.kenshuu.databinding.ActivityMainBinding
import com.example.kenshuu.repository.user.UserRepository
import com.example.kenshuu.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity :  BaseActivity<ActivityMainBinding>() {
    private val TAG = "__MainActivity"
    private val viewModel: MainViewModel by inject()
    override fun setBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupListener()
        setupData()
    }
    fun setupViews(){
    }
    fun setupListener(){
    }
    fun setupData(){
    }

}