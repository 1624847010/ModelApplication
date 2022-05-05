package com.ll.myapplication.ui.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.R
import com.ll.myapplication.databinding.ActivityCoroutine2Binding
import com.ll.myapplication.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume

class Coroutine2Activity : AppCompatActivity() {
    private val binding by lazy { ActivityCoroutine2Binding.inflate(layoutInflater) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Default).launch {
            LogUtils.d(mapA())


        }
    }

    suspend fun mapA() = suspendCancellableCoroutine<String> { continuation ->
        A {
            continuation.resume(it)
        }
    }

    fun A(func: (String) -> Unit) {
        thread {
            Thread.sleep(1000)
            func("完成")
        }
    }
}