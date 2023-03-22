package com.djay.sweetdogs.data.remote.api

import com.djay.sweetdogs.data.remote.model.DogDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsService {

    @GET("/v1/breeds?")
    suspend fun getDogsResponse(
        @Query("attach_breed") breed: Int
    ): Response<List<DogDTO>>

}
