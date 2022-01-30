package com.emad.movies.data.remote

import com.emad.movies.BuildConfig
import com.emad.movies.data.model.MovieDetails
import com.emad.movies.data.model.MovieReviews
import com.emad.movies.data.model.PopularMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String= BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
        @Query("page") page: Int=1): PopularMovies


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String= BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us"): MovieDetails

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String= BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
        @Query("page") page: Int = 1): MovieReviews
}