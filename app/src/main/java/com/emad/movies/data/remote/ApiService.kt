package com.emad.movies.data.remote

import com.emad.movies.BuildConfig
import com.emad.movies.data.model.PopularMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String= BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
        @Query("page") page: Int=1): PopularMovies

}