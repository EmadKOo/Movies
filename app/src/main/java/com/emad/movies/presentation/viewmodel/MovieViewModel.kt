package com.emad.movies.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.emad.movies.data.model.MovieDetails
import com.emad.movies.data.model.MovieReviews
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.model.Token
import com.emad.movies.data.repositories.MoviesRepository
import com.emad.movies.data.usecases.getmoviereviews.GetMovieReviewsUsecase
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecase
import com.emad.movies.data.usecases.popularmovies.PopularMoviesUseCase
import com.emad.movies.data.usecases.requesttoken.RequestTokenUsecase
import com.emad.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val movieDetailsUsecase: MovieDetailsUsecase,
    private val getMovieReviewsUsecase: GetMovieReviewsUsecase,
    private val requestTokenUsecase: RequestTokenUsecase
) : ViewModel() {
    private val _movieDetailsStateFlow = MutableStateFlow<Resource<MovieDetails>>(Resource.Init())
    val movieDetailsStateFlow: StateFlow<Resource<MovieDetails>> = _movieDetailsStateFlow

    private val _getMovieReviewsStateFlow= MutableStateFlow<Resource<MovieReviews>>(Resource.Init())
    val getMovieReviewsStateFlow: StateFlow<Resource<MovieReviews>> = _getMovieReviewsStateFlow

    private val _requestTokenStateFlow = MutableStateFlow<Resource<Token>>(Resource.Init())
    val requestTokenStateFlow: StateFlow<Resource<Token>> = _requestTokenStateFlow

    val moviesFlow = popularMoviesUseCase.invoke()

    fun getMovieDetails(movieID: Int) = viewModelScope.launch {
        try {
            _movieDetailsStateFlow.emit(Resource.Loading())
            _movieDetailsStateFlow.emit(Resource.Success(movieDetailsUsecase.invoke(movieID)))
        } catch (ex: Exception) {
            _movieDetailsStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }

    fun getMovieReviews(movieID: Int)= viewModelScope.launch {
        try {
            _getMovieReviewsStateFlow.emit(Resource.Loading())
            _getMovieReviewsStateFlow.emit(Resource.Success(getMovieReviewsUsecase.invoke(movieID)))
        }catch (ex: Exception){
            _getMovieReviewsStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }

    fun requestNewToken()= viewModelScope.launch {
        try {
            _requestTokenStateFlow.emit(Resource.Loading())
            _requestTokenStateFlow.emit(Resource.Success(requestTokenUsecase.invoke()))
        }catch (ex: Exception){
            _requestTokenStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }
}