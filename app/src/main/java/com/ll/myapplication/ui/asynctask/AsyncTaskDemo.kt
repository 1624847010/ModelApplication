package com.ll.myapplication.ui.asynctask

import android.os.AsyncTask
import com.blankj.utilcode.util.LogUtils

/**
 * @Author: ll
 * @CreateTime: 2022/05/05 17:38
 */
object AsyncTaskDemo {

    @JvmStatic
    fun taskFun() {

        val task = object : AsyncTask<Int, Int, String>() {
            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                LogUtils.d("onPreExecute")
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg params: Int?): String {
                for (i in params) {
                    i?.let { count ->
                        if (!isCancelled) {
                            for (a in 0..count) {
                                Thread.sleep(500)
                                publishProgress(a)
                            }
                        }
                    }
                }
                return "completed"
            }

            @Deprecated("Deprecated in Java")
            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                LogUtils.d(values)
            }

            @Deprecated("Deprecated in Java")
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                LogUtils.d("onPostExecute: $result")
            }

        }

        task.execute(10)
        /**
         * task.get() 取得值 但阻塞主线程 显然与使用习惯不符
         */

    }

}