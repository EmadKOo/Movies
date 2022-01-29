package com.emad.movies.data.usecases.popularmovies

import com.emad.movies.data.model.PopularMovies
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class PopularMoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository): PopularMoviesUseCase {
    override suspend fun invoke(page: Int): PopularMovies = moviesRepository.getPopularMovies(page)
}