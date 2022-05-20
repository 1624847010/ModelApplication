package com.ll.myapplication

import android.content.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.databinding.ActivityMainBinding
import com.ll.myapplication.demo.App
import com.ll.myapplication.demo.BaseApp
import com.ll.myapplication.demo.Sout
import com.ll.myapplication.model.Singleton
import com.ll.myapplication.ui.HandlerActivity
import com.ll.myapplication.ui.asynctask.AsyncTaskDemo
import com.ll.myapplication.ui.channel.ChannelTest
import com.ll.myapplication.ui.compose.ComposeActivity
import com.ll.myapplication.ui.coordinatorlayout.CoordinatorLayoutActivity
import com.ll.myapplication.ui.coroutine.Coroutine2Activity
import com.ll.myapplication.ui.permission.PermissionActivity
import com.ll.myapplication.ui.coroutine.CoroutineActivity
import com.ll.myapplication.ui.databinding.DatabindingActivity
import com.ll.myapplication.ui.flow.FlowActivity
import com.ll.myapplication.ui.infix.InfixDemo
import com.ll.myapplication.ui.inline.InlineDemo
import com.ll.myapplication.ui.livedata.LiveDataActivity
import com.ll.myapplication.ui.shape.ShapeActivity
import com.ll.myapplication.ui.view.CustomViewActivity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val br: BroadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBar()
        val filter = IntentFilter().apply {
            addAction(TAG)
        }

        registerReceiver(br, filter)

        lifecycleScope.launch {
            ChannelTest.main()
        }

        with(binding) {
            sendBroadcast.setOnClickListener {
                Intent().also { intent ->
                    intent.action = TAG
                    intent.putExtra("data", "Notice me senpai!")
                    sendBroadcast(intent)
                }
            }

            val intent = Intent(this@MainActivity, MyService::class.java)
            startService.setOnClickListener {
                startService(intent)
            }
            stopService.setOnClickListener {
                stopService(intent)
            }

            btDatabinding.setOnClickListener {
                startActivity(Intent(this@MainActivity, DatabindingActivity::class.java))
            }

            btShape.setOnClickListener {
                startActivity(Intent(this@MainActivity, ShapeActivity::class.java))
            }

            btCoroutine.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutineActivity::class.java))
            }

            btCoroutine2.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutine2Activity::class.java))
            }

            btPermission.setOnClickListener {
                startActivity(Intent(this@MainActivity, PermissionActivity::class.java))
            }

            btCompose.setOnClickListener {
                startActivity(Intent(this@MainActivity, ComposeActivity::class.java))
            }

            btHandler.setOnClickListener {
                startActivity(Intent(this@MainActivity, HandlerActivity::class.java))
            }

            btLivedata.setOnClickListener {
                LiveDataActivity.start(this@MainActivity)
            }
            btFlow.setOnClickListener {
                FlowActivity.start(this@MainActivity)
            }
            btView.setOnClickListener {
                CustomViewActivity.start(this@MainActivity)
            }
            btCoordinatorLayout.setOnClickListener {
                CoordinatorLayoutActivity.start(this@MainActivity)
            }
        }
    }

    private fun initBar() {
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window,binding.root).apply {
//            hide(WindowInsetsCompat.Type.statusBars())
//            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//        }
        ViewCompat.getWindowInsetsController(binding.root)?.apply {
            //状态栏文字颜色改为黑色,默认白
            isAppearanceLightStatusBars = true
            //隐藏系统栏,(状态栏会变黑)
//            hide(WindowInsetsCompat.Type.statusBars())
//            show(WindowInsetsCompat.Type.systemBars())
            //显示系统栏
            //导航栏隐藏时手势操作
            //systemBarsBehavior有三个值：
            //BEHAVIOR_SHOW_BARS_BY_SWIPE
            //BEHAVIOR_SHOW_BARS_BY_TOUCH
            //BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
}