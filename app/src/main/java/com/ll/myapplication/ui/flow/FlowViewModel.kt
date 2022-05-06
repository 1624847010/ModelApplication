package com.ll.myapplication.ui.flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/03/11 15:32
 */
class FlowViewModel : ViewModel() {

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