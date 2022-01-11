package com.ll.myapplication.ui.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.R
import com.ll.myapplication.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCoroutineBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            //main
            //没设置 纯纯的主线程
            LogUtils.d("1")

            //DefaultDispatcher-worker-2
            //那肯定是子线程了
            val a = async(Dispatchers.IO) {
                delay(1000)
                LogUtils.d("async")
                "async"
            }

            // DefaultDispatcher-worker-3
            //那肯定是子线程了
            launch(Dispatchers.IO) {
                Thread.sleep(2000)
                LogUtils.d("5")
            }

            // DefaultDispatcher-worker-3
            //那肯定是子线程了
            launch(Dispatchers.IO) {
                delay(2000)
                LogUtils.d("6")
            }

            //DefaultDispatcher-worker-4
            //那肯定是子线程了
            launch(Dispatchers.Default) {
                LogUtils.d("2")
            }

            //DefaultDispatcher-worker-5
            //那肯定是子线程了
            launch(Dispatchers.IO) {
                LogUtils.d("3" + su())
            }

            //main
            launch {
                //纯纯的主线程
                LogUtils.d("4" + a.await())
            }
        }
    }

    //DefaultDispatcher-worker-5
    //分配了一个默认线程 并不在主线程当中
    private suspend fun su(): String {
        delay(1000)
        LogUtils.d("suspend")
        return "suspend"
    }
}