package com.emad.movies.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.*
import com.emad.movies.data.repositories.MoviesRepository
import com.emad.movies.data.usecases.addfavourite.AddFavouriteUsecase
import com.emad.movies.data.usecases.checkfav.CheckMovieUsecase
import com.emad.movies.data.usecases.getallfavs.GetAllFavouritesUsecase
import com.emad.movies.data.usecases.getmoviereviews.GetMovieReviewsUsecase
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecase
import com.emad.movies.data.usecases.popularmovies.PopularMoviesUseCase
import com.emad.movies.data.usecases.requestrate.RequestRateUsecase
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
    private val requestTokenUsecase: RequestTokenUsecase,
    private val requestRateUsecase: RequestRateUsecase,
    private val addFavouriteUsecase: AddFavouriteUsecase,
    private val getAllFavouritesUsecase: GetAllFavouritesUsecase,
    private val checkMovieUsecase: CheckMovieUsecase,
) : ViewModel() {
    private val _movieDetailsStateFlow = MutableStateFlow<Resource<DetailsEntity>>(Resource.Init())
    val movieDetailsStateFlow: StateFlow<Resource<DetailsEntity>> = _movieDetailsStateFlow

    private val _getMovieReviewsStateFlow= MutableStateFlow<Resource<MovieReviews>>(Resource.Init())
    val getMovieReviewsStateFlow: StateFlow<Resource<MovieReviews>> = _getMovieReviewsStateFlow

    private val _requestTokenStateFlow = MutableStateFlow<Resource<Token>>(Resource.Init())
    val requestTokenStateFlow: StateFlow<Resource<Token>> = _requestTokenStateFlow

    private val _requestRateStateFlow= MutableStateFlow<Resource<RateResponse>>(Resource.Init())
    val requestRateStateFlow: StateFlow<Resource<RateResponse>> = _requestRateStateFlow

    private val _addFavouriteStateFlow= MutableStateFlow<Resource<Long>>(Resource.Init())
    val addFavouriteStateFlow: StateFlow<Resource<Long>> = _addFavouriteStateFlow

    private val _getAllFavouritesStateFlow = MutableStateFlow<Resource<ArrayList<FavouriteEntity>>>(Resource.Init())
    val getAllFavouritesStateFlow: StateFlow<Resource<ArrayList<FavouriteEntity>>> = _getAllFavouritesStateFlow

    private val _checkIfMovieIsFavStateFlow = MutableStateFlow<Resource<Int>>(Resource.Init())
    val checkIfMovieIsFavStateFlow: StateFlow<Resource<Int>> = _checkIfMovieIsFavStateFlow

    private val _addMoviesStateFlow= MutableStateFlow<Resource<Array<Long>>>(Resource.Init())
    val addMoviesStateFlow: StateFlow<Resource<Array<Long>>> = _addMoviesStateFlow

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

    fun addingMovieRate(movie_id: Int, session_id: String, requestRate: RequestRate)= viewModelScope.launch {
        try {
            _requestRateStateFlow.emit(Resource.Loading())
            _requestRateStateFlow.emit(Resource.Success(requestRateUsecase.invoke(movie_id, session_id, requestRate)))
        }catch (ex: Exception){
            _requestRateStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }

    fun addFavouriteMovie(favouriteEntity: FavouriteEntity)= viewModelScope.launch {
        try {
            _addFavouriteStateFlow.emit(Resource.Loading())
            _addFavouriteStateFlow.emit(Resource.Success(addFavouriteUsecase.invoke(favouriteEntity)))
        }catch (ex: Exception){
            _addFavouriteStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }

    fun getAllFavourites()= viewModelScope.launch {
        try {
            _getAllFavouritesStateFlow.emit(Resource.Loading())
            _getAllFavouritesStateFlow.emit(Resource.Success(getAllFavouritesUsecase.invoke()))
        }catch (ex: Exception){
            _getAllFavouritesStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }

    fun checkIfFav(movieID: Int)= viewModelScope.launch {
        try {
            _checkIfMovieIsFavStateFlow.emit(Resource.Loading())
            _checkIfMovieIsFavStateFlow.emit(Resource.Success(checkMovieUsecase.invoke(movieID)))
        }catch (ex: Exception){
            _checkIfMovieIsFavStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }
    companion object{
        private const val TAG = "MovieViewModel"
    }
}