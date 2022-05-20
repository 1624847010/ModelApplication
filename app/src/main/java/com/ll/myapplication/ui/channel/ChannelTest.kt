package com.ll.myapplication.ui.channel

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
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
                LogUtils.d( receive())
            }
            delay(1000)
            actor.send("actor")
            actor.close()
        }
        //BroadcastChannel
        launch {
            val broadChannel = Channel<String>().broadcast(3)

            launch {
                repeat(4){
                    delay(1000)
                    broadChannel.send("第${it.inc()}次发送")
                }
            }

            repeat(5){
                launch {
                    for(v in broadChannel.openSubscription()){
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
        val channel = listOf(
            Channel<Int>(),
            Channel<Int>(),
            Channel<Int>()
        )
        launch {
            repeat(3) {
                launch {
                    delay(1000L * it)
                    channel[it].send(1 * it)
                }
            }
        }


        //channelselect
        launch {
            val result = select<Int> {
                channel.forEach {
                    it.onReceive{
                        it
                    }
                }
            }
            LogUtils.d(result)
        }

        //flowselect
        launch {
            listOf(af(),bf())
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
}