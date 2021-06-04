package com.example.kenshuu.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewbinding.ViewBinding
import com.example.kenshuu.R
import com.example.kenshuu.ui.main.MainActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.android.synthetic.main.user_record.*


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val progress by lazy { findViewById<ConstraintLayout>(R.id.cslProgress)!! }//progress_view　を取る
    val toolbar by lazy{ findViewById<Toolbar>(R.id.toolbar)!!}// toolbar
    var hashMapRole: HashMap<Int, String> = HashMap<Int, String>()
    var hashMapGender: HashMap<Int, String> = HashMap<Int, String>()
    var roles = mutableListOf<String>()
    var genders = mutableListOf<String>()
    protected open var binding: T? = null
    lateinit var layout: RelativeLayout
    protected abstract fun setBinding(inflater: LayoutInflater): T
    protected abstract fun onViewReady(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)//画面を回転しても現在の画面のデータのままです
        binding = this.setBinding(layoutInflater)
        setContentView(binding?.root)
        onViewReady(savedInstanceState)
        toolbar.visibility= View.VISIBLE
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
     fun setTitle(title: String) {//画面のタイトルを設定する
        toolbar.tvTitle.text= title
    }

    fun getAuthorityId(authorityName: String): Int {//役職IDを取る
        var id: Int = 0
        for (key in hashMapRole.keys) {
            if (hashMapRole[key].equals(authorityName))
                id = key
        }
        return id
    }

    fun getGenderId(gender: String): Int {//性別IDを取る
        var id: Int = 0
        for (key in hashMapGender.keys) {
            if (hashMapGender[key].equals(gender))
                id = key
        }
        return id
    }
     fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}