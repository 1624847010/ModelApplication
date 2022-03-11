package com.ll.myapplication.ui.flow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.ll.myapplication.R
import com.ll.myapplication.databinding.ActivityFlowBinding
import kotlinx.coroutines.flow.Flow

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