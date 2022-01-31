package com.emad.movies.data.usecases.checkfav

import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class CheckMovieUsecaseImpl @Inject constructor(private val repository: MoviesRepository): CheckMovieUsecase {
    override suspend fun invoke(movieID: Int): Int = repository.isMovieFav(movieID)
}