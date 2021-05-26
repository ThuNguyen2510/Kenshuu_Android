package com.example.kenshuu.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract  class BaseFragment <T : ViewBinding, A : Any> : Fragment(){
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected open var binding: T? = null

    protected open var handler: A? = null //It's base activity

    protected abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    private val TAG = "__BaseFragmentActivity"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        this.handler = this.activity as? A
        Log.d(TAG, "onAttach: OK")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = this.setBinding(inflater, container)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}