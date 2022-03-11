package com.ll.myapplication

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ll.myapplication.databinding.ActivityMainBinding
import com.ll.myapplication.ui.HandlerActivity
import com.ll.myapplication.ui.compose.ComposeActivity
import com.ll.myapplication.ui.permission.PermissionActivity
import com.ll.myapplication.ui.coroutine.CoroutineActivity
import com.ll.myapplication.ui.databinding.DatabindingActivity
import com.ll.myapplication.ui.flow.FlowActivity
import com.ll.myapplication.ui.livedata.LiveDataActivity
import com.ll.myapplication.ui.shape.ShapeActivity
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val br: BroadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            flow {
                emit(0)
            }.map {
                "$it"
            }.onEach {
                it.toInt()
            }.onCompletion {

            }
        }

        initBar()
        val filter = IntentFilter().apply {
            addAction(TAG)
        }

        registerReceiver(br, filter)

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
        }
    }

    private fun initBar() {
        ViewCompat.getWindowInsetsController(binding.root)?.apply {
            //显示状态栏
            show(WindowInsetsCompat.Type.statusBars())
            //状态栏文字颜色改为黑色,默认白
//            isAppearanceLightStatusBars = true
            //隐藏状态栏,(状态栏会变黑)
//            hide(WindowInsetsCompat.Type.statusBars())
            //隐藏状态栏 导航栏,(状态栏会变黑)
//            hide(WindowInsetsCompat.Type.systemBars())
            //显示所有系统栏
//            show(WindowInsetsCompat.Type.systemBars())
            //导航栏隐藏时手势操作
            //systemBarsBehavior有三个值：
            //BEHAVIOR_SHOW_BARS_BY_SWIPE
            //BEHAVIOR_SHOW_BARS_BY_TOUCH
            //BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
}