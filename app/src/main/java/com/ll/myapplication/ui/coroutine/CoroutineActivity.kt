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
            LogUtils.d("lifecycleScope")

            val a =async {
                delay(1000)
                1+1
            }

            launch(Dispatchers.IO) {
                delay(2000)
                LogUtils.d("launch1")
            }

            launch(Dispatchers.Default) {
                LogUtils.d("launch2")
            }

            launch {
                LogUtils.d("launch3"+a.await())
            }
        }
    }
}