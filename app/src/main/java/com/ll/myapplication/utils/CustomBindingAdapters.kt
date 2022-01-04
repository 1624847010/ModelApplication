package com.ll.myapplication.utils

import android.content.Context
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import com.blankj.utilcode.util.LogUtils

/**
 * @Author: ll
 * @CreateTime: 2021/11/24 16:14
 */
object CustomBindingAdapters {

    @InverseMethod("btoA")
    @JvmStatic
    fun atoB(oldv: Int) = oldv.toString()


    @JvmStatic
    fun btoA(oldv: String) = oldv.toInt()

    @BindingAdapter("text2")
    @JvmStatic
    fun setText(t: TextView, s: String) {
        LogUtils.d("setText")
        if (t.text != s)
            t.text = s
    }

    @InverseBindingAdapter(attribute = "text2")
    @JvmStatic
    fun getText(t: TextView): String {
        LogUtils.d("getText")
        return t.text.toString()
    }

    @BindingAdapter("text2AttrChanged", requireAll = false)
    @JvmStatic
    fun setListener(v: TextView, listener: InverseBindingListener?) {
        LogUtils.d("textAttrChanged")
        v.doOnTextChanged { text, start, before, count ->
            LogUtils.d("setListener")
            listener?.onChange()
        }
    }
}