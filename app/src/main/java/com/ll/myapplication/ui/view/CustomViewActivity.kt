package com.ll.myapplication.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ll.myapplication.databinding.ActivityCustomViewBinding

class CustomViewActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CustomViewActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val binding by lazy { ActivityCustomViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}