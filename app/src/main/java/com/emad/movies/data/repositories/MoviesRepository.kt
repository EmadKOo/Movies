package com.emad.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emad.movies.data.MoviePagingSource
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.RequestRate
import com.emad.movies.data.remote.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService, private val favouritesDao: FavouritesDao, private val moviesDao: MovieDao) {

     fun getPopularMovies()= Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MoviePagingSource(apiService, moviesDao)}
    ).flow

    suspend fun getMovieDetails(movie_id: Int)= apiService.getMovieDetails(movie_id = movie_id)
    suspend fun getMovieReviews(movie_id: Int)= apiService.getMovieReviews(movie_id = movie_id)
    suspend fun requestNewToken()= apiService.requestNewToken()
    suspend fun addRate(movie_id: Int, session_id: String, requestRate: RequestRate)= apiService.postRate(movie_id = movie_id, session_id = session_id, requestRate = requestRate)

    suspend fun addFavourite(favouriteEntity: FavouriteEntity)= favouritesDao.addFavourite(favouriteEntity)
    suspend fun getAllFavourites()= favouritesDao.getFavourites()
    suspend fun isMovieFav(movieID: Int)= favouritesDao.isFav(movieID)

    suspend fun addMovies(movieEntitys: List<MovieEntity>)= moviesDao.addMovies(movieEntitys)
}