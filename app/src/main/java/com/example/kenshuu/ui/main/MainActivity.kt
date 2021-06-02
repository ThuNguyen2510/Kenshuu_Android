package com.example.kenshuu.ui.main

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import com.example.kenshuu.adapters.UserAdapter
import com.example.kenshuu.databinding.ActivityMainBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Role
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.user.read.ViewUserActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frag_slider.*
import kotlinx.android.synthetic.main.user_record.*
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by inject()
    private val pref: PrefsManager by inject()
    override fun setBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    var users: ArrayList<DtUser> = ArrayList()
    var roles = mutableListOf<String>()
    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupData()
        setupListener()

    }

    fun setupViews() {
        binding?.toolbar?.cslToolbar?.visibility = View.VISIBLE //メニューボタンを表示する
        setTitle("一覧")
    }

    fun setupListener() {
        binding?.btnSearch?.setOnClickListener {
            users.clear()
            val user: DtUser = DtUser(
                familyName = edtFamilyName.text.toString(),
                firstName = edtFirstName.text.toString(),
                authorityId = spnAuthority.selectedItemPosition
            )
            viewModel.search(pref.getToken().toString(), user)
        }

    }

    fun setupData() {
        viewModel.queryAllUser(pref.getToken().toString())//全てのデータを取る
        viewModel.users.observe(this, {
            if (it.data != null) {
                val size: Int = it.data?.size
                if (size == 0) {//結果がない
                    binding?.tvmessage?.text = "ユーザが見つかりませんでした。"
                } else {//結果がある
                    binding?.tvmessage?.text =""
                    for (i in 0 until size) {
                        users.add(it.data.get(i))
                    }
                    binding?.listuser?.adapter =
                        UserAdapter(this@MainActivity, users)//データをListViewに表示する
                }

            }
        })
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
                    spnAuthority.adapter = adapter//役職名をselectに表示する
                }
            })
        binding?.listuser?.setOnItemClickListener { parent, view, position, id ->
            val user: DtUser = parent.getItemAtPosition(position) as DtUser //指定行のユーザを取る
            val b = Bundle()
            b.putParcelable("selectedUser", user)
            val intent: Intent= Intent(this,ViewUserActivity::class.java)
            intent.putExtra("myBundle",b)//ユーザを保持して転送する
            startActivity(intent)//ユーザの詳細画面に遷移する
        }
    }

    fun closeDrawer() {
        binding?.activityMainDrawer?.closeDrawers()
    }

    fun openDrawer(v: View) {
        binding?.activityMainDrawer?.openDrawer(GravityCompat.START)
    }


}