package com.ll.myapplication.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.data.db.UserRoom
import com.ll.myapplication.data.db.Users

/**
 * @Author: ll
 * @CreateTime: 2022/08/02 16:06
 */
@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator(
    private val dataSource: FakeDataSource,
    private val database: UserRoom
) : RemoteMediator<Int, Users>() {
    private val userDao = database.dao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Users>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    lastItem
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = dataSource.fakeNews(query = "ExampleRemoteMediator")

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                userDao.insertAll(response)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )
        } catch (e: Exception) {
            LogUtils.e(e)
            MediatorResult.Error(e)
        }
    }
}