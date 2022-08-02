package com.ll.myapplication.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blankj.utilcode.util.LogUtils
import com.ll.myapplication.data.db.Users

/**
 * @Author: ll
 * @CreateTime: 2022/08/02 16:02
 */
//如果您通过将 Int 页码传递给 Retrofit 来从网络加载各页 User 对象，则应选择 Int 作为 Key 类型，选择 User 作为 Value 类型。
class ExamplePagingSource(
    private val dataSource: FakeDataSource
) : PagingSource<Int, Users>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Users> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = dataSource.fakeNews("ExamplePagingSource", nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null, // Only paging forward.
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LogUtils.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Users>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

//    @OptIn(ExperimentalPagingApi::class)
//    class ExampleRemoteMediator() : RemoteMediator<Int, String>() {
//        override suspend fun load(
//            loadType: LoadType,
//            state: PagingState<Int, String>
//        ): MediatorResult {
//
//        }
//    }