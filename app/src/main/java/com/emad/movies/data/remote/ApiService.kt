package com.emad.movies.data.remote

import androidx.databinding.BindingAdapter
import com.emad.movies.BuildConfig
import com.emad.movies.data.model.*
import retrofit2.http.*

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
        @Query("page") page: Int = 1,
    ): PopularMovies


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
    ): MovieDetails

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en_Us",
        @Query("page") page: Int = 1,
    ): MovieReviews

    @GET("authentication/token/new")
    suspend fun requestNewToken(@Query("api_key") api_key: String = BuildConfig.API_KEY): Token

    @POST("movie/{movie_id}/rating")
    suspend fun postRate(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("session_id") session_id: String,
        @Body requestRate: RequestRate,
    ): RateResponse
}