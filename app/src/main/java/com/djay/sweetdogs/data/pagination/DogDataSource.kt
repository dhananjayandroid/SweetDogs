package com.djay.sweetdogs.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.djay.sweetdogs.data.pagination.DogPagingSource.Constants.FIRST_PAGE
import com.djay.sweetdogs.domain.common.CallErrors
import com.djay.sweetdogs.domain.common.Result
import com.djay.sweetdogs.domain.model.Dog
import javax.inject.Inject

class DogPagingSource @Inject constructor(
    private val func: suspend (pageNumber: Int, pageSize: Int) -> Result<List<Dog>>
) : PagingSource<Int, Dog>() {

    object Constants {
        const val PAGE_SIZE = 10
        const val FIRST_PAGE = 1
        const val BREED = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        val position = params.key ?: FIRST_PAGE
        return when (val apiEvent = func.invoke(position, params.loadSize)) {
            is Result.Error -> LoadResult.Error(
                when (apiEvent.exception) {
                    CallErrors.ErrorEmptyData -> Throwable("Empty data")
                    is CallErrors.ErrorException -> apiEvent.exception.throwable
                    CallErrors.ErrorServer -> Throwable("Server error")
                }
            )
            is Result.Success -> LoadResult.Page(
                data = apiEvent.data,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                nextKey = if (apiEvent.data.isEmpty()) null else position + 1
            )
            else -> LoadResult.Error(Throwable("No Data Found."))
        }
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