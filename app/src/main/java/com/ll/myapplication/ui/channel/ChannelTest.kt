package com.ll.myapplication.ui.channel

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select

/**
 * @Author: ll
 * @CreateTime: 2022/05/20 10:13
 */
object ChannelTest {

    sealed class Data {
        override fun toString(): String {
            return when (this) {
                Local -> {
                    Local.javaClass.name
                }
                Remote -> {
                    Remote.javaClass.name
                }
            }
        }
    }

    object Local : Data()
    object Remote : Data()

    @OptIn(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
    suspend fun main() = withContext(Dispatchers.Default) {
        //produce
        launch {
            val produce = produce<String> {
                delay(1000)
                send("produce")
                close()
            }

            LogUtils.d(produce.receive())
        }

        //actor
        launch {
            val actor = actor<String> {
                LogUtils.d(receive())
            }
            delay(1000)
            actor.send("actor")
            actor.close()
        }
        //BroadcastChannel
        launch {
            val broadChannel = Channel<String>().broadcast(3)

            launch {
                repeat(4) {
                    delay(1000)
                    broadChannel.send("第${it.inc()}次发送")
                }
            }

            repeat(5) {
                launch {
                    for (v in broadChannel.openSubscription()) {
                        LogUtils.d("${v}: 第${it}接收")
                    }
                }
            }
        }

        //select
        launch {
            val a = getLocal()
            val b = getRemote()

            val userData = select<Data> {
                a.onAwait {
                    it
                }

                b.onAwait {
                    it
                }
            }

            LogUtils.d(userData.toString())
        }

        //actor
        val channel1 = listOf(
            Channel<Int>(),
            Channel<Int>(),
            Channel<Int>()
        )
        launch {
            repeat(3) {
                launch {
                    delay(1000L * it)
                    channel1[it].send(1 * it)
                }
            }
        }


        //channelselect
        launch {
            val result = select<Int> {
                channel1.forEach {
                    it.onReceive {
                        it
                    }
                }
            }
            LogUtils.d(result)
        }

        //flowselect
        launch {
            listOf(af(), bf())
                .map {
                    flow {
                        emit(it.await())
                    }
                }.merge()
                .collect {
                    LogUtils.d(it)
                }
        }
        val job1 = launch {
            delay(100)
            println("job 1")
        }

        val job2 = launch {
            delay(10)
            println("job 2")
        }

        select<Unit> {
            job1.onJoin { println("job 1 onJoin") }
            job2.onJoin { println("job 2 onJoin") }
        }

    }

    suspend fun main2() = coroutineScope {
        val channel = Channel<Int>()

        launch {
            repeat(3) {
                delay(100)
                channel.send(it)
            }
        }

        //多个接收者
        val a =
            channel.receiveAsFlow()
        //一个接收者
//            channel.consumeAsFlow()

        //1 3
        launch {
            a.collect {
                LogUtils.d("launch1: $it")
            }
        }

        //2
        launch {
            a.collect {
                LogUtils.d("launch2: $it")
            }
        }

    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    suspend fun main3() = coroutineScope {
        val a = flowOf(1, 2, 3).produceIn(this)

        for (i in a) {
            LogUtils.d(i)
        }

        callbackFlow<Int> {
            getApi {
                trySend(it - 1)
                close()
            }


            awaitClose {
                LogUtils.d("awaitClose")
            }
        }.collect {
            LogUtils.d(it)
        }

        channelFlow {
            getApi {
                trySend(it)
            }
        }.collect {
            LogUtils.d(it)
        }
    }

    fun CoroutineScope.getLocal() = async {
        delay(3000)
        Local
    }

    fun CoroutineScope.getRemote() = async {
        Remote
    }

    fun CoroutineScope.af() = async {
        delay(100)
        "a"
    }

    fun CoroutineScope.bf() = async {
        delay(500)
        "b"
    }

    fun getApi(a: MyClick) {
        Thread.sleep(100)
        a.invoke(5)
    }


//    interface MyClick {
//        fun click(i: Int)
//    }
}

typealias MyClick = (i: Int) -> Unit
