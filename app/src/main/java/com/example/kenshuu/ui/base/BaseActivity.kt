package com.example.kenshuu.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.example.kenshuu.R
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val progress by lazy { findViewById<ConstraintLayout>(R.id.cslProgress)!! }//progress_view　を取る
    protected open var binding: T? = null

    protected abstract fun setBinding(inflater: LayoutInflater): T
    protected abstract fun onViewReady(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = this.setBinding(layoutInflater)
        setContentView(binding?.root)
        onViewReady(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
    fun showProgress() {//表示される
        progress.visibility=View.VISIBLE
    }
    fun hiddenProgress() {//非表示
        progress.visibility=View.INVISIBLE
    }

}