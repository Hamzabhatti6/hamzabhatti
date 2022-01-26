package com.xisys.hamzabhatti.interfaces

import com.xisys.hamzabhatti.models.PageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("home")
    suspend fun getAllFeeds(): Response<PageResponse>
}