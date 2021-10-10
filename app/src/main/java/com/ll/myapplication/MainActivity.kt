package com.ll.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.ll.myapplication.databinding.ActivityMainBinding
import java.io.File
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

   companion object{
       private const val TAG = "MainActivity"
   }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val br: BroadcastReceiver = MyBroadcastReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val filter = IntentFilter().apply {
            addAction(TAG)
        }

        registerReceiver(br, filter)

        binding.sendBroadcast.setOnClickListener {
            Intent().also { intent ->
                intent.action = TAG
                intent.putExtra("data", "Notice me senpai!")
                sendBroadcast(intent)
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
}