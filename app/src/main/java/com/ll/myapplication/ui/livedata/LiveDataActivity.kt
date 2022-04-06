package com.ll.myapplication.ui.livedata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.R
import kotlinx.coroutines.delay
import kotlin.properties.Delegates

class LiveDataActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LiveDataActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val model by viewModels<LiveDataViewModel> { LiveDataFactory(application, 1) }

    //监听数值变动
    private var a by Delegates.observable(0) { _, old, new ->
        LogUtils.d("$old => $new")
        onStateChangeListener?.onStateChanged(old,new)
    }

    //监听数值变动 并判断是否允许修改
    private var b by Delegates.vetoable(0) { _, old, new ->
        LogUtils.d("$old => $new")
        onStateChangeListener?.onStateChanged(old,new)
        old>new
    }

    private var onStateChangeListener: OnStateChangeListener? = null

    interface OnStateChangeListener {
        fun onStateChanged(old: Int, new: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        model.mediatorData.observe(this) {
            findViewById<TextView>(R.id.tv_1).text = it
        }

        lifecycleScope.launchWhenCreated {
            delay(1000)
            ++a
            --b
            delay(1000)
            ++a
            --b
            delay(1000)
            ++a
            --b
        }

        onStateChangeListener = object : OnStateChangeListener{
            override fun onStateChanged(old: Int, new: Int) {
                LogUtils.d("${Thread.currentThread()}  $old => $new")
            }
        }
        model.getMediator()
    }
}