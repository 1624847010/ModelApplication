package com.ll.myapplication.ui.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author: ll
 * @CreateTime: 2022/03/11 15:32
 */
class FlowViewModel : ViewModel() {

    //1个缓冲值
    val channel = Channel<Int>(Channel.BUFFERED)
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


    val flow1 = flow {
        delay(2000)
        emit(1)
        delay(2000)
        emit(2)
        delay(2000)
        emit(3)
    }

    val flow2 = (1..5).asFlow().take(3)

    val flow3 = flow2.transformLatest { v ->
        flow1.map {
            emit(v == it)
        }
    }

    val flow4 = flow2.flatMapLatest { v ->
        flow1.map {
            v == it
        }
    }

    val flow5 = flow2.mapLatest { v ->
        var b = false
        flow1.map {
            b = v == it
        }
        val a = flow2.collect {
            b = v == it
        }
        b
    }

    val flow6 = flow2.zip(flow1) { t1, t2 ->
        t1 == t2
    }

    val flow12 = combine(flow1, flow2) { t1, t2 ->
        t1 == t2
    }

    val flow13 = combineTransform(flow1, flow2) { t1, t2 ->
        emit(t1 == t2)
    }

    //每当上游流产生值的时候都需要重新计算
    val flow7 = flow2.combine(flow1) { t1, t2 ->
        t1 == t2
    }

    //它们在等待内部流完成之前开始收集下一个值
    val flow8 = flow1.flatMapConcat { t1 ->
        flow2.map { t2 ->
            t1 == t2
        }
    }

    //并发收集所有传入的流，并将它们的值合并到一个单独的流，以便尽快的发射值
    val flow9 = flow1.flatMapMerge { t1 ->
        flow2.map { t2 ->
            t1 == t2
        }
    }

    //8的换序
    val flow10 = flow2.flatMapConcat { t1 ->
        flow1.map { t2 ->
            t1 == t2
        }
    }

    //9的换序
    val flow11 = flow2.flatMapMerge { t1 ->
        flow1.map { t2 ->
            t1 == t2
        }
    }
}