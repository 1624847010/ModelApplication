package com.ll.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/05/25 09:21
 * 状态（State）用 StateFlow ；事件（Event）用 SharedFlow
 */
class MainVM : ViewModel() {
    //1个缓冲值
    val channel = Channel<Int>(BUFFERED)
    //没有缓冲值 相当于冷流
//    val channel = Channel<Int>()

    //有初始值 不记录每次不变化 true true 则后true不显示
//    val state = MutableStateFlow(true)
    //无初始值 会记录不变化 true true 全部展示 但没有初始值 也无法使用onStart赋予初始值 ->使用onSubscription替代onStart
    //replay为1时与stateflow相似 但无初始值与防抖
    val state = MutableSharedFlow<Boolean>(1)

    init {
//        flow()
        channel
            .consumeAsFlow()
                //伴随viewModelScope开始与结束
            .onStart {
//                channel.send(1)
                LogUtils.d("onStart")
            }
            .onCompletion { LogUtils.d("onCompletion") }
            .onEach {
//                state.value = it % 3 == 0
                state.emit(it % 3 == 0)
            }
//            .onEach { LogUtils.d(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }


    private fun flow() {
        var tag = true

        viewModelScope.launch {
            launch {
                flow {
                    delay(500)
                    emit(0)
                }
                    .flowOn(Dispatchers.Default)
                    .map { it.toString() }
                    .onEach { LogUtils.d("运行") }
                    .onEach {
                        if (tag) {
                            tag = false
                            throw NullPointerException()
                        }
                    }
                    .retry(2) { cause ->
                        cause is NullPointerException
                    }
                    .catch { LogUtils.e(it) }
                    .onCompletion { LogUtils.d("0.5") }
                    .onStart { LogUtils.d("-1") }
                    .collect {
                        LogUtils.d(it)
                    }
            }

            flow {
                Thread.sleep(1000)
                emit(1)
            }
                .flowOn(Dispatchers.Default)
                .collect {
                    LogUtils.d(it)
                }

            flow {
                emit(2)
            }
//                .onEach { Thread.sleep(2000) }
//                .flowOn(Dispatchers.Default)
                .collect { LogUtils.d(it) }

            flow {
                emit(3)
            }
                .onEach { Thread.sleep(2000) }
                .collect { LogUtils.d(it) }

            launch {
                flow {
                    emit(4)
                    LogUtils.d("4")
                }
                    .onEach {
                        LogUtils.d(it)
                    }
                    .flowOn(Dispatchers.Default)
                    .onEach {
                        LogUtils.d(it)
                    }
                    .flowOn(Dispatchers.Main)
                    .onEach {
                        LogUtils.d(it)
                    }
                    .first()
            }

            val a = flow {
                repeat(10){
                    delay(1000)
                    emit(5)
                }
            }

            launch {
                a.collect {
                    LogUtils.d(it)
                }
                LogUtils.d("launch")
            }

            launch {
                a.collect {
                    LogUtils.d(it)
                }
            }
        }

        flow {
            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
            }
            emit(6)
        }
            .onEach { LogUtils.d(it) }
            .launchIn(viewModelScope)
    }
}