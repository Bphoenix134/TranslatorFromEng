package com.example.translatorfromeng.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.translatorfromeng.data.remote.dto.WordResponse

interface SkyengApi {
    @GET("api/public/v1/words/search")
    suspend fun searchWord(@Query("search") word: String): List<WordResponse>

    companion object {
        const val BASE_URL = "https://dictionary.skyeng.ru/"
    }
}