package com.ll.myapplication.ui.databinding

import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @Author: ll
 * @CreateTime: 2021/11/23 11:42
 */
class DatabindingModel : ViewModel() {
    val et = MutableStateFlow("早上好")

    val tv = MutableStateFlow(1)

    val onTextChanged = TextViewBindingAdapter.OnTextChanged { s, start, before, count ->
        et.value = s.toString()
    }

    val onTextChanged2 = InverseBindingListener {
        LogUtils.d("onTextChanged2")
    }
}