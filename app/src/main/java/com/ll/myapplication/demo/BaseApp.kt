package com.ll.myapplication.demo

import com.blankj.utilcode.util.LogUtils

/**
 * @Author: ll
 * @CreateTime: 2022/04/26 11:40
 */
abstract class BaseApp : Sout {
    companion object {
        @JvmStatic
        lateinit var application: BaseApp
    }

    init {
        application = this
    }

    override fun sout() {
        LogUtils.d("BaseApp")
    }
}