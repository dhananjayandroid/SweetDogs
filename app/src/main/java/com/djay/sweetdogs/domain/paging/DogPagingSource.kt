package com.djay.sweetdogs.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.djay.sweetdogs.common.CallErrors
import com.djay.sweetdogs.common.Result
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.domain.paging.PagingConstants.Companion.FIRST_PAGE
import javax.inject.Inject

class DogPagingSource @Inject constructor(
    private val func: suspend (pageNumber: Int, pageSize: Int) -> Result<List<Dog>>
) : PagingSource<Int, Dog>() {

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

    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}