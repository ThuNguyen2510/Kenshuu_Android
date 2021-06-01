package com.example.kenshuu.ui.user.create

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.example.kenshuu.databinding.ActCreateUserBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Gender
import com.example.kenshuu.model.Role
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.success.SuccessActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.act_create_user.*
import org.koin.android.ext.android.inject

class CreateUserActivity : BaseActivity<ActCreateUserBinding>() {
    override fun setBinding(inflater: LayoutInflater): ActCreateUserBinding =
        ActCreateUserBinding.inflate(inflater)

    private val viewModel: CreateUserViewModel by inject()
    private val pref: PrefsManager by inject()
    var roles = mutableListOf<String>()
    var genders = mutableListOf<String>()
    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupData()
        setupListener()
    }

    private fun setupData() {
        viewModel.queryAllRole(pref.getToken().toString())//役職を取る
        roles.add("")
        viewModel.roles.observe(this,
            {
                if (it.data != null) {
                    for (i in 0 until it.data.size) {
                        val role: Role = it.data.get(i)
                        roles.add(role.authorityName.toString())
                    }
                    val adapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            this,
                            R.layout.simple_spinner_item,
                            roles as List<Any?>
                        )
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    spnAuthorityName.adapter = adapter//役職名をselectに表示する
                }
            })

        viewModel.queryAllGender(pref.getToken().toString())//役職を取る
        genders.add("")
        viewModel.genders.observe(this,
            {
                if (it.data != null) {
                    for (i in 0 until it.data.size) {
                        val gender: Gender = it.data.get(i)
                        genders.add(gender.genderName.toString())
                    }
                    val adapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            this,
                            R.layout.simple_spinner_item,
                            genders as List<Any?>
                        )
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    spnGender.adapter = adapter//役職名をselectに表示する
                }
            })

    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//メニューボタンが非表示される
        setTitle("登録")
    }

    private fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding?.btnCreate?.setOnClickListener {
            var age: Int = 0
            if (binding?.edtAge?.text.toString() != "") age =
                binding?.edtAge?.text.toString().toInt()
            var admin: Int = 0
            if (binding?.cbAdmin?.isChecked == true) admin = 1
            val user: DtUser = DtUser(
                userId = binding?.edtUserId?.text.toString(),
                password = binding?.edtPassword?.text.toString(),
                familyName = binding?.edtFamilyName?.text.toString(),
                firstName = binding?.edtFirstName?.text.toString(),
                age,
                admin,
                authorityId = spnAuthorityName.selectedItemPosition,
                createUserId = pref.getUserId(),
                genderId = spnGender.selectedItemPosition
            )
            //入力チェック
            if (user.userId == "" || user.password == "" || user.familyName == "" || user.firstName == "") {
                if (user.userId == "") {
                    edtUserId.setError("ユーザIDが未入力です。")
                    edtUserId.requestFocus();
                }
                Log.d("AAA", user.familyName.toString())
                if (user.password == "") {
                    edtPassword.setError("パスワードが未入力です。")
                    edtPassword.requestFocus()
                }
                if (user.familyName == "") {
                    edtFamilyName.setError("姓が未入力です。")
                    edtFamilyName.requestFocus()
                }
                if (user.firstName == "") {
                    edtFirstName.setError("名が未入力です。")
                    edtFirstName.requestFocus()
                }
            } else {
                viewModel.createUser(pref.getToken().toString(), user)//登録する
                viewModel.flag.observe(this, {
                    if (it.data?.message.toString() != "") {
                        tvError.text = it.data?.message//入力チェックしてから、エラーが発生する
                    } else {
                        if (it.data?.status.equals("duplicate_user")) {
                            tvError.text = "ユーザIDが重複しています。"//録済みのユーザID
                        } else if (it.data?.status.equals("fail")) {
                            tvError.text = "登録が失敗しました。"//システムのせいかもしれない
                        } else {//成功
                            val intent: Intent=Intent(this,SuccessActivity::class.java)
                            intent.putExtra("message","登録完了")
                            startActivity(intent)//完了画面に遷移する
                        }
                    }
                })
            }
        }

    }

}