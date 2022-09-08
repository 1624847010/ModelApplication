package com.ll.myapplication.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ll.myapplication.R

/**
 * 注意：如果您的可搜索 Activity 会在单一顶级模式 (android:launchMode="singleTop") 下启动，
 * 请也在 onNewIntent() 方法中处理 ACTION_SEARCH intent。在单一顶级模式下，只能创建 Activity 的一个实例，
 * 启动 Activity 的后续调用不会在堆栈上创建新的 Activity。该启动模式非常实用，用户可以通过同一 Activity 执行搜索，
 * 而无需每次都创建新的 Activity 实例。
 */
class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }
    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Toast.makeText(this, query, Toast.LENGTH_LONG).show()
        }
    }
}