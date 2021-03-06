package com.ll.myapplication.ui

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.databinding.ActivityHandlerBinding

//Looper类用来为一个线程开启一个消息循环 一个线程只能有一个Looper，对应一个MessageQueue。
//子线程中当前Looper为空    默认情况下android中新诞生的线程是没有开启消息循环的。（主线程除外，主线程系统会自动为其创建Looper对象，开启消息循环。）
//HandlerThread.looper等价于在子线程中先使用Looper.prepare
//Handler参数为空会默认获取当前looper
//Looper类用来为一个线程开启一个消息循环
//Looper.prepare 启用Looper
//Looper.loop 让Looper开始工作，从消息队列里取消息，处理消息。
class HandlerActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHandlerBinding.inflate(layoutInflater) }

//    private val handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            when (msg.what) {
//                1 -> {
//                    LogUtils.d("thread" + "1" + Looper.myLooper())
//                }
//                2 -> {
//                    LogUtils.d("thread" + "2" + Looper.myLooper())
//                }
//                else -> {}
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val handler = HandlerThread("myHandlerThread")
        handler.start()

        val mainHandler = object :Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                LogUtils.d(Looper.myLooper() == Looper.getMainLooper())

                binding.tvTitle.text = "已得值"
            }
        }


        val threadHandler = object : Handler(handler.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                LogUtils.d(Looper.myLooper() == Looper.getMainLooper())

                mainHandler.sendEmptyMessage(1)
            }
        }

        threadHandler.post {
            LogUtils.d(Looper.myLooper() == Looper.getMainLooper())
            Thread.sleep(3000)
            threadHandler.sendEmptyMessage(1)
        }

//        LogUtils.d("thread" + Looper.myLooper())
//        thread {
//            LogUtils.d("threada" + Looper.myLooper())
//            handler.sendEmptyMessageDelayed(1,2000)
//            Thread.sleep(1000)
//            handler.sendEmptyMessage(2)
//        }
    }
}