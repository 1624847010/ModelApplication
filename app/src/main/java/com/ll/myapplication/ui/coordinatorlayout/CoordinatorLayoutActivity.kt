package com.ll.myapplication.ui.coordinatorlayout

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ll.myapplication.databinding.ActivityCoordinatorLayoutBinding

class CoordinatorLayoutActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCoordinatorLayoutBinding.inflate(layoutInflater) }
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CoordinatorLayoutActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}