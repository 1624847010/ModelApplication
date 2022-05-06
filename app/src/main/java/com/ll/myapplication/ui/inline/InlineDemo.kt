package com.ll.myapplication.ui.inline

import com.blankj.utilcode.util.LogUtils
import kotlin.random.Random

/**
 * @Author: ll
 * @CreateTime: 2022/05/06 14:59
 */
object InlineDemo {
    inline fun <reified T> create() {
        val clazzT = T::class.java
    }

    sealed interface Result<out T>
    data class Success<out T>(val msg: T) : Result<T>
    data class Fail(val code: Int, val msg: String) : Result<Nothing>

    fun test() {
        val result: Result<*>? = null

        result?.fail { a, b ->
            LogUtils.d(a, b)
        }

        getResult() success {
            LogUtils.d(
                it.toString()
            )
        } fail { a, b ->
            LogUtils.d(a, b)
        }

//        when (val value = result) {
//            is Success -> {
//                value.msg
//            }
//            is Fail -> {
//                value.code
//                value.msg
//            }
//            else -> {
//
//            }
//        }
    }

    private fun getResult(): Result<*> = if (Random.nextBoolean()) Success("成功") else Fail(404, "请求失败")

    private inline infix fun <reified T> Result<T>.success(b: (T) -> Unit): Result<*> {
        if (this is Success)
            b.invoke(msg)
        return this
    }

    private inline infix fun <reified T> Result<T>.fail(b: (code: Int, msg: String) -> Unit): Result<*> {
        if (this is Fail)
            b.invoke(code, msg)
        return this
    }
}
