package com.emad.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emad.movies.data.MoviePagingSource
import com.emad.movies.data.local.dao.DetailsDao
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.dao.ReviewsDao
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.local.entities.ReviewEntity
import com.emad.movies.data.model.RequestRate
import com.emad.movies.data.remote.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService, private val favouritesDao: FavouritesDao, private val moviesDao: MovieDao, private val detailsDao: DetailsDao, private val reviewsDao: ReviewsDao) {

     fun getPopularMovies()= Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MoviePagingSource(apiService, moviesDao)}
    ).flow

    suspend fun getMovieDetails(movie_id: Int): DetailsEntity{
        try {
            val response= apiService.getMovieDetails(movie_id = movie_id)
            detailsDao.addDetails(DetailsEntity(movieID= response.id, movieName = response.title, movieImagePath = response.poster_path, movieDescription = response.overview, movieReleaseDate = response.release_date, movieVotes = response.vote_average, moviePopularity = response.popularity))
            return detailsDao.getDetails(movie_id)
        }catch (ex: Exception){
            val cachedDetailsInfo = detailsDao.getDetails(movie_id)
            if (cachedDetailsInfo != null)
                return cachedDetailsInfo
            return DetailsEntity(0, "", "", "", "", 0.0,0.0)
        }
    }
    suspend fun getMovieReviews(movie_id: Int): List<ReviewEntity>{
        try {
            val response= apiService.getMovieReviews(movie_id = movie_id)
            val reviewsEntitys= response.results.map {
                ReviewEntity(movie_id, reviewID = it.id, authorName = it.author, reviewContent = it.content)
            }
            reviewsDao.addReviews(reviewsEntitys)
            return reviewsDao.getReviews(movie_id)
        }catch (ex: Exception){
            val cachedReviews= reviewsDao.getReviews(movie_id)
            if (cachedReviews != null)
                return cachedReviews
            return arrayListOf<ReviewEntity>()
        }
    }
    suspend fun requestNewToken()= apiService.requestNewToken()
    suspend fun addRate(movie_id: Int, session_id: String, requestRate: RequestRate)= apiService.postRate(movie_id = movie_id, session_id = session_id, requestRate = requestRate)

    suspend fun addFavourite(favouriteEntity: FavouriteEntity)= favouritesDao.addFavourite(favouriteEntity)
    suspend fun getAllFavourites()= favouritesDao.getFavourites()
    suspend fun isMovieFav(movieID: Int)= favouritesDao.isFav(movieID)

    suspend fun addMovies(movieEntitys: List<MovieEntity>)= moviesDao.addMovies(movieEntitys)
}