package com.ll.myapplication.ui.completablefuture

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.*
import java.util.concurrent.CompletableFuture

/**
 * @Author: ll
 * @CreateTime: 2022/05/06 11:32
 */
@RequiresApi(Build.VERSION_CODES.N)
class CompletableFutureDemo {

    fun completableTask(){
        //run开头：这种不会返回结果，就只是执行线程任务
        CompletableFuture.runAsync {

        }
        //supply开头：这种方法，可以返回异步线程执行之后的结果
        CompletableFuture.supplyAsync {
            return@supplyAsync 10086
        }.handle { t, u ->
            return@handle ""
        }.whenComplete { result, error ->
            result
        }
    }


}