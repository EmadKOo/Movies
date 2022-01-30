package com.emad.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emad.movies.data.MoviePagingSource
import com.emad.movies.data.remote.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService) {

     fun getPopularMovies()= Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MoviePagingSource(apiService)}
    ).flow
}