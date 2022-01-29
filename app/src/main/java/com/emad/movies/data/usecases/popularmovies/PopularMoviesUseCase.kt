package com.emad.movies.data.usecases.popularmovies

import com.emad.movies.data.model.PopularMovies

interface PopularMoviesUseCase {
    suspend operator fun invoke(page: Int): PopularMovies
}