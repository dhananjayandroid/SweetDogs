package com.djay.sweetdogs.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.djay.sweetdogs.domain.model.Dog

class FakeDogPagingSource(private val dogs: List<Dog>) : PagingSource<Int, Dog>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        val pageNumber = params.key ?: 1
        val page = dogs.subList(
            (pageNumber - 1) * params.loadSize,
            minOf(pageNumber * params.loadSize, dogs.size)
        )
        return LoadResult.Page(
            data = page,
            prevKey = if (pageNumber == 1) null else pageNumber - 1,
            nextKey = (pageNumber + 1).takeIf { (pageNumber * params.loadSize) < dogs.size }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        return null
    }
}