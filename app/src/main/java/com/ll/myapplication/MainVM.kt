package com.ll.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/05/25 09:21
 */
class MainVM : ViewModel() {
    init {
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
                        if (tag){
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
        }

        flow {
            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
            }
            emit(5)
        }
            .onEach { LogUtils.d(it) }
            .launchIn(viewModelScope)
    }

    fun aFunction() {
        flow {
            emit(6)
        }.onEach {
            LogUtils.d(it)
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}