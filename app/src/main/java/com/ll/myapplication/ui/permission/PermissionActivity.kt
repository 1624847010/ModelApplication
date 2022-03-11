package com.ll.myapplication.ui.permission

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ll.myapplication.databinding.ActivityPermissionBinding
import com.permissionx.guolindev.PermissionX

class PermissionActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun ItemClick(v:View){
        PermissionX.init(this)
            .permissions(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    listOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } else {
                    listOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "需要您同意以下权限才能正常使用", "确定", "取消")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "去设置", "去设置开启权限", "取消")
            }
            .request {  allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }
}