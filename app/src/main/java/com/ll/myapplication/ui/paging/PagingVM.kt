package com.ll.myapplication.ui.paging

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ll.myapplication.data.datasource.ExamplePagingSource
import com.ll.myapplication.data.datasource.ExampleRemoteMediator
import com.ll.myapplication.data.db.UserRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: ll
 * @CreateTime: 2022/08/01 16:24
 */
@HiltViewModel
class PagingVM @Inject constructor(
    private val examplePagingSource: ExamplePagingSource,
    private val exampleRemoteMediator: ExampleRemoteMediator,
    private val userRoom: UserRoom,
) : ViewModel() {
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 10)
    ) {
        examplePagingSource
    }.flow
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    val flow2 = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        config = PagingConfig(pageSize = 20),
        remoteMediator = exampleRemoteMediator
    ) {
        userRoom.dao().pagingSource()
    }.flow
}