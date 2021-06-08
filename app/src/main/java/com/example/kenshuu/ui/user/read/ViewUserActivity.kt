package com.example.kenshuu.ui.user.read

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.kenshuu.databinding.ActViewUserBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.OnSwipeTouchListener
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.user.delete.DeleteUserActivity
import com.example.kenshuu.ui.user.update.UpdateUserActivity
import org.koin.android.ext.android.bind

class ViewUserActivity : BaseActivity<ActViewUserBinding>() {
    var user: DtUser = DtUser()
    override fun setBinding(inflater: LayoutInflater): ActViewUserBinding =
        ActViewUserBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupData()
        setupListener()
        setSwipe()
    }

    private fun setSwipe() {
        layout = binding?.viewUserContent!!
        layout.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                setupData()
                Toast.makeText(this@ViewUserActivity, "最新のデータが更新されています。", Toast.LENGTH_LONG)
                    .show()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent: Intent = Intent(this@ViewUserActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("詳細")
    }

    private fun setupData() {
        val bundle: Bundle? = intent.getBundleExtra("myBundle")
        user = bundle?.getParcelable<DtUser>("selectedUser") as DtUser
        binding?.run {
            tvUserId.text = user.userId
            tvPassword.text = user.password
            tvFamilyName.text = user.familyName
            tvFirstName.text = user.firstName
            if (user.age != -1) tvAge.text = user.age.toString()
            tvAuthorityName.text = user.role?.authorityName
            tvGender.text = user.gender?.genderName?.trim()
            if (user.admin == 1) tvAdmin.text = "X"
        }
    }

    private fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)//一覧画面に遷移する
        }
        binding?.btnDelete?.setOnClickListener {
            val intent: Intent = Intent(this, DeleteUserActivity::class.java)
            val b = Bundle()
            b.putParcelable("selectedUser", user)
            intent.putExtra("myBundle",b)
            startActivity(intent)//削除画面に遷移する
        }
        binding?.btnEdit?.setOnClickListener {
            val intent: Intent = Intent(this, UpdateUserActivity::class.java)
            val b = Bundle()
            b.putParcelable("selectedUser", user)
            intent.putExtra("myBundle",b)
            startActivity(intent)//更新画面に遷移する
        }

    }

}