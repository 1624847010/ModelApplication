package com.ll.myapplication.ui.flow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.R
import com.ll.myapplication.databinding.ActivityFlowBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class FlowActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, FlowActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val model by viewModels<FlowViewModel>()

    private val binding by lazy { ActivityFlowBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            observe(model.flow1,tv1)
            observe(model.flow2,tv2)
            observe(model.flow3,tv3)
            observe(model.flow4,tv4)
            observe(model.flow5,tv5)
            observe(model.flow6,tv6)
            observe(model.flow7,tv7)
            observe(model.flow8,tv8)
            observe(model.flow9,tv9)
            observe(model.flow10,tv10)
            observe(model.flow11,tv11)

            val a = AtomicInteger()
            button.setOnClickListener {
//                model.state.value = !model.state.value
                model.channel.trySend(a.addAndGet(1)).isSuccess.apply {
//                    LogUtils.d(it)
                }
            }
        }

        lifecycleScope.launch {
            //热流一个flow占一个协程
            launch {
                //延迟5000ms达到新订阅者效果 会立即返回replay数量的值 replay=2 false false
//                delay(5000)
                model.state
                    //会在被观察的生命周期中进行开始与结束
//                    .onSubscription { model.state.emit(true) }
//                    .onStart { LogUtils.d("onStart") }
//                    .onCompletion { LogUtils.d("onCompletion") }
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        LogUtils.d(it)
                    }
            }


//            launch {
//                model.state
//                    .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
//                    .collect {
//                        LogUtils.d(it)
//                    }
//            }

//            launch {
//                flowOf(1).collect {
//                    LogUtils.d(it)
//                }
//                flowOf(2).collect {
//                    LogUtils.d(it)
//                }
//                flowOf(3).collect {
//                    LogUtils.d(it)
//                }
//            }
        }
    }

    private fun <T> LifecycleOwner.observe(liveData: Flow<T>, tv: TextView) {
        liveData.asLiveData().observe(this, Observer {
            it?.let { t ->
                tv.text = t.toString()
            }
        })
    }
}