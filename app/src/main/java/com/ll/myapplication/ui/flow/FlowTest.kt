package com.ll.myapplication.ui.flow

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/08/16 18:45
 */
object FlowTest {

    suspend fun B() = coroutineScope {
        LogUtils.d(
            (0..3).map {
                async {
                    A(it).first()
                }
            }.sumOf {
                it.await()
            }
        )

        flowOf(1).sample(1000)
//        val a = async {
//            A(1)
//        }
//
//        val b = async {
//            A(3)
//        }
//
//        launch {
//            A(4)
//            cancel()
//        }
//
//        val c = async {
//            A(5)
//        }
//
//        LogUtils.d(a.await()+b.await()+c.await())
    }

    suspend fun A(i: Int) = channelFlow {
        delay(i * 1000L)
        LogUtils.d("A====>$i")
        trySendBlocking(i)
    }

    fun A(){
        val map = HashMap<String,Any>()
        map["a"] = "a"
        map["b"] = "b"
        val a by map // 会找出map中同名key取value
        LogUtils.d(a)
        map["a"] = 1
        LogUtils.d(a)
    }
}