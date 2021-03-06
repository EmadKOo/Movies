package com.emad.movies.data.usecases.popularmovies

import androidx.paging.PagingData
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularMoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository): PopularMoviesUseCase {
    override  fun invoke(): Flow<PagingData<MovieEntity>> = moviesRepository.getPopularMovies()
}