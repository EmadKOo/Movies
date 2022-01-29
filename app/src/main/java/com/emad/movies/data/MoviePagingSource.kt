package com.emad.movies.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.remote.ApiService
import java.lang.Exception

class MoviePagingSource(private val apiService: ApiService) : PagingSource<Int, PopularMovies.Result>() {
    private val firstIndex = 1
    override fun getRefreshKey(state: PagingState<Int, PopularMovies.Result>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovies.Result> {
        return try {
            val position = params.key ?: firstIndex
            val response = apiService.getPopularMovies(page = position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == firstIndex) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1)

        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}