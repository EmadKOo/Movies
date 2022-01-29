package com.emad.movies.data.repositories

import com.emad.movies.data.remote.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPopularMovies(page: Int)= apiService.getPopularMovies(page = page)
}