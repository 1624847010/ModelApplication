package com.ll.myapplication.data.datasource

import android.content.Context
import com.ll.myapplication.data.db.Users

/**
 * @Author: ll
 * @CreateTime: 2022/08/02 15:55
 */
class FakeDataSource(private val context: Context) {
    var page: Int = 0

    fun fakeNews(query: String, page: Int): List<Users> =
        if (page < 5)
            (0 * page..10 * page).map { Users(label = "查询内容:$query==>条数:$it,页码:$page") }
        else
            emptyList()


    fun fakeNews(query: String): List<Users> {
        page += 1
        return if (page < 5)
            (0 * page..10 * page)
                .map { Users(label = "查询内容:$query==>条数:$it,页码:$page") }
        else
            emptyList()
    }
}