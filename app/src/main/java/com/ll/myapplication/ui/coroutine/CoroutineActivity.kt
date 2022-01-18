package com.ll.myapplication.ui.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.*

/**
 * runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
 * 主要区别在于，runBlocking 方法会阻塞当前线程来等待， 而 coroutineScope 只是挂起，会释放底层线程用于其他用途。
 * 由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数。
 */
@ObsoleteCoroutinesApi
class CoroutineActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCoroutineBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {

            //DefaultDispatcher-worker-2
            //那肯定是子线程了
            val a = async(Dispatchers.IO) {
                delay(1000)
                LogUtils.d("async")
                "async"
            }

            //main
            //没设置 主线程
            LogUtils.d("1")

            //DefaultDispatcher-worker-4
            //子线程
            //将会获取默认调度器
            launch(Dispatchers.Default) {
                LogUtils.d("2")
            }

            //DefaultDispatcher-worker-5
            //子线程
            //调用job.join() 则会在调起位置等待该携程执行完成
            //倘若没有调用job.join() 则会正常顺序执行
            //调用job.cancel() 则会取消该携程(连同其中的挂起一并取消)
            val job = launch(Dispatchers.IO) {
                LogUtils.d("3" + su())
            }

            //main 运行在父协程的上下文中，即 主协程
            //虽然这条 4 只需要1s执行 小于 3 的1.5秒 但需要等待job结束
            //由于3的挂起执行需要1.5s await(1s)仍会先执行
            launch {
                //主线程
                job.join()
                LogUtils.d("4" + a.await())
            }

            // DefaultDispatcher-worker-3
            // 将使它获得一个新的线程
            launch(newSingleThreadContext("MyOwnThread")) {
//            launch(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
                delay(2000)
                LogUtils.d("5")
            }

            // DefaultDispatcher-worker-3
            //子线程
            launch(Dispatchers.IO) {
                Thread.sleep(2000)
                LogUtils.d("6")
            }

            //kotlinx.coroutines.DefaultExecutor
            //不受限的
            launch(Dispatchers.Unconfined) {
                delay(3000)
                LogUtils.d("7")
            }
        }
    }

    //DefaultDispatcher-worker-5
    //分配了一个默认线程 并不在主线程当中
    private suspend fun su(): String {
        delay(1500)
        LogUtils.d("suspend")
        return "suspend"
    }
}