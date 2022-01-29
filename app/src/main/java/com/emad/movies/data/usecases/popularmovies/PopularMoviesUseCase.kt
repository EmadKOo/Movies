package com.emad.movies.data.usecases.popularmovies

import androidx.paging.PagingData
import com.emad.movies.data.model.PopularMovies
import kotlinx.coroutines.flow.Flow

interface PopularMoviesUseCase {
    suspend operator fun invoke(): Flow<PagingData<PopularMovies.Result>>
}