package com.emad.movies.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.remote.ApiService
import java.lang.Exception

class MoviePagingSource(private val apiService: ApiService, private val movieDao: MovieDao) : PagingSource<Int, MovieEntity>() {
    private val firstIndex = 1
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: firstIndex
        return try {
            val response = apiService.getPopularMovies(page = position)
            val movies = response.results
            movieDao.addMovies(movies.map {
                MovieEntity(movieID = it.id, movieName = it.original_title, movieImagePath = it.poster_path)
            })
            val list= movieDao.getMovies()
            LoadResult.Page(
                data = list,
                prevKey = if (position == firstIndex) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1)

        } catch (ex: Exception) {
            val list= movieDao.getMovies()
            LoadResult.Page(
                data = list,
                prevKey = if (position == firstIndex) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1)
        }
    }
}