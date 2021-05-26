package com.example.kenshuu.ui.slider

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenshuu.databinding.FragSliderBinding
import com.example.kenshuu.ui.base.BaseFragment
import com.example.kenshuu.ui.main.MainActivity
import com.example.kenshuu.utils.PrefsManager
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
    private val TAG = "__SliderFragment"
    private val viewModel : SliderViewModel by inject()

    fun setupViews() {
        Log.d(TAG, "setupViews: ")

        binding?.run {
            drawerHeader.tvUserName.text = pref.getUserName()
        }
    }
}