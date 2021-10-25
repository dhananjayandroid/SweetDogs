package com.djay.sweetdogs.data.remote.api

import com.djay.sweetdogs.domain.model.Dog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsService {

    @GET("/v1/breeds?")
    suspend fun getDogs(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("attach_breed") breed: Int
    ): Response<List<Dog>>

}
