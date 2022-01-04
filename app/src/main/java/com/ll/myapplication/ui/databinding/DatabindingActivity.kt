package com.ll.myapplication.ui.databinding

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.databinding.ActivityDatabindingBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DatabindingActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDatabindingBinding.inflate(layoutInflater) }
    private val model by viewModels<DatabindingModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.model = model
//        binding.lifecycleOwner = this
        binding.bt1.setOnClickListener {
            binding.tv1.text= (model.tv.value+1).toString()
//            LogUtils.d(model.tv.value)
//            model.tv.value = "${model.tv.value}+1"
//            binding.tv1.apply {
//                text = "$text+1"
//            }
        }
        lifecycleScope.launch {
            launch {
                model.et.collect {
                    LogUtils.d(it)
                }
            }
            launch {
                model.tv.collect {
                    LogUtils.d(it)
                }
            }
        }
    }
}