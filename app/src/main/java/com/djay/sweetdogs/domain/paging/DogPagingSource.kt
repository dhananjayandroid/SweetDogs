package com.djay.sweetdogs.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.djay.sweetdogs.domain.model.Dog
import javax.inject.Inject

class DogPagingSource @Inject constructor(
    private val func: suspend (pageNumber: Int, pageSize: Int) -> List<Dog>
) : PagingSource<Int, Dog>() {

    companion object Constants {
        const val PAGE_SIZE = 10
        const val FIRST_PAGE = 1
        const val BREED = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        val position = params.key ?: FIRST_PAGE
        val data = func.invoke(position, params.loadSize)
        return LoadResult.Page(
                data = data,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}