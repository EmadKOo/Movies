package com.emad.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.usecases.popularmovies.PopularMoviesUseCase
import com.emad.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val popularMoviesUseCase: PopularMoviesUseCase): ViewModel() {
    private val _popularMoviesStateFlow= MutableStateFlow<Resource<Flow<PagingData<PopularMovies.Result>>>>(Resource.Init())
    val popularMoviesStateFlow: StateFlow<Resource<Flow<PagingData<PopularMovies.Result>>>> = _popularMoviesStateFlow

    suspend fun loadPopularMovies()= viewModelScope.launch {
        try {
            _popularMoviesStateFlow.emit(Resource.Loading())
            _popularMoviesStateFlow.emit(Resource.Success(popularMoviesUseCase.invoke()))
        }catch (ex: Exception){
            _popularMoviesStateFlow.emit(Resource.Error(ex.localizedMessage))
        }
    }
}