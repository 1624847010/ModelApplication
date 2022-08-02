package com.ll.myapplication

import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.ll.myapplication.ui.databinding.DatabindingActivity

/**
 * @Author: ll
 * @CreateTime: 2021/10/19 10:01
 */
class MyService : Service() {

    companion object{
        private const val TAG = "MyService"
    }

    //在调用 onStartCommand() 或 onBind() 之前）调用此方法来执行一次性设置程序
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")

        application.startActivity(Intent(application, DatabindingActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MyService = this@MyService
    }

    private val binder = LocalBinder()

    //调用 bindService() 来调用此方法
    //当该服务与其所有组件取消绑定后，系统便会将其销毁。
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        return binder
    }

    //startService() 来调用此方法
    //如果您只想提供绑定，则无需实现此方法。
    //调用 stopSelf() 或 stopService() 来停止服务
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}