package com.ll.myapplication.demo

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/04/26 11:44
 */
class App : BaseApp() {

    override fun sout() {
        super.sout()
        LogUtils.d("App")
    }
}