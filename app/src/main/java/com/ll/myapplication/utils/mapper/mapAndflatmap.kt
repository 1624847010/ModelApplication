package com.ll.myapplication.utils.mapper

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * @Author: ll
 * @CreateTime: 2022/05/24 16:34
 */
@OptIn(FlowPreview::class)
suspend fun main() {
    val list = listOf(listOf(1, 2, 3),listOf(1, 2, 3),listOf(1, 2, 3))

    val list2 = list.flatMap {
        it.map {
            translate(it)
        }
    }

    val list3 = list.flatten().map {
        translate(it)
    }

    val flow = flowOf(flowOf(1,2,3),flowOf(1,2,3))

    val flow1 =flow.flatMapConcat {
        it.map {
            translate(it)
        }
    }
}

fun translate(i:Int) = i.toString()