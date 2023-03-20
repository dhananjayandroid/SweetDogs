package com.djay.sweetdogs.data.remote.mapper

import retrofit2.Response
import com.djay.sweetdogs.common.Result

interface ResponseMapper<T : Any> {
    fun map(response: Response<T>): Result<T>
}
