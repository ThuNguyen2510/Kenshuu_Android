package com.example.kenshuu.ui.user.update

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kenshuu.databinding.ActUpdateUserBinding
import com.example.kenshuu.model.DtUser
import com.example.kenshuu.model.Gender
import com.example.kenshuu.model.Role
import com.example.kenshuu.ui.base.BaseActivity
import com.example.kenshuu.ui.base.OnSwipeTouchListener
import com.example.kenshuu.ui.login.LoginActivity
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.ui.slider.SliderViewModel
import com.example.kenshuu.ui.success.SuccessActivity
import com.example.kenshuu.utils.PrefsManager
import kotlinx.android.synthetic.main.act_create_user.*
import kotlinx.android.synthetic.main.act_create_user.spnAuthorityName
import kotlinx.android.synthetic.main.act_create_user.spnGender
import kotlinx.android.synthetic.main.act_update_user.*
import org.koin.android.ext.android.inject

class UpdateUserActivity : BaseActivity<ActUpdateUserBinding>() {
    private val viewModel: UpdateUserViewModel by inject()
    private val viewModel2: SliderViewModel by inject()
    private val pref: PrefsManager by inject()
    var user: DtUser = DtUser()
    override fun setBinding(inflater: LayoutInflater): ActUpdateUserBinding =
        ActUpdateUserBinding.inflate(inflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupViews()
        setupData()
        setupListener()
        setSwipe()
        countDownTimer.start()
    }

    private fun setSwipe() {
        layout = binding?.updateUserContent!!
        layout.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeDown() {
                super.onSwipeDown()
                binding?.tvError?.text=""
                roles.clear()
                genders.clear()
                setupData()
                Toast.makeText(this@UpdateUserActivity, "????????????????????????????????????????????????", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent: Intent = Intent(this@UpdateUserActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setupViews() {
        binding?.toolbar?.btnDrawer?.visibility = View.INVISIBLE//??????????????????????????????????????????
        setTitle("??????")
    }

    private fun setupData() {
        val bundle: Bundle? = intent.getBundleExtra("myBundle")
        user = bundle?.getParcelable<DtUser>("selectedUser") as DtUser//??????????????????
        binding?.edtUserId?.setText(user.userId)
        binding?.edtPassword?.setText(user.password)
        binding?.edtFirstName?.setText(user.firstName)
        binding?.edtFamilyName?.setText(user.familyName)
        if (user.age != -1) binding?.edtAge?.setText(user.age.toString())
        val role: String = user.role?.authorityName?.trim().toString()
        val gender: String = user.gender?.genderName?.trim().toString()
        if (user.admin == 1) binding?.cbAdmin?.isChecked = true
        viewModel.queryAllRole(pref.getToken().toString())//???????????????
        roles.add("")
        viewModel.roles.observe(this,
            {
                if (it.data != null) {
                    for (i in 0 until it.data.size) {
                        val role: Role = it.data.get(i)
                        roles.add(role.authorityName.toString())
                        hashMapRole.put(role.authorityId, role.authorityName.toString())
                    }
                    val adapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            this,
                            R.layout.simple_spinner_item,
                            roles as List<Any?>
                        )
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding?.spnAuthorityName?.adapter = adapter//????????????select???????????????
                    var indexRole: Int = 0
                    for (i in 0 until binding?.spnAuthorityName?.adapter?.count!!.toInt()) {
                        if (binding?.spnAuthorityName?.getItemAtPosition(i)?.equals(role) == true) {
                            indexRole = i
                        }
                    }
                    spnAuthorityName.setSelection(indexRole)//?????????????????????
                }
            })

        viewModel.queryAllGender(pref.getToken().toString())//???????????????
        genders.add("")
        viewModel.genders.observe(this,
            {
                if (it.data != null) {
                    for (i in 0 until it.data.size) {
                        val gender: Gender = it.data.get(i)
                        genders.add(gender.genderName.toString())
                        hashMapGender.put(gender.genderId, gender.genderName.toString())
                    }
                    val adapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            this,
                            R.layout.simple_spinner_item,
                            genders as List<Any?>
                        )
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding?.spnGender?.adapter = adapter//?????????select???????????????
                    var indexGender: Int = 0
                    for (i in 0 until binding?.spnGender?.adapter?.count!!.toInt()) {
                        if (binding?.spnGender?.getItemAtPosition(i)?.equals(gender) == true) {
                            indexGender = i
                        }
                    }
                    binding?.spnGender?.setSelection(indexGender)//?????????????????????
                }
            })
    }

    private fun setupListener() {
        binding?.btnReturn?.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)//???????????????????????????
        }
        binding?.btnUpdate?.setOnClickListener {
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
                authorityId = getAuthorityId(
                    binding?.spnAuthorityName?.getItemAtPosition(spnAuthorityName.selectedItemPosition)
                        .toString()
                ),
                updateUserId = pref.getUserId(),
                genderId = getGenderId(
                    binding?.spnGender?.getItemAtPosition(spnGender.selectedItemPosition).toString()
                )
            )
            //??????????????????
            if (user.password == "" || user.familyName == "" || user.firstName == "") {
                if (user.password == "") {
                    binding?.edtPassword?.setError("????????????????????????????????????")
                    binding?.edtPassword?.requestFocus()
                }
                if (user.familyName == "") {
                    binding?.edtFamilyName?.setError("????????????????????????")
                    binding?.edtFamilyName?.requestFocus()
                }
                if (user.firstName == "") {
                    binding?.edtFirstName?.setError("????????????????????????")
                    binding?.edtFirstName?.requestFocus()
                }
            } else {
                viewModel.updateUser(pref.getToken().toString(), user)//????????????
                viewModel.flag.observe(this, {
                    if (it.data?.message.toString() != "") {//?????????????????????????????????????????????????????????
                        binding?.tvError?.text = it.data?.message
                    } else {//??????
                        if (it.data?.status.equals("not_found")) {
                            binding?.tvError?.text = "????????????????????????????????????"//???????????????????????????
                        } else if (it.data?.status.equals("fail")) {
                            binding?.tvError?.text = "??????????????????????????????"//???????????????????????????????????????
                        } else {//??????
                            val intent: Intent = Intent(this, SuccessActivity::class.java)
                            intent.putExtra("message", "????????????")
                            startActivity(intent)//???????????????????????????
                        }
                    }
                })
            }
        }
        binding?.run {
            edtUserId.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//?????????ID?????????????????????????????????
                    if (edtUserId?.text.toString().length == 0) {
                        edtUserId.setError("?????????ID?????????????????????");
                    }
                }
            }
            edtPassword.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//????????????????????????????????????????????????
                    if (edtPassword?.text.toString().length == 0) {
                        edtPassword.setError("????????????????????????????????????")
                    }
                }
            }
            edtFamilyName.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//????????????????????????????????????
                    if (edtFamilyName?.text.toString().length == 0) {
                        edtFamilyName.setError("????????????????????????");
                    }
                }
            }
            edtFirstName.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {//????????????????????????????????????
                    if (edtFirstName?.text.toString().length == 0) {
                        edtFirstName.setError("????????????????????????")
                    }
                }
            }
            spnGender.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    hideKeyboard()
                }
            }
            spnAuthorityName.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    hideKeyboard()
                }
            }
        }
    }
}