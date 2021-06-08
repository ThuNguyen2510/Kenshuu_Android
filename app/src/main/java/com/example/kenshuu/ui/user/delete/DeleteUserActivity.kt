package com.example.kenshuu.ui.user.delete

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.kenshuu.databinding.ActDeleteUserBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.OnSwipeTouchListener
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.success.SuccessActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.act_delete_user.*
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject

class DeleteUserActivity : BaseActivity<ActDeleteUserBinding>() {
    private val pref: PrefsManager by inject()
    private val viewModel: DeleteUserViewModel by inject()
    var user: DtUser = DtUser()
    override fun setBinding(inflater: LayoutInflater): ActDeleteUserBinding =
        ActDeleteUserBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupData()
        setupListener()
        setSwipe()
    }

    private fun setSwipe() {
        layout = binding?.deleteUserContent!!
        layout.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                setupData()
                Toast.makeText(this@DeleteUserActivity, "最新のデータが更新されています。", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent: Intent = Intent(this@DeleteUserActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)//一覧画面に遷移する
        }
        binding?.btnDelete?.setOnClickListener {
            if (user.userId != pref.getUserId()) {
                viewModel.deleteUser(pref.getToken().toString(), user)
                viewModel.flag.observe(this, {
                    if (it.data?.message.equals("not_found")) {
                        tvmessage.text = "ユーザが見つかりません。"//ユーザが存在していない
                    } else if (it.data?.status.equals("fail")) {
                        tvmessage.text = "削除が失敗しました。"//失敗
                    } else {//成功
                        val intent: Intent = Intent(this, SuccessActivity::class.java)
                        intent.putExtra("message", "削除完了")
                        startActivity(intent)//完了画面に遷移する
                    }
                })
            } else {
                Toast.makeText(
                    this@DeleteUserActivity,
                    "ユーザがログインしてますので、削除できません。",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("削除")
    }

    private fun setupData() {
        val bundle: Bundle? = intent.getBundleExtra("myBundle")
        user = bundle?.getParcelable<DtUser>("selectedUser") as DtUser//ユーザを取る
        binding?.run {
            tvUserId.text = user.userId
            tvFullName.text = user.familyName + "  " + user.firstName
        }
    }
}