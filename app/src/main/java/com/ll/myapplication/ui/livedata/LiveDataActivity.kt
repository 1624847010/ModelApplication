package com.ll.myapplication.ui.livedata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.R

class LiveDataActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LiveDataActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val model by viewModels<LiveDataViewModel> { LiveDataFactory(application , 1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        model.mediatorData.observe(this){
            findViewById<TextView>(R.id.tv_1).text = it
        }

        model.getMediator()
    }
}