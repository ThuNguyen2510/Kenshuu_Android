package com.example.kenshuu.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.kenshuu.databinding.ActivityMainBinding
import com.example.kenshuu.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity :  BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by inject()
    override fun setBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupListener()
        setupData()
    }
    fun setupViews(){
        binding?.toolbar?.cslToolbar?.visibility= View.VISIBLE //メニューボタンを表示する
        setTitle("一覧")
    }
    fun setupListener(){
        binding?.run {
            navDrawer.setNavigationItemSelectedListener { item: MenuItem ->
                item.isChecked = true
                binding?.activityMainDrawer?.closeDrawers()
                true
            }
        }
    }
    fun setupData(){
    }
    fun closeDrawer() {
        binding?.activityMainDrawer?.closeDrawers()
    }

    fun openDrawer(v: View) {
        binding?.activityMainDrawer?.openDrawer(GravityCompat.START)
    }

}